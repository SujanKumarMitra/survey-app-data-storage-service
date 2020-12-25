package com.github.mitrakumarsujan.datastorageservice.service.authentication.access;

import com.github.mitrakumarsujan.datastorageservice.service.FormResponseResourceService;
import com.github.mitrakumarsujan.datastorageservice.service.authentication.FormResponseAccessAuthenticationService;
import com.github.mitrakumarsujan.datastorageservice.service.authentication.FormResponseAuthenticationResult;
import com.github.mitrakumarsujan.formmodel.exception.BadCredentialsException;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class FormResponseResourceAccessServiceImpl implements FormResponseResourceAccessService {

    @Autowired
    private FormResponseAccessAuthenticationService authenticationService;

    @Autowired
    private FormResponseResourceService resourceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FormResponseResourceAccessServiceImpl.class);

    @Override
    public Resource getFormResource(FormResponseAccessRequest accessRequest) {
        String formId = accessRequest.getFormId();
        LOGGER.info("FormResponseResource of formId '{}' is requested for access", formId);
        FormResponseAuthenticationResult authResult = authenticationService.authenticate(accessRequest);
        if (authResult.isAuthenticated()) {
            LOGGER.info("Authentication Successful for formId '{}'", formId);
            return resourceService.getFileResource(formId);
        } else {
            LOGGER.info("Authentication failed for formId '{}'", formId);
            throw new BadCredentialsException("Incorrect FormKey");
        }
    }
}
