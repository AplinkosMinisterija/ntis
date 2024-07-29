package eu.itreegroup.spark.app.job.request.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;

import eu.itreegroup.spark.app.job.request.JobRequest;
import eu.itreegroup.spark.enums.Languages;
import eu.itreegroup.spark.modules.admin.dao.SprJobDefinitionsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestArgsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestAuthorsDAO;
import eu.itreegroup.spark.modules.admin.dao.SprJobRequestsDAO;
import eu.itreegroup.spark.modules.admin.service.SprJobDefinitionsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestArgsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestAuthorsDBService;
import eu.itreegroup.spark.modules.admin.service.SprJobRequestsDBService;

public abstract class JobRequestImpl implements JobRequest, BeanNameAware {

    private static final Logger log = LoggerFactory.getLogger(JobRequestImpl.class);

    @Autowired
    SprJobDefinitionsDBService sprJobDefinitionsDBService;

    @Autowired
    SprJobRequestsDBService sprJobRequestDBService;

    @Autowired
    SprJobRequestAuthorsDBService sprJobRequestAuthorsDBService;

    @Autowired
    SprJobRequestArgsDBService sprJobRequestArgsDBService;

    protected String beanName;

    protected SprJobDefinitionsDAO requestType;

    @Override
    public String getName() {
        return this.beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public SprJobDefinitionsDAO getJobDefinitionsType(Connection conn) throws Exception {
        if (requestType == null) {
            requestType = sprJobDefinitionsDBService.loadRecordByCode(conn, getType(), getCode());
        }
        return requestType;
    }

    public SprJobDefinitionsDAO getJobDefinitionsType(Connection conn, String jobDefinitionCode) throws Exception {
        return sprJobDefinitionsDBService.loadRecordByCode(conn, getType(), jobDefinitionCode);
    }

    private Double createJobRequest(Connection conn, SprJobDefinitionsDAO requestType, Double userId, Languages lang, Map<String, String> params)
            throws Exception {
        SprJobRequestsDAO request = sprJobRequestDBService.newRecord();
        request.setJrq_type(requestType.getJde_type());
        request.setJrq_jde_id(requestType.getJde_id());
        // if result type was not set, will be used default output type.
        if (getResultType() != null) {
            request.setJrq_result_type(getResultType().getCode());
        } else {
            request.setJrq_result_type(requestType.getJde_default_output_type());
        }
        request.setJrq_lang(lang.getCode());
        if (SprJobDefinitionsDBService.EXECUTOR_TYPE_MANUAL.equals(getExecutorType())) {
            request.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_PROCESSING);
        } else {
            request.setJrq_status(SprJobRequestsDBService.REQUEST_STATUS_WAITING);
        }
        request.setJrq_usr_id(userId);
        request = sprJobRequestDBService.saveRecord(conn, request);
        if (params != null) {
            for (String paramName : params.keySet()) {
                SprJobRequestArgsDAO arg = sprJobRequestArgsDBService.newRecord();
                arg.setJra_jrq_id(request.getJrq_id());
                arg.setJra_name(paramName);
                arg.setJra_value(params.get(paramName));
                arg = sprJobRequestArgsDBService.saveRecord(conn, arg);
            }
        }
        return request.getJrq_id();
    }

    /**
     * Method will create job request for logic execution. Function will create record in table spr_job_request and if job has arguments they will
     * be saved into spr_job_request_args table
     * @param conn
     * @param userId
     * @param lang
     * @param params
     * @return
     * @throws Exception
     */
    public Double createJobRequest(Connection conn, Double userId, Languages lang, Map<String, String> params) throws Exception {
        return createJobRequest(conn, getJobDefinitionsType(conn), userId, lang, params);
    }

    /**
     * Function will create request for provided job definition code.
     * @param conn - connection to the db 
     * @param jobRequestCode - job request definition code for witch should be created request
     * @param userId - user id, that initiate record creation
     * @param lang - language in which should be performed actions
     * @param params - list of parameters
     * @return created request identifier.
     * @throws Exception
     */
    public Double createJobRequest(Connection conn, String jobRequestCode, Double userId, Languages lang, Map<String, String> params) throws Exception {
        return createJobRequest(conn, getJobDefinitionsType(conn, jobRequestCode), userId, lang, params);
    }

    /**
     * Method will create job request with parameters and with information who will are interested on this request (request Authors). Funcion will 
     * call method createJobRequest
     * @param conn - connection to the DB
     * @param userId - user identifier who initiate request
     * @param orgId - user organization id 
     * @param lang - language code
     * @param params - list of parameters that will be used by created request.
     * @return created request identifier;
     * @throws Exception
     */
    public Double createJobRequestWithAuthors(Connection conn, Double userId, Double orgId, Languages lang, Map<String, String> params) throws Exception {
        Double requestId = createJobRequest(conn, userId, lang, params);
        SprJobRequestAuthorsDAO authorsDAO = sprJobRequestAuthorsDBService.newRecord();
        authorsDAO.setJrt_jrq_id(requestId);
        authorsDAO.setJrt_org_id(orgId);
        authorsDAO.setJrt_usr_id(userId);
        sprJobRequestAuthorsDBService.saveRecord(conn, authorsDAO);
        return requestId;
    }

    /**
     * Function will load all request arguments to the hash table structure.
     * @param conn - used for data access from DB
     * @param requestId - reference to the request which arguments should be loaded
     * @return request arguments.
     */
    @Override
    public Map<String, String> loadJobRequestParams(Connection conn, Double requestId) {
        Map<String, String> answer = new HashMap<String, String>();
        if (requestId != null) {
            String stmtStr = "select jra_name, jra_value from spr_job_request_args where jra_jrq_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(stmtStr)) {
                pstmt.setDouble(1, requestId);
                try (ResultSet rez = pstmt.executeQuery()) {
                    while (rez.next()) {
                        answer.put(rez.getString(1), rez.getString(2));
                    }
                } catch (SQLException e) {
                    log.error("Error on execution statment: " + stmtStr + " with parameter: " + requestId, e);
                }
            } catch (SQLException e1) {
                log.error("Error on creation statment: " + stmtStr, e1);
            }
        }
        return answer;
    }
}
