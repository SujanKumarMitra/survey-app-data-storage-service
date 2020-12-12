package com.github.mitrakumarsujan.datastorageservice.service.authentication;

import com.github.mitrakumarsujan.datastorageservice.service.FormStorageService;
import com.github.mitrakumarsujan.formmodel.exception.BadCredentialsException;
import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormResponseAccessAuthenticationServiceImpl implements FormResponseAccessAuthenticationService {

    @Autowired
    private FormStorageService formStorageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FormResponseAccessAuthenticationServiceImpl.class);

    @Override
    public FormResponseAuthenticationResult authenticate(FormResponseAccessRequest request) throws BadCredentialsException {
        String formId = request.getFormId();
        LOGGER.info("Authenticating for formId '{}'", formId);
        try {
            Form form = formStorageService.find(formId);
            String actualKey = form.getKey();
            String suppliedKey = request.getFormKey();
            boolean result = isSame(actualKey, suppliedKey);
            return new FormResponseAuthenticationResultImpl(result, request);
        } catch (FormNotFoundException e) {
            LOGGER.info("Authentication failed for formId '{}'. Reason='{}'", formId, e.toString());
            throw new BadCredentialsException("No form found with id='" + formId + "'");
        }

    }

    private boolean isSame(String actualKey, String suppliedKey) {
        return actualKey.contentEquals(suppliedKey);
    }
}
