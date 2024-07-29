package eu.itreegroup.spark.modules.admin.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.dao.SprNewsCommentsDAO;
import eu.itreegroup.spark.modules.admin.logic.forms.SprNewsBrowse;
import eu.itreegroup.spark.modules.admin.logic.forms.SprNewsEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprNewsView;
import eu.itreegroup.spark.modules.admin.logic.forms.SprProfileSettings;
import eu.itreegroup.spark.modules.admin.logic.forms.model.NewsCommentModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.NewsEditModel;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;

/**
 * @description This class is a REST controller that handles requests related to spark news data.
 * 
 * @see S2RestAuthService
 */
@RestController
@RequestMapping("/spark-news")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SparkNewsService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    // These are the fields that store the autowired dependencies for profile settings, news browse, news edit and news view.
    @Autowired
    SprProfileSettings<SprBackendUserSession> sprProfileSettings;

    @Autowired
    SprNewsBrowse sprNewsBrowse;

    @Autowired
    SprNewsEdit sprNewsEdit;

    @Autowired
    SprNewsView sprNewsView;

    // This method overrides the createNewSession method from the parent class and returns a new instance of SprBackendUserSession.
    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    /**
     * @description This method handles the POST request for getting a list of news items that match the given parameters and returns a JSON response.
     * @param {SelectRequestParams} params - The object that contains the paging and search parameters.
     * @return {ResponseEntity<String>} A response entity with the JSON result of the search, which includes the total count and the list of news items.
     * @throws {Exception} If any error occurs during the request processing.
     */
    @PostMapping("/list")
    public ResponseEntity<String> getList(@RequestBody SelectRequestParams params) throws Exception {
        String language = this.requestContext.getUserSession().getSes_language();
        return okResponse(sprNewsBrowse.getList(this.getDBConnection(), params, language));
    }

    /**
     * @description This method handles the POST request for saving a news item to the database and returns a JSON response with the updated data.
     * @param {NewsEditModel} formData - The object that contains the news item data to be saved.
     * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON saved news item data.
     * @throws {Exception} If any error occurs during the request processing or data validation.
     */
    @PostMapping("/save")
    public ResponseEntity<NewsEditModel> save(@RequestBody NewsEditModel formData) throws Exception {
        return okResponse(sprNewsEdit.save(this.getDBConnection(), formData));
    }

    /**
     * @description This method handles the GET request for getting a news item from the database by its id and returns a JSON response with the data.
     * @param {Double} id - The double value that represents the unique identifier of the news item.
     * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON news item data.
     * @throws {Exception} If any error occurs during the request processing or data retrieval.
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<NewsEditModel> get(@PathVariable Double id) throws Exception {
        return okResponse(sprNewsEdit.get(this.getDBConnection(), id));
    }

    /**
     * @description This method handles the POST request for deleting a news item from the database by its id and returns an empty response.
     * @param {RecordIdentifier} recordIdentifier - The object that contains the record id as a double value.
     * @throws {Exception} If any error occurs during the request processing or data deletion.
     */
    @PostMapping("/delete")
    public void delete(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprNewsEdit.delete(this.getDBConnection(), recordIdentifier.getIdAsDouble());
    }

    /**
     * @description This method handles the GET request for viewing a news item from the database by its id and returns a JSON response with the data and increments its view count.
     * @param {Double} id - The double value that represents the unique identifier of the news item to be viewed.
     * @return {ResponseEntity<NewsEditModel>} A response entity with the JSON viewed news item data with the updated view count.
     * @throws {Exception} If any error occurs during the request processing or data retrieval or update.
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<NewsEditModel> view(@PathVariable Double id) throws Exception {
        Double userId = this.requestContext.getUserSession().getSes_usr_id();
        return okResponse(sprNewsView.view(this.getDBConnection(), id, userId));
    }

    /**
     * @description This method handles the POST request for adding a comment to a news item and returns a JSON response with the updated list of comments for that news item.
     * @param {SprNewsCommentsDAO} formData - The object that contains the comment data to be added, such as the author, content and record id of the news item.
     * @return {ResponseEntity<List<NewsCommentModel>>} A response entity with the JSON list of comments for the news item with the added comment.
     * @throws {Exception} If any error occurs during the request processing or data insertion or retrieval. 
     */
    @PostMapping("/comment")
    public ResponseEntity<List<NewsCommentModel>> comment(@RequestBody SprNewsCommentsDAO formData) throws Exception {
        Double userId = this.requestContext.getUserSession().getSes_usr_id();
        return okResponse(sprNewsView.comment(this.getDBConnection(), formData, userId));
    }

    /**
     * @descriptionThis function is a POST mapping for the "/comment/delete" endpoint.
     * It deletes a specified comment from the user who made the request.
     * If the operation is successful, it returns a list of remaining comments.
     * @param commentData - NewsCommentModel object with the comment data
     * @return ResponseEntity<List<NewsCommentModel>> - ResponseEntity object with a list of remaining news comments 
     * @throws Exception if an error occurs during the database operation or the user does not have the necessary privileges
     */
    @PostMapping("/comment/delete")
    public ResponseEntity<List<NewsCommentModel>> deleteComment(@RequestBody NewsCommentModel commentData) throws Exception {
        Double userId = this.requestContext.getUserSession().getSes_usr_id();
        return okResponse(sprNewsView.deleteComment(this.getDBConnection(), commentData, userId));
    }

}