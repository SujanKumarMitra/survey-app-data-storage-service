package com.github.mitrakumarsujan.datastorageservice.service.authentication.access;

import com.github.mitrakumarsujan.datastorageservice.service.FormResponseStorageService;
import com.github.mitrakumarsujan.datastorageservice.service.authentication.FormResponseAccessAuthenticationService;
import com.github.mitrakumarsujan.datastorageservice.service.authentication.FormResponseAuthenticationResult;
import com.github.mitrakumarsujan.formmodel.exception.BadCredentialsException;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FormResponseStorageAccessServiceImpl implements FormResponseStorageAccessService {

    @Autowired
    private FormResponseAccessAuthenticationService authenticationService;


    @Autowired
    @Qualifier("csv-response-storage")
    private FormResponseStorageService responseStorageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FormResponseStorageAccessService.class);

    @Override
    public FormResponseCollection getResponses(FormResponseAccessRequest accessRequest) {
        String formId = accessRequest.getFormId();
        LOGGER.info("FormResponseStorage of formId '{}' is requested for access", formId);
        FormResponseAuthenticationResult authResult = authenticationService.authenticate(accessRequest);
        if(authResult.isAuthenticated()) {
            LOGGER.info("Authentication Successful for formId '{}'", formId);
            return responseStorageService.getResponses(formId);
        } else {
            LOGGER.info("Authentication failed for formId '{}'", formId);
            throw new BadCredentialsException("Incorrect FormKey");
        }
    }
}
