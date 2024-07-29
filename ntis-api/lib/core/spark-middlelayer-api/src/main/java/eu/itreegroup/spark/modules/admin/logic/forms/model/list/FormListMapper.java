package eu.itreegroup.spark.modules.admin.logic.forms.model.list;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FormListMapper implements RowMapper<FormListRec> {

    @Override
    public FormListRec mapRow(ResultSet rs, int rowNum) throws SQLException {
        FormListRec answer = new FormListRec();
        answer.setFrm_id(rs.getDouble("frm_id"));
        answer.setFrm_code(rs.getString("frm_code"));
        answer.setFrm_name(rs.getString("frm_name"));
        answer.setFrm_description(rs.getString("frm_description"));
        answer.setRec_create_timestamp(rs.getString("rec_create_timestamp"));
        return answer;
    }

}
