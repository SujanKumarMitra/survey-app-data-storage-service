package com.github.mitrakumarsujan.datastorageservice.service.authentication;


import com.github.mitrakumarsujan.formmodel.exception.BadCredentialsException;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequestImpl;

import java.util.Objects;

public class FormResponseAccessRequestBuilder {

    private String formId;
    private String formKey;

    public FormResponseAccessRequestBuilder withFormId(String formId) {
        this.formId = formId;
        return this;
    }

    public FormResponseAccessRequestBuilder withFormKey(String formKey) {
        this.formKey = formKey;
        return this;
    }

    public FormResponseAccessRequestBuilder withFormKeyIfNotPresentAndNotNull(String formKey) {
        if (this.formKey == null && formKey != null)
            this.formKey = formKey;
        return this;
    }

    public FormResponseAccessRequest build() {
        try {
            Objects.requireNonNull(formId, () -> "formId can't be null");
            Objects.requireNonNull(formKey, () -> "formKey can't be null");
        } catch (NullPointerException e) {
            throw new BadCredentialsException(e.getMessage());
        }
        return new FormResponseAccessRequestImpl(formId, formKey);
    }
}
