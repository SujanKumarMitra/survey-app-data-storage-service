package com.github.mitrakumarsujan.datastorageservice.service.authentication.access;

import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;

public interface FormResponseStorageAccessService {

    FormResponseCollection getResponses(FormResponseAccessRequest accessRequest);
}
