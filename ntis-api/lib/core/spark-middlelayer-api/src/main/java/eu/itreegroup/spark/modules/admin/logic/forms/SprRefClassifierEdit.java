package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.s2.server.rest.S2RestRequestContext;
import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectParamValue;
import eu.itreegroup.spark.modules.admin.dao.SprRefCodesDAO;
import eu.itreegroup.spark.modules.admin.dao.SprRefTranslationsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.CodeDictionaryModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RefCodesClassifierObj;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RefCodesTranslations;
import eu.itreegroup.spark.modules.admin.logic.forms.model.RefTranslationsObj;
import eu.itreegroup.spark.modules.admin.service.SprRefCodesDBService;
import eu.itreegroup.spark.modules.admin.service.SprRefTranslationDBService;

@Component
public class SprRefClassifierEdit extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprPropertiesBrowse.class);

    @Autowired
    SprRefTranslationDBService translationDBService;

    @Autowired
    SprRefCodesDBService codesDBService;

    @Autowired
    protected S2RestRequestContext<BackendUserSession> requestContext;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    DBStatementManager dbStatementManager;

    @Override
    public String getFormName() {
        return "SPR_REF_CLASSIFIER_EDIT";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark classifier edit", "Spark classifier edit");
        addFormActionCRUD();
    }

    public ArrayList<RefCodesClassifierObj> save(Connection conn, ArrayList<RefCodesClassifierObj> record) throws Exception {
        for (int i = 0; i < record.size(); i++) {
            RefCodesClassifierObj row = record.get(i);
            SprRefCodesDAO rfcDAO = new SprRefCodesDAO();
            rfcDAO.setRfc_id(row.getRfc_id());
            rfcDAO.setRfc_domain(row.getRfc_domain());
            rfcDAO.setRfc_code(row.getRfc_code());
            rfcDAO.setRfc_meaning(row.getRfc_meaning());
            rfcDAO.setRfc_description(row.getRfc_description());
            rfcDAO.setRfc_parent_domain(row.getRfc_parent_domain());
            rfcDAO.setRfc_parent_domain_code(row.getRfc_parent_domain_code());
            rfcDAO.setRfc_date_from(java.util.Calendar.getInstance().getTime());
            rfcDAO.setRfc_order(row.getRfc_order());
            rfcDAO = codesDBService.saveRecord(conn, rfcDAO);
            ArrayList<RefTranslationsObj> childs = row.getRft();
            for (int j = 0; j < childs.size(); j++) {
                RefTranslationsObj child = childs.get(j);
                SprRefTranslationsDAO rftDAO = translationDBService.newRecord();
                rftDAO.setRft_id(child.getRft_id());
                rftDAO.setRft_lang(child.getRft_lang());
                rftDAO.setRft_display_code(child.getRft_display_code());
                rftDAO.setRft_description(child.getRft_description());
                rftDAO.setRft_rfc_id(rfcDAO.getRfc_id());
                translationDBService.saveRecord(conn, rftDAO);
            }
            removeFromCache(conn, rfcDAO.getRfc_domain(), null);
        }
        return record;
    }

    public SprRefCodesDAO getRefCode(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        SprRefCodesDAO refCode = new SprRefCodesDAO();
        if ("new".equalsIgnoreCase(recordIdentifier.getId())) {
            log.debug("Will create new record");
            SprRefCodesDAO sprRefCodesDAO = codesDBService.newRecord();
            System.out.println(sprRefCodesDAO);
            refCode.copyValues(sprRefCodesDAO);
            refCode.setFormActions(getFormActions(conn));
        } else {
            log.debug("Will return record by id: " + recordIdentifier.getId());
            this.checkIsFormActionAssigned(conn, SprRefClassifierEdit.ACTION_READ);
            SprRefCodesDAO sprRefCodesDAO = codesDBService.loadRecord(conn, Utils.getDouble(recordIdentifier.getId()));
            refCode.copyValues(sprRefCodesDAO);
            refCode.setFormActions(getFormActions(conn));
        }
        return refCode;
    }

    public CodeDictionaryModel loadDictionary(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        CodeDictionaryModel answerObj = null;
        answerObj = codesDBService.loadDictionary(conn, Double.parseDouble(recordIdentifier.getId()));
        return answerObj;
    }

    public SprRefCodesDAO setRefCode(Connection conn, SprRefCodesDAO refCode) throws Exception {
        this.checkIsFormActionAssigned(conn, refCode.getRecordIdentifier() == null ? SprRefClassifierEdit.ACTION_CREATE : SprRefClassifierEdit.ACTION_UPDATE);
        refCode = codesDBService.saveRecord(conn, refCode);
        removeFromCache(conn, refCode.getRfc_domain(), null);
        return refCode;
    }

    public void setRefCodes(Connection conn, List<SprRefCodesDAO> refCodes) throws Exception {
        this.checkIsFormActionAssigned(conn, SprRefClassifierEdit.ACTION_CREATE);
        for (SprRefCodesDAO row : refCodes) {
            SprRefCodesDAO loadedData = codesDBService.loadRecordByParams(conn, " WHERE rfc_code = ? AND rfc_domain = ?",
                    new SelectParamValue(row.getRfc_code()), new SelectParamValue(row.getRfc_domain()));
            if (loadedData != null) {
                loadedData.setRfc_date_from(row.getRfc_date_from());
                loadedData.setRfc_date_to(row.getRfc_date_to());
                loadedData.setRfc_description(row.getRfc_description());
                loadedData.setRfc_meaning(row.getRfc_meaning());
                loadedData.setRfc_order(row.getRfc_order());
                loadedData.setRfc_rfd_id(row.getRfc_rfd_id());
                codesDBService.saveRecord(conn, loadedData);
            } else {
                codesDBService.saveRecord(conn, row);
            }
            removeFromCache(conn, row.getRfc_domain(), null);
        }
    }

    public SprRefTranslationsDAO saveRft(Connection conn, SprRefTranslationsDAO rft) throws Exception {
        this.checkIsFormActionAssigned(conn, rft.getRft_id() == null ? SprRefClassifierEdit.ACTION_CREATE : SprRefClassifierEdit.ACTION_UPDATE);
        rft = translationDBService.saveRecord(conn, rft);
        SprRefCodesDAO rfcDAO = codesDBService.loadRecord(conn, rft.getRft_rfc_id());
        removeFromCache(conn, rfcDAO.getRfc_domain(), rft.getRft_lang());
        return rft;
    }

    public RefCodesTranslations saveCodeTranslation(Connection conn, RefCodesTranslations record) throws Exception {
        SprRefCodesDAO rfcDAO = codesDBService.newRecord();
        rfcDAO.setRfc_id(record.getRfc_id());
        rfcDAO.setRfc_domain(record.getRfc_domain());
        rfcDAO.setRfc_code(record.getRfc_code());
        rfcDAO.setRfc_meaning(record.getRfc_meaning());
        rfcDAO.setRfc_description(record.getRfc_description());
        rfcDAO.setRfc_parent_domain(record.getRfc_parent_domain());
        rfcDAO.setRfc_parent_domain_code(record.getRfc_parent_domain_code());
        rfcDAO.setRfc_date_from(java.util.Calendar.getInstance().getTime());
        rfcDAO.setRfc_order(record.getRfc_order());
        rfcDAO = codesDBService.saveRecord(conn, rfcDAO);
        SprRefTranslationsDAO rftDAO = new SprRefTranslationsDAO();
        rftDAO.setRft_id(record.getRft_id());
        rftDAO.setRft_lang(record.getRft_lang());
        rftDAO.setRft_display_code(record.getRft_display_code());
        rftDAO.setRft_description(record.getRft_description());
        rftDAO.setRft_rfc_id(rfcDAO.getRfc_id());
        translationDBService.saveRecord(conn, rftDAO);
        return record;
    }

    private void removeFromCache(Connection conn, String refCode, String language) throws Exception {
        if (language == null) {
            List<IdKeyValuePair> languageList = codesDBService.getRefCodesByName(conn, "SPR_LANGUAGE", null);
            for (int i = 0; i < languageList.size(); i++) {
                codesDBService.clearCacheForRefCodesManager(refCode, languageList.get(i).getId());
                codesDBService.clearCasheForRefCodesManagerJSON(refCode, languageList.get(i).getId());
            }
        } else {
            codesDBService.clearCacheForRefCodesManager(refCode, language);
            codesDBService.clearCasheForRefCodesManagerJSON(refCode, language);
        }
    }

    public void delete(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprRefClassifier.ACTION_DELETE);
        SprRefCodesDAO rfcDAO = codesDBService.loadRecord(conn, Utils.getDouble(recordIdentifier.getId()));
        codesDBService.deleteRecord(conn, Utils.getDouble(recordIdentifier.getId()));
        removeFromCache(conn, rfcDAO.getRfc_domain(), null);
    }

    public void deleteRft(Connection conn, RecordIdentifier recordIdentifier) throws NumberFormatException, Exception {
        this.checkIsFormActionAssigned(conn, SprRefClassifier.ACTION_DELETE);
        SprRefTranslationsDAO rftDAO = translationDBService.loadRecord(conn, Utils.getDouble(recordIdentifier.getId()));
        SprRefCodesDAO rfcDAO = codesDBService.loadRecord(conn, rftDAO.getRft_rfc_id());
        translationDBService.deleteRecord(conn, Utils.getDouble(recordIdentifier.getId()));
        removeFromCache(conn, rfcDAO.getRfc_domain(), rftDAO.getRft_lang());
    }
}