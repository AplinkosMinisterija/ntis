package lt.project.common;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.model.SprListIdKeyValue;
import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;

@Component
public class ProjectCommonLists {

    public ProjectCommonLists(BaseControllerJDBC queryController) {
        super();
        this.queryController = queryController;

    }

    final private BaseControllerJDBC queryController;

    public ArrayList<SprListIdKeyValue> getUserOrganizations(Connection conn, Double usrId) throws Exception {

        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement(" SELECT org_id as id,"//
                + " org_id as  key,"//
                + " org_name as  display " //
                + " FROM spr_organizations org" //
                + " JOIN spr_org_users  on org_id = ou_org_id" + //
                " where ou_usr_id = ? ");
        stmt.addSelectParam(usrId);
       

        return new ArrayList<SprListIdKeyValue>(queryController.selectQueryAsObjectArray(conn, stmt, SprListIdKeyValue.class));
    }

    public ArrayList<SprListIdKeyValue> getListValuesByListCode(Connection conn, String listCode, Double usrId, String language) throws Exception {

        ArrayList<SprListIdKeyValue> result = null;
        if ("CUST_ORGANIZATIONS".equals(listCode)) {
          return getUserOrganizations(conn, usrId);
        }
        return result;

    }

}
