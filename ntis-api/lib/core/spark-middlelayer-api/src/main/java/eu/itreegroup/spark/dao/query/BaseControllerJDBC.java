package eu.itreegroup.spark.dao.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import eu.itreegroup.spark.dao.DBStatementConstructor;
import eu.itreegroup.spark.dao.common.Utils;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityException;
import eu.itreegroup.spark.dao.query.security.QueryResultSecurityManager;
import eu.itreegroup.spark.dto.BrowseData;
import eu.itreegroup.spark.dto.RecordBase;

@Service
public class BaseControllerJDBC {

    private static final Logger log = LoggerFactory.getLogger(BaseControllerJDBC.class);

    private static final Logger auditLogger = LoggerFactory.getLogger("select.audit");

    private static Set<String> numericTypes = new HashSet<String>(Arrays.asList("numeric", "integer", "number", "int4", "serial"));

    DBStatementConstructor dbStatementConstructor;

    @Value("${app.max.record.in.page:#{1000}}")
    private Integer maxRecordsInPage;

    private Gson gson;

    private BaseControllerJDBC(DBStatementConstructor dbStatementConstructor) {
        this.dbStatementConstructor = dbStatementConstructor;
        gson = new Gson();
    }

    public String selectQueryAsJSON(JdbcTemplate jdbcTemplate, StatementAndParams sp, final SelectRequestParams reqParams,
            @SuppressWarnings("rawtypes") final QueryResultSecurityManager qsm) {
        if (auditLogger.isDebugEnabled()) {
            auditLogger.debug(gson.toJson(sp));
        }
        String selectStmt = sp.getStatemenWithParams();
        log.trace("selectQueryAsJSON statement: " + selectStmt);
        String answer = jdbcTemplate.query(selectStmt, sp, new ResultSetExtractor<String>() {

            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                return parseResultToJSON(rs, reqParams, sp.getJsonColumns(), qsm);
            }
        });
        return answer;
    }

    private String escapeOrderPart(String orderPart, String defaultOrder) {
        String answer = null;
        if (orderPart != null && !"".equals(orderPart)) {
            if (orderPart.matches("^[0-9A-Za-z_$#, ]*$")) {
                answer = " ORDER BY " + orderPart;
            }
        } else {
            answer = defaultOrder;
        }
        return answer;
    }

    /**
     * Function will perform insert/update/delete statement. Function will return count adjusted records.
     * @param conn - connection to the db
     * @param sp - Prepared statement and his parameters, that should be executed in db
     * @return - record count that was effected by provided statement.
     * @throws Exception
     */
    public int adjustRecordsInDB(Connection conn, StatementAndParams sp) throws Exception {
        if (auditLogger.isDebugEnabled()) {
            auditLogger.debug(gson.toJson(sp));
        }
        int recordsAffected = 0;
        String sqlStatement = sp.getStatemenWithParams();
        log.trace("selectQueryAsJSON statement: " + sqlStatement);
        try (PreparedStatement ps = conn.prepareStatement(sqlStatement)) {
            sp.setValues(ps);
            ps.execute();
            recordsAffected = ps.getUpdateCount();
        } catch (Exception ex) {
            log.error("Error while try execute statement: /n" + sp.logData() + "/n", ex);
            throw ex;
        }
        log.trace("Records count was effected: " + recordsAffected);
        return recordsAffected;
    }

    public String selectQueryAsJSON(Connection conn, StatementAndParams sp, SelectRequestParams reqParams, QueryResultSecurityManager<?> qsm) throws Exception {
        if (auditLogger.isDebugEnabled()) {
            auditLogger.debug(gson.toJson(sp));
        }
        String selectStmt = formatStatement(sp, reqParams);
        String answer = null;
        log.trace("selectQueryAsJSON statement: " + selectStmt);
        try (PreparedStatement ps = conn.prepareStatement(selectStmt)) {
            sp.setValues(ps);
            try (ResultSet rez = ps.executeQuery()) {
                answer = parseResultToJSON(rez, reqParams, sp.getJsonColumns(), qsm);
            } catch (Exception ex) {
                throw ex;
            }
        } catch (Exception ex) {
            log.debug("Request param: " + reqParams);
            log.error("Exception on processing sql: " + sp.logData(), ex);
            throw ex;
        }
        return answer;
    }

    /**
     * Function will collect data from db and put to BrowseData object. Function will take provided sql statement and received data
     * put to the BrowseData. 
     * @param <S> - DTO object structure
     * @param conn - connection to the db
     * @param sp - statement object, that holds information about select statement and his parameters.
     * @param reqParams - request parameters received from front
     * @param qsm - security manager that has additional logic, that should be applied for each received row
     * @param clazz - DTO class name
     * @return Extracted data from database. 
     * @throws Exception
     */
    public <S extends RecordBase> BrowseData<S> selectQueryAsDTO(Connection conn, StatementAndParams sp, SelectRequestParams reqParams,
            QueryResultSecurityManager<?> qsm, Class<S> clazz) throws Exception {
        return selectQueryAsDTO(conn, sp, reqParams, qsm, BeanPropertyRowMapper.newInstance(clazz));
    }

    /**
     * Function will collect data from db and put to BrowseData object. Function will take provided sql statement and received data
     * put to the BrowseData. 
     * @param <S> - DTO object structure
     * @param conn - connection to the db
     * @param sp - statement object, that holds information about select statement and his parameters.
     * @param reqParams - request parameters received from front
     * @param qsm - security manager that has additional logic, that should be applied for each received row
     * @param clazz - DTO class name
     * @return Extracted data from database. 
     * @throws Exception
     */
    public <S extends RecordBase> BrowseData<S> selectQueryAsDTO(Connection conn, StatementAndParams sp, SelectRequestParams reqParams,
            @SuppressWarnings("rawtypes") QueryResultSecurityManager qsm, RowMapper<S> mapper) throws Exception {
        if (auditLogger.isDebugEnabled()) {
            auditLogger.debug(gson.toJson(sp));
        }
        String selectStmt = formatStatement(sp, reqParams);
        BrowseData<S> answer = null;
        log.trace("selectQueryAsJSON statement: " + selectStmt);
        try (PreparedStatement ps = conn.prepareStatement(selectStmt)) {
            sp.setValues(ps);
            try (ResultSet rez = ps.executeQuery()) {
                answer = parseResultToDTO(rez, reqParams, qsm, mapper);
            }
        } catch (Exception ex) {
            log.error("Exception on processing sql: " + sp.logData(), ex);
            throw ex;
        }
        return answer;
    }

    private String formatStatement(StatementAndParams sp, SelectRequestParams reqParams) throws Exception {
        String selectStmt = null;
        if (reqParams != null) {
            if (reqParams.getPagingParams() != null) {
                sp.setStatementOrderPart(escapeOrderPart(reqParams.getPagingParams().getOrder_clause(), sp.getStatementOrderPart()));
            }
            selectStmt = sp.getStatemenWithParams();
            if (reqParams.getPagingParams() != null && reqParams.getPagingParams().getPage_size() != null
                    && reqParams.getPagingParams().getSkip_rows() != null) {
                if (reqParams.getPagingParams().getPage_size() > maxRecordsInPage) {
                    reqParams.getPagingParams().setPage_size(Double.valueOf(maxRecordsInPage));
                }
                selectStmt = selectStmt
                        + dbStatementConstructor.getPagingString(reqParams.getPagingParams().getSkip_rows(), reqParams.getPagingParams().getPage_size(), 3);
            }
        } else {
            selectStmt = sp.getStatemenWithParams();
        }
        return selectStmt;

    }

    public String selectQueryAsJson(Connection conn, StatementAndParams sp, @SuppressWarnings("rawtypes") QueryResultSecurityManager qsm) throws Exception {
        return selectQueryAsJSON(conn, sp, null, qsm);
    }

    public String selectQueryAsJSON(Connection conn, StatementAndParams sp) throws Exception {
        return selectQueryAsJSON(conn, sp, null, null);
    }

    public String selectRefCodesAsJSON(Connection conn, StatementAndParams sp) throws Exception {
        if (auditLogger.isDebugEnabled()) {
            auditLogger.debug(gson.toJson(sp));
        }
        String selectStmt = sp.getStatemenWithParams();
        String answer = null;
        log.trace("selectRefCodesAsJSON statement: " + selectStmt);
        try (PreparedStatement ps = conn.prepareStatement(selectStmt)) {
            sp.setValues(ps);
            try (ResultSet rez = ps.executeQuery()) {
                answer = parseRefCodesToJSON(rez);
            } catch (Exception ex) {
                throw ex;
            }
        } catch (Exception ex) {
            log.error("Exception on processing sql:\n" + sp.logData(), ex);
            throw ex;
        }
        return answer;
    }

    /**
     *
     * @deprecated
     * This method is no longer acceptable to extract data from db.
     * <p> Use {@link selectQueryAsObjectArrayList(Connection conn, StatementAndParams sp, Class<S> clazz)} instead.
     */
    @Deprecated
    public <S> Vector<S> selectQueryAsObjectArray(Connection conn, StatementAndParams sp, Class<S> clazz) throws Exception {
        return selectQueryAsObjectArray(conn, sp, new Data2ObjectProcessor<S>(clazz));
    }

    public <S> List<S> selectQueryAsObjectArrayList(Connection conn, StatementAndParams sp, Class<S> clazz) throws Exception {
        return selectQueryAsObjectArrayList(conn, sp, new Data2ObjectProcessor<S>(clazz));
    }

    public <S> List<S> selectQueryAsObjectArrayList(Connection conn, StatementAndParams sp, Data2ObjectProcessor<S> dp) throws Exception {
        if (auditLogger.isDebugEnabled()) {
            auditLogger.debug(gson.toJson(sp));
        }
        String selectStmt = sp.getStatemenWithParams();
        try (PreparedStatement ps = conn.prepareStatement(selectStmt)) {
            sp.setValues(ps);
            try (ResultSet rez = ps.executeQuery()) {
                return dp.getClassData(rez);
            }
        } catch (SQLException ex) {
            log.error("Exception on processing sql:\n {} ", sp.logData(), ex);
            throw ex;
        }
    }

    /**
     *
     * @deprecated
     * This method is no longer acceptable to extract data from db.
     * <p> Use {@link selectQueryAsObjectArrayList(Connection conn, StatementAndParams sp, Data2ObjectProcessor<S> dp)} instead.
     */
    @Deprecated
    public <S> Vector<S> selectQueryAsObjectArray(Connection conn, StatementAndParams sp, Data2ObjectProcessor<S> dp) throws Exception {
        return new Vector<S>(selectQueryAsObjectArrayList(conn, sp, dp));
    }

    public List<HashMap<String, String>> selectQueryAsDataArrayList(Connection conn, StatementAndParams sp) throws SQLException {
        String selectStmt = sp.getStatemenWithParams();
        if (auditLogger.isDebugEnabled()) {
            auditLogger.debug(gson.toJson(sp));
        }
        List<HashMap<String, String>> answer = null;
        log.trace("selectQueryAsDataArry statement: " + selectStmt);
        try (PreparedStatement ps = conn.prepareStatement(selectStmt)) {
            sp.setValues(ps);
            try (ResultSet rez = ps.executeQuery()) {
                answer = parseResultToDataArray(rez);
            }
        } catch (SQLException ex) {
            log.error("Exception on processing sql:\n " + sp.logData(), ex);
            throw ex;
        }
        return answer;
    }

    protected List<HashMap<String, String>> parseResultToDataArray(ResultSet rs) throws SQLException {
        List<HashMap<String, String>> answer = new ArrayList<HashMap<String, String>>();
        ResultSetMetaData metaData = rs.getMetaData();
        while (rs.next()) {
            HashMap<String, String> row = new HashMap<String, String>();
            for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
                String value = rs.getString(i);
                if ("bpchar".equalsIgnoreCase(metaData.getColumnTypeName(i))) {
                    value = Utils.removeWhitespacesFromEnd(value);
                }
                if (value != null) {
                    row.put(metaData.getColumnName(i).toLowerCase(), value);
                }
            }
            answer.add(row);
        }
        return answer;
    }

    @SuppressWarnings("unchecked")
    private String parseResultToJSON(ResultSet rs, SelectRequestParams reqParams, List<String> jsonColumns,
            @SuppressWarnings("rawtypes") QueryResultSecurityManager qsm) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        boolean dataExists = false;
        double pageSize = 0;
        double skipRows = 0;
        boolean reqParamsExists = false;
        if (reqParams != null && reqParams.getPagingParams() != null && reqParams.getPagingParams().getPage_size() != null) {
            pageSize = reqParams.getPagingParams().getPage_size().doubleValue();
            skipRows = reqParams.getPagingParams().getSkip_rows().doubleValue();
            reqParamsExists = true;
        }
        int count = 1;
        StringBuffer answer = new StringBuffer("{");
        StringBuffer recAnswer = new StringBuffer();
        if (qsm != null) {
            answer.append("\"formActions\":");
            answer.append(gson.toJson(qsm.getFormActions()));
            answer.append(", ");
        }
        answer.append("\"data\":[");
        String elementStart = "{";
        int columnCount = metaData.getColumnCount();
        String columnNames[] = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1).toLowerCase();
        }
        String columnTypes[] = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnTypes[i] = metaData.getColumnTypeName(i + 1).toLowerCase();
        }
        Map<String, String> row = new HashMap<String, String>();
        RecordData recordData = new RecordData();
        while (rs.next() && (!reqParamsExists || pageSize >= count)) {
            try {
                if (!reqParamsExists || pageSize >= count) {
                    recAnswer.append(elementStart);
                    recAnswer.append("\"skip\":");
                    recAnswer.append(skipRows + count);
                    recAnswer.append(", ");
                    row.put("skip", "" + count);
                    for (int i = 1; i < columnCount + 1; i++) {
                        String value = rs.getString(i);
                        if (value == null) {
                            value = "";
                        } else {
                            if ("bpchar".equals(columnTypes[i - 1])) {
                                value = Utils.removeWhitespacesFromEnd(value);
                            }
                        }
                        row.put(columnNames[i - 1], value);
                        recordData.add(
                                new ColumnData(columnNames[i - 1], value, numericTypes.contains(columnTypes[i - 1]), jsonColumns.contains(columnNames[i - 1])));
                    }
                    if (qsm != null) {
                        qsm.manageRecordData(recordData);
                    }
                    recAnswer.append(recordData.toString());
                    if (qsm != null) {
                        recAnswer.append(", ");
                        recAnswer.append(qsm.getRowActionsJSON(row));
                    }
                    answer.append(recAnswer);
                    recordData.clearData();
                    elementStart = "}, {";
                    count++;
                    dataExists = true;
                }
            } catch (QueryResultSecurityException ex) {
                log.debug("Security exception. Data record removed from list.");
            }
            recAnswer.setLength(0);
            row.clear();
        }
        if (rs.isAfterLast()) {
            count--;
        }
        if (dataExists) {
            answer.append("}");
        }
        answer.append("]");
        if (reqParams != null && reqParams.getPagingParams() != null) {
            answer.append(",\"paging\":{\"cnt\":");
            answer.append(reqParams.getPagingParams().getSkip_rows() + count);
            answer.append(",\"skip_rows\":");
            answer.append(reqParams.getPagingParams().getSkip_rows());
            answer.append(",\"page_size\":");
            answer.append(reqParams.getPagingParams().getSkip_rows());
            answer.append(",\"order_clause\":\"");
            answer.append(reqParams.getPagingParams().getOrder_clause());
            answer.append("\",\"sum_values\":");
            answer.append(reqParams.getPagingParams().getSum_values());
            answer.append("}");
        }
        answer.append("}");
        String answerStr = answer.toString();
        log.debug(answerStr);
        return answerStr;
    }

    @SuppressWarnings("unchecked")
    private <S extends RecordBase> BrowseData<S> parseResultToDTO(ResultSet rs, SelectRequestParams reqParams, QueryResultSecurityManager<?> qsm,
            RowMapper<S> mapper) throws Exception {
        BrowseData<S> answer = new BrowseData<S>();
        answer.setPaging(reqParams.getPagingParams());
        if (qsm != null) {
            answer.setFormActions(qsm.getFormActions());
        }
        List<RecordBase> data = new ArrayList<RecordBase>();
        long skipRows = 0;
        double pageSize = 0;
        boolean pagingExists = false;
        if (answer.getPaging() != null) {
            skipRows = Utils.getLong(answer.getPaging().getSkip_rows());
            pageSize = answer.getPaging().getPage_size();
            pagingExists = true;
        }

        long count = 1;
        while ((!pagingExists || pageSize >= count) && rs.next()) {
            RecordBase obj = mapper.mapRow(rs, (int) count);
            try {
                if (qsm != null) {
                    qsm.applySecurityLogicForDto(obj);
                }
                obj.setSkip(skipRows + count);
                data.add(obj);
                count++;
            } catch (QueryResultSecurityException ex) {
                log.debug("Record do nto pass security validation rule. : {0}", obj);
            }
        }
        if (rs.isAfterLast()) {
            count--;
        }
        if (pagingExists) {
            answer.getPaging().setCnt((double) (skipRows + count));
        }

        answer.setData((List<S>) data);
        return answer;
    }

    private String parseRefCodesToJSON(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int count = 0;
        StringBuffer answer = new StringBuffer("[");

        String elementStart = "{";
        int columnCount = metaData.getColumnCount();
        String columnNames[] = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnName(i + 1).toLowerCase();
        }
        String columnTypes[] = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnTypes[i] = metaData.getColumnTypeName(i + 1).toLowerCase();
        }
        while (rs.next()) {
            count++;
            answer.append(elementStart);
            for (int i = 1; i < columnCount + 1; i++) {
                String value = rs.getString(i);
                if (value == null) {
                    value = "";
                } else {
                    if ("bpchar".equals(columnTypes[i - 1])) {
                        value = Utils.removeWhitespacesFromEnd(value);
                    }
                }
                answer.append("\"");
                answer.append(columnNames[i - 1]);
                answer.append("\":");
                answer.append(JSONObject.quote(value));
                if (i < columnCount) {
                    answer.append(", ");
                }
            }
            elementStart = "}, {";
        }
        if (count > 0) {
            answer.append("}");
        }
        answer.append("]");
        String answerStr = answer.toString();
        log.debug(answerStr);
        return answerStr;
    }

}
