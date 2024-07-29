package lt.project.ntis.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.constants.DbConstants;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprNewsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNewsFilesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprNewsView;
import eu.itreegroup.spark.modules.admin.logic.forms.model.NewsCommentModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprNewsCommentsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsFilesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;
import lt.project.ntis.logic.forms.model.NtisNewsEditModel;

@Component
public class NtisSprNewsView extends SprNewsView {

    private final String DELETE_ALL_COMMENTS = "DELETE_ALL_COMMENTS";

    private static final Logger log = LoggerFactory.getLogger(NtisSprNewsView.class);

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    SprFilesDBService sprFilesDBService;

    @Autowired
    DBStatementManager dbstatementManager;

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprNewsDBService sprNewsDBService;

    @Autowired
    SprNewsFilesDBService sprNewsFilesDBService;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprNewsCommentsDBService sprNewsCommentsDBService;

    /**
    * This function checks if a user has the privilege to delete a comment.
    * If the user has the "DELETE_ALL_COMMENTS" action available, they can delete any comment.
    * If they do not have this action, they can only delete their own comments.
    *
    * @param conn - Connection object used for database connection
    * @param userId - ID of the user trying to delete a comment
    * @param commentUserId - ID of the user who submitted the comment
    * @return boolean - returns true if the user can delete the comment, false otherwise
    * @throws Exception if an error occurs during the database operation
    */
    private boolean checkCommentAction(Connection conn, Double userId, Double commentUserId) {
        boolean canDeleteAllComments = this.getFormActions(conn).isActionAvailable(DELETE_ALL_COMMENTS);
        return canDeleteAllComments ? canDeleteAllComments : (userId.compareTo(commentUserId) > -1);
    }

    /**
     * This function returns NewsEditModel object by specified record ID.
     * 
     * @param conn - Connection object used for database connection
     * @param recordId - record ID by which NewsEditModel object will be returned
     * @param isPublic - boolean type parameter - is rec visible only publicly or not
     * @return NewsEditModel object with filled fields by specified record ID
     * @throws Exception if an error occurs
     */
    public NtisNewsEditModel view(Connection conn, SelectRequestParams params, Double userId, Boolean isPublic, String lang) throws Exception {
        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);
        NtisNewsEditModel returnData = new NtisNewsEditModel();
        HashMap<String, String> paramList = params.getParamList();
        Double recordId = Double.parseDouble(paramList.get("id"));
        lang = lang != null ? lang : paramList.get("lang");

        if (recordId != null) {
            SprNewsDAO loadedData = sprNewsDBService.loadRecord(conn, recordId);
            if (!lang.equals(loadedData.getNw_lang())) {
                if (loadedData.getC03() != null) {
                    loadedData = sprNewsDBService.loadRecordByParams(conn, " where nw_lang = ? and c03 = ? ", new SelectParamValue(lang),
                            new SelectParamValue(loadedData.getC03()));
                }
            }

            StatementAndParams stmt = new StatementAndParams();
            stmt.setStatement("""
                    SELECT nw_id as nwId,
                        nw_title as nwTitle,
                        nw_text as nwText,
                        nw_summary as nwSummary,
                        nw_type as nwType,
                        nw_publication_date as nwPublished,
                        c01 as isPublic,
                        c02 as isTemplate,
                        rc.rft_display_code as pageTemplateType
                      FROM spr_news
                      LEFT JOIN spr_ref_codes_vw rc ON rc.rfc_code = c03 AND rc.rfc_domain = 'NTIS_PAGE_TEMPLATE_TYPE' AND rc.rft_lang = ?
                                    """);
            stmt.addSelectParam(lang);
            stmt.addParam4WherePart("c03 = ?", loadedData.getC03());
            stmt.addParam4WherePart("nw_id = ?", loadedData.getNw_id());
            stmt.addParam4WherePart("nw_lang = ?", lang);
            try {
                returnData = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisNewsEditModel.class).get(0);
            } catch (Exception e) {
                return null;
            }

            List<SprFile> attachments = getNewsFiles(conn, recordId, isPublic);
            returnData.setAttachment(attachments);
            List<NewsCommentModel> comments = getNewsComments(conn, recordId, userId);
            returnData.setComments(comments);
        }
        if (isPublic && !returnData.getIsPublic().equals(DbConstants.BOOLEAN_TRUE)) {
            throw new SparkBusinessException(
                    new S2Message("common.error.action_not_granted", SparkMessageType.ERROR, "Actions are not granted to the current user"));
        } else {
            return returnData;
        }
    }

    /**
    * Get all news files belonging to the specified news.
    *
    * @param conn database connection
    * @param recordId news ID
    * @return list of news files objects
    * @throws Exception
    */
    private List<SprFile> getNewsFiles(Connection conn, Double recordId, boolean publicApi) throws Exception {

        List<SprFile> attachments = new ArrayList<>();
        List<SprNewsFilesDAO> data = sprNewsFilesDBService.loadRecordsByParams(conn, "nwf_nw_id = ?", new SelectParamValue(recordId));

        if (!data.isEmpty()) {
            for (SprNewsFilesDAO row : data) {
                SprFile attachment = new SprFile();
                SprFilesDAO file = sprFilesDBService.loadRecordByParams(conn, "fil_id = ?", new SelectParamValue(row.getNwf_fil_id()));
                attachment.setFil_content_type(file.getFil_content_type());
                attachment.setFil_key(!publicApi ? fileStorageService.encryptFileKey(file.getFil_key()) : row.getNwf_id().toString());
                attachment.setFil_name(file.getFil_name());
                attachments.add(attachment);
            }
        }
        return attachments;
    }

    /**
    * This function returns all news comments by specified news ID.
    *
    * @param conn - Connection object used for database connection
    * @param recordId - news ID by which all news comments will be returned
    * @return List<NewsCommentModel> list with all news comments by specified news ID
    * @throws Exception if an error occurs
    */
    private List<NewsCommentModel> getNewsComments(Connection conn, Double recordId, Double userId) throws Exception {

        List<NewsCommentModel> comments = new ArrayList<>();

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT nwc.nwc_id nwcId,
                nwc.nwc_comment nwcComment,
                nwc.nwc_create_date nwcCreateDate,
                nwc.nwc_usr_id nwcUsrId,
                nwc.nwc_nw_id nwcNwId,
                case when usr.usr_person_name is null and usr.usr_person_surname is null then
                usr.usr_username
                else
                COALESCE(usr.usr_person_name,' ') || ' ' || COALESCE(usr.usr_person_surname,' ') end nwcUser
                FROM spr_news_comments nwc
                LEFT JOIN spr_users usr
                ON usr.usr_id = nwc.nwc_usr_id
                WHERE nwc.nwc_nw_id = ? AND nwc.nwc_nwc_id IS NULL
                ORDER BY nwc.nwc_create_date DESC
                """);
        stmt.addSelectParam(recordId);
        List<NewsCommentModel> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NewsCommentModel.class);

        if (!data.isEmpty()) {
            for (NewsCommentModel row : data) {
                NewsCommentModel comment = new NewsCommentModel();
                comment.setNwcComment(row.getNwcComment());
                comment.setNwcCreateDate(row.getNwcCreateDate());
                comment.setNwcId(row.getNwcId());
                comment.setNwcNwId(row.getNwcNwId());
                comment.setNwcUsrId(row.getNwcUsrId());
                comment.setNwcUser(row.getNwcUser());
                comment.setCanDeleteComment(checkCommentAction(conn, userId, row.getNwcUsrId()));
                comment.setReplyComments(getAllReplyComments(conn, recordId, row.getNwcId(), userId));
                comments.add(comment);
            }
        }
        return comments;
    }

    /**
    * This function returns all replies to the specified news comment by specified news ID and comment ID.
    *
    * @param conn - Connection object used for database connection
    * @param recordId - news ID by which all replies to the specified comment will be returned
    * @param commentId - comment ID by which all replies to this comment will be returned
    * @return List<NewsCommentModel> list with all replies to the specified comment by specified news ID and comment ID
    * @throws Exception if an error occurs
    */
    private List<NewsCommentModel> getAllReplyComments(Connection conn, Double recordId, Double commentId, Double userId) throws Exception {

        List<NewsCommentModel> comments = new ArrayList<>();

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT nwc.nwc_id nwcId,
                nwc.nwc_comment nwcComment,
                nwc.nwc_create_date nwcCreateDate,
                nwc.nwc_usr_id nwcUsrId,
                nwc.nwc_nwc_id nwcNwcId,
                nwc.nwc_nw_id nwcNwId,
                case when usr.usr_person_name is null and usr.usr_person_surname is null then
                usr.usr_username
                else
                COALESCE(usr.usr_person_name,' ') || ' ' || COALESCE(usr.usr_person_surname,' ') end nwcUser
                FROM spr_news_comments nwc
                LEFT JOIN spr_users usr
                ON usr.usr_id = nwc.nwc_usr_id
                WHERE nwc.nwc_nw_id = ? AND nwc.nwc_nwc_id = ?
                ORDER BY nwc.nwc_create_date DESC
                """);
        stmt.addSelectParam(recordId);
        stmt.addSelectParam(commentId);
        List<NewsCommentModel> data = queryController.selectQueryAsObjectArrayList(conn, stmt, NewsCommentModel.class);

        for (NewsCommentModel row : data) {
            NewsCommentModel comment = new NewsCommentModel();
            comment.setNwcComment(row.getNwcComment());
            comment.setNwcCreateDate(row.getNwcCreateDate());
            comment.setNwcId(row.getNwcId());
            comment.setNwcNwcId(row.getNwcNwcId());
            comment.setNwcUsrId(row.getNwcUsrId());
            comment.setNwcNwId(row.getNwcNwId());
            comment.setNwcUser(row.getNwcUser());
            comment.setCanDeleteComment(checkCommentAction(conn, userId, row.getNwcUsrId()));
            comment.setReplyComments(row.getReplyComments());
            comments.add(comment);
        }
        return comments;
    }

    /**
     * Metodas grąžina spr_news lentoje esantį puslapio šabloną pagal pateiktą šablono kodą
     * @param conn - prisijungimas prie DB
     * @param pageTemplateType - šablono kodas iš klasifikatoriaus NTIS_PAGE_TEMPLATE_TYPE
     * @return NtisNewsEditModel objektas
     * @throws Exception
     */
    public NtisNewsEditModel viewPageTemplate(Connection conn, SelectRequestParams params, String lang) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("""
                SELECT nw_title as nwTitle,
                       nw_text as nwText
                  FROM spr_news
                  WHERE c03 is not null and c03 = ? and nw_lang = ?
                                """);
        HashMap<String, String> paramList = params.getParamList();
        stmt.addSelectParam(paramList.get("pageTemplateType"));
        stmt.addSelectParam(lang != null ? lang : paramList.get("lang"));

        NtisNewsEditModel returnData = queryController.selectQueryAsObjectArrayList(conn, stmt, NtisNewsEditModel.class).get(0);
        return returnData;
    }
}
