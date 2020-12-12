package com.github.mitrakumarsujan.datastorageservice.service.authentication;

import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;

public class FormResponseAuthenticationResultImpl implements FormResponseAuthenticationResult {

    private final boolean authenticated;
    private final FormResponseAccessRequest accessRequest;

    public FormResponseAuthenticationResultImpl(boolean authenticated, FormResponseAccessRequest accessRequest) {
        this.authenticated = authenticated;
        this.accessRequest = accessRequest;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public FormResponseAccessRequest getAccessRequest() {
        return accessRequest;
    }
}
