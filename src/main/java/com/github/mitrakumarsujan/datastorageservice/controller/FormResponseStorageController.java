package com.github.mitrakumarsujan.datastorageservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mitrakumarsujan.datastorageservice.service.FormResponseStorageService;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponse;
import com.github.mitrakumarsujan.formmodel.model.restresponse.RestSuccessResponse;
import com.github.mitrakumarsujan.formmodel.model.restresponse.success.RestSuccessResponseBuilder;
import com.github.mitrakumarsujan.formmodel.model.restresponse.success.RestSuccessResponseBuilderFactory;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/v1/formResponse")
public class FormResponseStorageController {

	@Autowired
	@Qualifier("csv-response-storage")
	private FormResponseStorageService responseStorageService;

	@Autowired
	private RestSuccessResponseBuilderFactory responseBuilderFactory;

	@PostMapping
	public ResponseEntity<RestSuccessResponse<FormResponse>> saveForm(@RequestBody FormResponse formResponse) {
		FormResponse savedForm = responseStorageService.save(formResponse);

		return responseBuilderFactory	.getSingleDataBuilder(FormResponse.class)
										.withData(savedForm)
										.withStatus(HttpStatus.CREATED)
										.build()
										.toResponseEntity();

	}

	@GetMapping("/{formId}")
	public ResponseEntity<RestSuccessResponse<List<FormResponse>>> saveForm(@PathVariable("formId") String formId) {
		List<FormResponse> responses = responseStorageService.getAll(formId);
		RestSuccessResponseBuilder<List<FormResponse>> builder = responseBuilderFactory.getSingleDataBuilder();

		return builder	.withData(responses)
						.withStatus(HttpStatus.FOUND)
						.build()
						.toResponseEntity();
	}

}
