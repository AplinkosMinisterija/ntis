package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprNewsCommentsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNewsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprNewsFilesDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.NewsCommentModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.NewsEditModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.service.SprNewsCommentsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsDBService;
import eu.itreegroup.spark.modules.admin.service.SprNewsFilesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;

@Component
public class SprNewsView extends FormBase {

    private final String DELETE_ALL_COMMENTS = "DELETE_ALL_COMMENTS";

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

    @Override
    public String getFormName() {
        return "SPR_NEWS_VIEW";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark news view", "Spark news view");
        addFormActionCRUD();
    }

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
     * This function saves news comment and returns all news comments by specified news ID.
     * @param conn - Connection object used for database connection
     * @param formData - SprNewsCommentsDAO object with news comment data
     * @param userId - user ID who submitted the comment
     * @return List<NewsCommentModel> list with all news comments by specified news ID
     * @throws Exception if an error occurs
     */
    public List<NewsCommentModel> comment(Connection conn, SprNewsCommentsDAO formData, Double userId) throws Exception {
        formData.setNwc_usr_id(userId);
        formData.setNwc_create_date(new Date());
        SprNewsCommentsDAO data = sprNewsCommentsDBService.saveRecord(conn, formData);
        return getNewsComments(conn, data.getNwc_nw_id(), userId);
    }

    /**
     * This function deletes a comment if the user has the necessary privileges and then
     * returns all remaining comments for the specified news.
     *
     * @param conn - Connection object used for database connection
     * @param commentData - NewsCommentModel object with the comment data
     * @param userId - ID of the user trying to delete the comment
     * @return List<NewsCommentModel> - list with all remaining news comments for the specified news
     * @throws Exception if an error occurs during the database operation or the user does not have the necessary privileges
     */
    public List<NewsCommentModel> deleteComment(Connection conn, NewsCommentModel commentData, Double userId) throws Exception {

        boolean canDeleteAllComments = checkCommentAction(conn, userId, commentData.getNwcUsrId());
        if (canDeleteAllComments) {
            if (commentData.getNwcNwcId() == null) {
                List<SprNewsCommentsDAO> loadedRecords = sprNewsCommentsDBService.loadRecordsByParams(conn, " nwc_nwc_id = ? ",
                        new SelectParamValue(commentData.getNwcId()));
                for (SprNewsCommentsDAO row : loadedRecords) {
                    sprNewsCommentsDBService.deleteRecord(conn, row.getNwc_id());
                }
            }
            sprNewsCommentsDBService.deleteRecord(conn, commentData.getNwcId());
        } else {
            throw new IllegalArgumentException("Permission Denied: You do not have the necessary privileges to delete another user's comment.");
        }
        return getNewsComments(conn, commentData.getNwcNwId(), userId);
    }

    /**
     * This function returns NewsEditModel object by specified record ID.
     * 
     * @param conn - Connection object used for database connection
     * @param recordId - record ID by which NewsEditModel object will be returned
     * @return NewsEditModel object with filled fields by specified record ID
     * @throws Exception if an error occurs
     */
    public NewsEditModel view(Connection conn, Double recordId, Double userId) throws Exception {

        this.checkIsFormActionAssigned(conn, FormBase.ACTION_READ);

        SprNewsDAO loadedData = null;
        NewsEditModel returnData = new NewsEditModel();

        if (recordId != null) {
            loadedData = sprNewsDBService.loadRecord(conn, recordId);
            returnData.setNwId(loadedData.getNw_id());
            returnData.setNwTitle(loadedData.getNw_title());
            returnData.setNwText(loadedData.getNw_text());
            returnData.setNwSummary(loadedData.getNw_summary());
            returnData.setNwType(loadedData.getNw_type());
            returnData.setNwPublished(loadedData.getNw_publication_date());
            List<SprFile> attachments = getNewsFiles(conn, recordId);
            returnData.setAttachment(attachments);
            List<NewsCommentModel> comments = getNewsComments(conn, recordId, userId);
            returnData.setComments(comments);
        }
        return returnData;
    }

    /** 
    * Get all news files belonging to the specified news.
    * 
    * @param conn database connection
    * @param recordId news ID 
    * @return list of news files objects
    * @throws Exception
    */
    private List<SprFile> getNewsFiles(Connection conn, Double recordId) throws Exception {

        List<SprFile> attachments = new ArrayList<>();
        List<SprNewsFilesDAO> data = sprNewsFilesDBService.loadRecordsByParams(conn, "nwf_nw_id = ?", new SelectParamValue(recordId));

        if (!data.isEmpty()) {
            for (SprNewsFilesDAO row : data) {
                SprFile attachment = new SprFile();
                SprFilesDAO file = sprFilesDBService.loadRecordByParams(conn, "fil_id = ?", new SelectParamValue(row.getNwf_fil_id()));
                attachment.setFil_content_type(file.getFil_content_type());
                attachment.setFil_key(fileStorageService.encryptFileKey(file.getFil_key()));
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

}
