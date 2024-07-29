package eu.itreegroup.spark.modules.admin.logic.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.dao.DBStatementManager;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprMenuStructuresDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.model.MenuStructureRequest;
import eu.itreegroup.spark.modules.admin.logic.forms.security.SprMenuStructuresTreeBrowseSecurityManager;
import eu.itreegroup.spark.modules.admin.service.SprMenuStructuresDBService;

@Component
public class SprMenuStructuresTree extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprMenuStructuresTree.class);

    private static final String ROLE_ACTION_CACHE = "roleActions";

    private static final String USER_ACTION_CACHE = "userActions";

    private static final String ROLE_MENU_CACHE = "roleMenu";

    private static final String USER_MENU_CACHE = "userMenu";

    private static final String PUBLIC_MENU_CACHE = "publicMenu";

    @Autowired
    DBStatementManager dbStatementManager;

    @Autowired
    BaseControllerJDBC queryController;

    @Autowired
    SprMenuStructuresDBService sprMenuStructuresDBService;

    @Autowired
    SprCacheManager sprCacheManager;

    @Override
    public String getFormName() {
        return "SPR_MENU_STR_TREE";
    }

    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Spark menu structure tree", "Spark menu structure tree");
        addFormActionCRUD();
    }

    public String getRecList(Connection conn, SelectRequestParams params) throws Exception {
        log.debug("Start extract information from db");
        checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        HashMap<String, String> paramList = params.getParamList();
        String language = paramList.get("p_lang");
        String site = paramList.get("p_site");
        String isPublic = paramList.get("p_is_public");
        if (isPublic == null) {
            isPublic = "N";
        }
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(dbStatementManager.getStatementByName("menuStructure"));
        stmt.addSelectParam(language);
        stmt.addSelectParam(site);
        stmt.addSelectParam(isPublic);
        SprMenuStructuresTreeBrowseSecurityManager sqm = new SprMenuStructuresTreeBrowseSecurityManager();
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    public String getNotAssignedMenuPages(Connection conn, SelectRequestParams params) throws Exception {
        log.debug("Start extract information from db");
        this.checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        HashMap<String, String> paramList = params.getParamList();
        String language = paramList.get("p_lang");
        String site = paramList.get("p_site");
        String isPublic = paramList.get("p_is_public");
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("SELECT  ms.mst_id, "//
                + "ms.mst_mst_id, "//
                + "ms.mst_uri as uri, "//
                + "ms.mst_icon as icon, "//
                + "ms.mst_title as title "//
                + "FROM Spr_Menu_Structures ms "//
                + "WHERE ms.mst_order IS NULL "//
                + "AND ms.mst_lang = ? " //
                + "AND ms.mst_site = ? " //
                + "AND ms.mst_is_public = ?");
        stmt.addSelectParam(language);
        stmt.addSelectParam(site);
        stmt.addSelectParam(isPublic);
        SprMenuStructuresTreeBrowseSecurityManager sqm = new SprMenuStructuresTreeBrowseSecurityManager();
        sqm.setFormActions(this.getFormActions(conn));
        return queryController.selectQueryAsJSON(conn, stmt, null, sqm);
    }

    public void updateMenuStructureTree(Connection conn, ArrayList<MenuStructureRequest> records) throws Exception {
        this.checkIsFormActionAssigned(conn, SprMenuStructuresTree.ACTION_UPDATE);
        for (MenuStructureRequest record : records) {
            SprMenuStructuresDAO menuStructure = new SprMenuStructuresDAO();
            menuStructure.setMst_mst_id(record.getMst_mst_id());
            menuStructure.setMst_id(record.getMst_id());
            menuStructure.setMst_order(record.getMst_order());
            menuStructure.setForceUpdate(true);
            sprMenuStructuresDBService.saveRecord(conn, menuStructure);
        }
        clearRelatedCaches(conn);
    }

    public void updateUnassginedPages(Connection conn, ArrayList<Double> records) throws Exception {
        this.checkIsFormActionAssigned(conn, SprMenuStructuresTree.ACTION_UPDATE);
        for (Double record : records) {
            SprMenuStructuresDAO menuStructure = new SprMenuStructuresDAO();
            menuStructure.setForceUpdate(true);
            menuStructure.setMst_id(record);
            menuStructure.setMst_mst_id(null);
            menuStructure.setMst_order(null);
            sprMenuStructuresDBService.saveRecord(conn, menuStructure);
        }
        clearRelatedCaches(conn);
    }

    /**
     * Function will clear with user roles related caches.
     * @param conn - connection to th db.
     */
    private void clearRelatedCaches(Connection conn) {
        try {
            sprCacheManager.clearCache(conn, ROLE_ACTION_CACHE);
            sprCacheManager.clearCache(conn, USER_ACTION_CACHE);
            sprCacheManager.clearCache(conn, ROLE_MENU_CACHE);
            sprCacheManager.clearCache(conn, USER_MENU_CACHE);
            sprCacheManager.clearCache(conn, PUBLIC_MENU_CACHE);
        } catch (Exception ex) {
            log.error("Error while try clear cache", ex);
        }
    }
}
