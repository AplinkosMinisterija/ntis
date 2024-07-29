package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.itreegroup.spark.dao.query.BaseControllerJDBC;
import eu.itreegroup.spark.dao.query.StatementAndParams;
import eu.itreegroup.spark.modules.admin.dao.SprTemplatesDAO;
import eu.itreegroup.spark.modules.admin.service.gen.SprTemplatesDBServiceGen;

@Service
public class SprTemplatesDBServiceImpl extends SprTemplatesDBServiceGen implements SprTemplatesDBService {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(SprTemplatesDBServiceImpl.class);

    @Autowired
    BaseControllerJDBC baseControllerJDBC;

    public SprTemplatesDBServiceImpl() {
    }

    @Override
    public SprTemplatesDAO newRecord() {
        SprTemplatesDAO daoObject = super.newRecord();
        return daoObject;
    }

    protected Hashtable<String, String> getTemplateTexts(Connection conn, String templateCode, String tamplateTextCode, String language) throws Exception {
        Hashtable<String, String> answer = new Hashtable<String, String>();
        StatementAndParams stmt = new StatementAndParams();
        stmt.setStatement("select tt.tmt_code, tt.tmt_text" //
                + "          from spr_templates t " //
                + "    inner join spr_template_texts tt on t.tml_id = tt.tmt_tml_id ");
        stmt.addParam4WherePart(" t.tml_code = ? ", templateCode);
        stmt.addParam4WherePart(" t.tml_status = ? ", PROD_STATUS);
        stmt.addParam4WherePart(" tt.tmt_code = ? ", tamplateTextCode);
        stmt.addParam4WherePart(" tt.tmt_lang = ?", language);
        List<HashMap<String, String>> data = baseControllerJDBC.selectQueryAsDataArrayList(conn, stmt);
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                HashMap<String, String> record = data.get(i);
                answer.put(record.get("tmt_code"), record.get("tmt_text"));
            }
        }
        return answer;
    }

    @Override
    public Hashtable<String, String> getTemplateAllTexts(Connection conn, String templateCode, String language) throws Exception {
        return getTemplateTexts(conn, templateCode, null, language);
    }

    @Override
    public String getTemplateText(Connection conn, String templateCode, String templateTextCode, String language) throws Exception {
        Hashtable<String, String> templateData = getTemplateTexts(conn, templateCode, templateTextCode, language);
        return templateData.get(templateTextCode);
    }
}