package com.github.mitrakumarsujan.datastorageservice.service.authentication.access;

import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import org.springframework.core.io.Resource;

public interface FormResponseResourceAccessService {

    Resource getFormResource(FormResponseAccessRequest accessRequest);
}
