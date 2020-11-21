package com.github.mitrakumarsujan.datastorageservice.service.authentication;

import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;

public interface FormResponseAuthenticationResult {
    boolean isAuthenticated();

    FormResponseAccessRequest getAccessRequest();
}
