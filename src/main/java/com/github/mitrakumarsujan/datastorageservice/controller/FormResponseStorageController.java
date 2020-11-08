package com.github.mitrakumarsujan.datastorageservice.controller;

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
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseCollectionDto;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseCollectionDtoConverter;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponse;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;
import com.github.mitrakumarsujan.formmodel.model.restresponse.RestSuccessResponse;
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

	@Autowired
	private FormResponseCollectionDtoConverter dtoConverter;

	@PostMapping
	public ResponseEntity<RestSuccessResponse<FormResponse>> saveForm(
			@RequestBody FormResponse formResponse) {
		FormResponse savedForm = responseStorageService.save(formResponse);

		return responseBuilderFactory	.getSingleDataBuilder(FormResponse.class)
										.withData(savedForm)
										.withStatus(HttpStatus.CREATED)
										.build()
										.toResponseEntity();

	}

	@GetMapping("/{formId}")
	public ResponseEntity<RestSuccessResponse<FormResponseCollectionDto>> getResponses(
			@PathVariable("formId") String formId) {

		FormResponseCollection responses = responseStorageService.getResponses(formId);
		FormResponseCollectionDto responsesDto = dtoConverter.convert(responses);
		
		return responseBuilderFactory	.getSingleDataBuilder(FormResponseCollectionDto.class)
										.withStatus(HttpStatus.FOUND)
										.withData(responsesDto)
										.build()
										.toResponseEntity();
	}

}
