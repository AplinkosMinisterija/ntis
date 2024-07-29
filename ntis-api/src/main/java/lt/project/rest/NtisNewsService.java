package lt.project.rest;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.dao.SprNewsFilesDAO;
import eu.itreegroup.spark.modules.admin.rest.SparkNewsService;
import eu.itreegroup.spark.modules.admin.service.SprNewsFilesDBService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.project.ntis.logic.forms.NtisSprNewsBrowse;
import lt.project.ntis.logic.forms.NtisSprNewsEdit;
import lt.project.ntis.logic.forms.NtisSprNewsView;
import lt.project.ntis.logic.forms.model.NtisNewsEditModel;

/**
 * @description Klasė skirta gauti/saugoti NTIS naujienų duomenis
 * 
 */
@RestController
@RequestMapping("/ntis-news")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisNewsService extends SparkNewsService {

    @Autowired
    NtisSprNewsEdit ntisNewsEdit;

    @Autowired
    NtisSprNewsBrowse ntisNewsBrowse;

    @Autowired
    NtisSprNewsView ntisNewsView;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    SprNewsFilesDBService sprNewsFilesDBService;

    @Autowired
    SprFilesDBService sprFilesDBService;

    /**
     * @description This method handles the GET request for viewing a news item from the database by its id and returns a JSON response with the data and increments its view count.
     * @param {Double} id - The double value that represents the unique identifier of the news item to be viewed.
     * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON viewed news item data with the updated view count.
     * @throws {Exception} If any error occurs during the request processing or data retrieval or update.
     */
    @PostMapping("/view-nw/{id}")
    public ResponseEntity<NtisNewsEditModel> viewRec(@RequestBody SelectRequestParams params) throws Exception {
        Double userId = this.requestContext.getUserSession().getSes_usr_id();
        return okResponse(ntisNewsView.view(this.getDBConnection(), params, userId, false, this.requestContext.getUserSession().getSes_language()));
    }

    /**
    * @description This method handles the GET request for viewing a news item from the database by its id and returns a JSON response with the data and
    increments its view count.
    * @param {Double} id - The double value that represents the unique identifier of the news item to be viewed.
    * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON viewed news item data with the updated view count.
    * @throws {Exception} If any error occurs during the request processing or data retrieval or update.
    */
    @PostMapping("/public/view-nw/{id}")
    public ResponseEntity<NtisNewsEditModel> viewPublic(@RequestBody SelectRequestParams params) throws Exception {
        Double userId = this.requestContext.getUserSession().getSes_usr_id();
        return okResponse(ntisNewsView.view(this.getDBConnection(), params, userId, true, null));
    }

    /**
     * @description This method handles the GET request for getting a news item from the database by its id and returns a JSON response with the data.
     * @param {Double} id - The double value that represents the unique identifier of the news item.
     * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON news item data.
     * @throws {Exception} If any error occurs during the request processing or data retrieval.
     */
    @GetMapping("/get-nw/{id}")
    public ResponseEntity<NtisNewsEditModel> getRec(@PathVariable Double id) throws Exception {
        return okResponse(ntisNewsEdit.get(this.getDBConnection(), id, this.requestContext.getUserSession().getSes_language()));
    }

    /**
     * @description This method handles the POST request for saving a news item to the database and returns a JSON response with the updated data.
     * @param {NewsEditModel} formData - The object that contains the news item data to be saved.
     * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON saved news item data.
     * @throws {Exception} If any error occurs during the request processing or data validation.
     */
    @PostMapping("/save-nw")
    public ResponseEntity<NtisNewsEditModel> save(@RequestBody NtisNewsEditModel formData) throws Exception {
        return okResponse(ntisNewsEdit.save(this.getDBConnection(), formData, this.requestContext.getUserSession().getSes_language()));
    }

    /**
     * @description This method handles the POST request for getting a list of news items that match the given parameters and returns a JSON response.
     * @param {SelectRequestParams} params - The object that contains the paging and search parameters.
     * @return {ResponseEntity<String>} A response entity with the JSON result of the search, which includes the total count and the list of news items.
     * @throws {Exception} If any error occurs during the request processing.
     */
    @PostMapping("/get-news-list")
    public ResponseEntity<String> getRecList(@RequestBody SelectRequestParams params) throws Exception {
        String language = this.requestContext.getUserSession().getSes_language();
        return okResponse(ntisNewsBrowse.getRecList(this.getDBConnection(), params, language, false));
    }

    /**
     * @description This method handles the POST request for getting a list of news items that match the given parameters and returns a JSON response.
     * @param {SelectRequestParams} params - The object that contains the paging and search parameters.
     * @return {ResponseEntity<String>} A response entity with the JSON result of the search, which includes the total count and the list of news items.
     * @throws {Exception} If any error occurs during the request processing.
     */
    @PostMapping("/public/get-news-list")
    public ResponseEntity<String> getPublicList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisNewsBrowse.getRecList(this.getDBConnection(), params, null, true));
    }

    // Page templates
    @PostMapping("/get-page-template")
    public ResponseEntity<NtisNewsEditModel> getPageTemplate(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisNewsView.viewPageTemplate(this.getDBConnection(), params, this.requestContext.getUserSession().getSes_language()));
    }

    /**
     * @description This method handles the GET request for getting a news file from the database by its id and returns response with the data.
     * @param {Double} id - The double value that represents the unique identifier of the news item.
     * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON news item data.
     * @throws Exception 
     * @throws {Exception} If any error occurs during the request processing or data retrieval.
     */
    @GetMapping("public/get-file/{id}")
    public ResponseEntity<InputStreamResource> getAttachment(@PathVariable Double id) throws Exception {
        if (id != null) {
            SprNewsFilesDAO nwfile = sprNewsFilesDBService.loadRecord(getDBConnection(), id);
            SprFilesDAO file = sprFilesDBService.loadRecord(getDBConnection(), nwfile.getNwf_fil_id());
            InputStream fileContent = fileStorageService.getFileByFileKey(file.getFil_key(), this.getDBConnection());
            HttpHeaders headers = new HttpHeaders();
            headers.add(X_S2_STATUS, OK);
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(fileContent));
        } else {
            return okResponse(null);
        }
    }

}
