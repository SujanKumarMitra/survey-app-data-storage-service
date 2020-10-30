package com.github.mitrakumarsujan.datastorageservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mitrakumarsujan.datastorageservice.service.FormStorageService;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.response.RestSuccessResponse;
import com.github.mitrakumarsujan.formmodel.model.response.success.RestSuccessResponseBuilderFactory;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/v1/form")
public class FormStorageController {

	@Autowired
	private FormStorageService service;

	@Autowired
	private RestSuccessResponseBuilderFactory builderFactory;

	@PostMapping
	public ResponseEntity<RestSuccessResponse<Form>> saveForm(@RequestBody Form form) {
		Form savedForm = service.save(form);
		return getResponseEntity(savedForm);
	}

	@GetMapping("/{formId}")
	public ResponseEntity<RestSuccessResponse<Form>> getForm
	(@PathVariable("formId") String formId) {
		Form form = service.find(formId);
		return getResponseEntity(form);
	}

	private ResponseEntity<RestSuccessResponse<Form>> getResponseEntity(Form savedForm) {
		return builderFactory	.getSingleDataBuilder(Form.class)
								.withStatus(HttpStatus.FOUND)
								.withData(savedForm)
								.build()
								.toResponseEntity();
	}

}
