package com.github.mitrakumarsujan.datastorageservice.service.file;

import org.springframework.core.io.Resource;

import com.github.mitrakumarsujan.formmodel.model.api.FormResponseDownloadRequest;

/**
 * @author skmitra
 * @since 2020-11-08
 */
public interface FormResponseResourceService {
	
	Resource getFileResource(FormResponseDownloadRequest request);
}
