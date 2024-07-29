package lt.project.ntis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.itreegroup.s2.server.rest.S2RestAuthService;
import eu.itreegroup.spark.dao.common.RecordIdentifier;
import eu.itreegroup.spark.dao.query.SelectRequestParams;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFaqEdit;
import eu.itreegroup.spark.modules.admin.logic.forms.SprFaqThemesList;
import eu.itreegroup.spark.modules.admin.logic.forms.model.FaqModel;
import eu.itreegroup.spark.modules.admin.logic.forms.model.IdKeyValuePair;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendUserSession;
import eu.itreegroup.spark.modules.common.rest.model.SprBackendWebSessionInfo;
import lt.project.ntis.logic.forms.NtisSprFaqBrowse;

/**
 * Klasė skirta peržiūrėti FAQ (prisijungusiam vartotojui)
 * 
 */

@RestController
@RequestMapping("/ntis-faq")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class NtisFAQService extends S2RestAuthService<SprBackendWebSessionInfo, SprBackendUserSession> {

    @Autowired
    SprFaqThemesList sprFaqThemesList;

    @Autowired
    NtisSprFaqBrowse ntisSprFaqBrowse;

    @Autowired
    SprFaqEdit sprFaqEdit;

    @Override
    protected SprBackendUserSession createNewSession() {
        return new SprBackendUserSession();
    }

    // Frequently asked questions:
    @PostMapping(value = "/get-faq-themes-list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFaqThemesList(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(sprFaqThemesList.getThemesList(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-faq-list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFaqListForAdmin(@RequestBody SelectRequestParams params) throws Exception {
        return okResponse(ntisSprFaqBrowse.getFaqList(this.getDBConnection(), params));
    }

    @RequestMapping(value = "/get-faq-name", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<IdKeyValuePair> getGroupName(@RequestBody String code) throws Exception {
        return okResponse(ntisSprFaqBrowse.getGroupName(this.getDBConnection(), code));
    }

    @RequestMapping(value = "/set-question-answer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setQuestionAnswer(@RequestBody FaqModel formData) throws Exception {
        sprFaqEdit.saveQuestionAnswer(getDBConnection(), formData, this.requestContext.getUserSession().getSes_language());
    }

    @RequestMapping(value = "/get-question-answer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FaqModel> getRec(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        return okResponse(sprFaqEdit.getQuestionAnswer(this.getDBConnection(), recordIdentifier));
    }

    @RequestMapping(value = "/del-question-answer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteQuestionAnswer(@RequestBody RecordIdentifier recordIdentifier) throws Exception {
        sprFaqEdit.deleteQuestionAnswer(this.getDBConnection(), recordIdentifier);
    }
}
