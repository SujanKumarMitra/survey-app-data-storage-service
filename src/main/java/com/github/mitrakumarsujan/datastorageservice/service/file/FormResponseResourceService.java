package com.github.mitrakumarsujan.datastorageservice.service.file;

import org.springframework.core.io.Resource;

/**
 * @author skmitra
 * @since 2020-11-08
 */
public interface FormResponseResourceService {
	
	Resource getFileResource(String formId);
}
