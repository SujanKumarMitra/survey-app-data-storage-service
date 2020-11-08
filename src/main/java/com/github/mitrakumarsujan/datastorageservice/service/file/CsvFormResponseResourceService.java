package com.github.mitrakumarsujan.datastorageservice.service.file;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.github.mitrakumarsujan.datastorageservice.service.FormStorageService;
import com.github.mitrakumarsujan.formmodel.exception.IncorrectCredentialsException;
import com.github.mitrakumarsujan.formmodel.model.api.FormResponseDownloadRequest;
import com.github.mitrakumarsujan.formmodel.model.form.Form;

/**
 * @author skmitra
 * @since 2020-11-08
 */
@Service("csv-form-response-resource")
public class CsvFormResponseResourceService implements FormResponseResourceService {

	@Autowired
	private FormStorageService formStorageService;

	@Autowired
	@Qualifier("csv-response-manager")
	private FormResponseFileManager fileManager;

	@Override
	public Resource getFileResource(FormResponseDownloadRequest request) {
		String formId = request.getFormId();
		Form form = formStorageService.find(formId);
		if (!matches(request, form)) {
			throw new IncorrectCredentialsException("key not matching");
		}
		File file = fileManager.getFile(formId);
		return new FileSystemResource(file);
	}

	private boolean matches(FormResponseDownloadRequest request, Form form) {
		String suppliedKey = request.getFormKey();
		String actualKey = form.getKey();
		return actualKey.contentEquals(suppliedKey);
	}

}
