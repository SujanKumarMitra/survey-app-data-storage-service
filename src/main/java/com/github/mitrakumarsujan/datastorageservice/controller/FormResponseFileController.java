package com.github.mitrakumarsujan.datastorageservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.mitrakumarsujan.datastorageservice.exception.IncorrectCredentialsException;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormResponseResourceService;
import com.github.mitrakumarsujan.formmodel.model.api.FormResponseDownloadRequest;
import com.github.mitrakumarsujan.formmodel.model.api.FormResponseDownloadRequestImpl;

/**
 * @author skmitra
 * @since 2020-11-08
 */
@RestController
@RequestMapping("/v1/download")
public class FormResponseFileController {

	@Autowired
	@Qualifier("csv-form-response-resource")
	private FormResponseResourceService downloadManager;

	private static final String NO_PARAM = "null";

	@GetMapping("/{formId}")
	public ResponseEntity<Resource> getFileResource(
			@PathVariable("formId") String formId,
			@RequestParam(name = "formKey", defaultValue = NO_PARAM) String paramKey,
			@RequestHeader(name = "formKey", defaultValue = NO_PARAM) String headerKey) {
		
		FormResponseDownloadRequest request = getRequestCommand(formId, paramKey, headerKey);
		Resource resource = downloadManager.getFileResource(request);

		String fileName = formId + ".csv";
		return ResponseEntity	.status(HttpStatus.OK)
								.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
								.contentType(MediaType.APPLICATION_OCTET_STREAM)
								.body(resource);
	}

	private FormResponseDownloadRequest getRequestCommand(String formId, String paramKey, String headerKey) {
		String formKey = null;
		if (paramKey != null && !paramKey.contentEquals(NO_PARAM)) {
			formKey = paramKey;
		} else if (headerKey != null && !headerKey.contentEquals(NO_PARAM)) {
			formKey = headerKey;
		}

		if (formKey == null) {
			throw new IncorrectCredentialsException("form key is missing");
		}

		return new FormResponseDownloadRequestImpl(formId, formKey);
	}

}
