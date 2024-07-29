package eu.itreegroup.spark.modules.admin.service;

import java.sql.Connection;
import java.util.Map;

import eu.itreegroup.spark.dao.common.SprBaseDBService;
import eu.itreegroup.spark.modules.admin.dao.SprTemplatesDAO;

public interface SprTemplatesDBService extends SprBaseDBService<SprTemplatesDAO> {

    public static final String PROD_STATUS = "PROD";

    /**
     * Will return all templates text for provided template code and language
     * @param conn - connection to the date base
     * @param templateCode - template code which text should be selected
     * @param language - in which text should be selected
     * @return list of template texts.
     * @throws Exception
     */
    public Map<String, String> getTemplateAllTexts(Connection conn, String templateCode, String language) throws Exception;

    /**
     * Will return template text for provided template code ant template text code.
     * @param conn - connection to the database.
     * @param templateCode - template code which text should be returned
     * @param templateTextCode template text code which text should be returned
     * @param language - in which template text should be selected
     * @return - template text which belongs to the provided template code ant template text code
     * @throws Exception
     */
    public String getTemplateText(Connection conn, String templateCode, String templateTextCode, String language) throws Exception;
}