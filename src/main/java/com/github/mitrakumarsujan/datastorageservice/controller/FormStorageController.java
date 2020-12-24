package com.github.mitrakumarsujan.datastorageservice.controller;

import com.github.mitrakumarsujan.datastorageservice.service.FormStorageService;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.restresponse.RestSuccessResponse;
import com.github.mitrakumarsujan.formmodel.model.restresponse.SuccessMessage;
import com.github.mitrakumarsujan.formmodel.model.restresponse.success.RestSuccessResponseBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/v1/form")
@CrossOrigin
public class FormStorageController {

	@Autowired
	private FormStorageService service;

	@Autowired
	private RestSuccessResponseBuilderFactory responseBuilderFactory;

	@PostMapping
	public ResponseEntity<RestSuccessResponse<SuccessMessage>> saveForm(@RequestBody Form form) {
		service.save(form);
		return responseBuilderFactory	.getSingleDataBuilder(SuccessMessage.class)
										.withData(new SuccessMessage("form saved successfully"))
										.withStatus(HttpStatus.CREATED)
										.build()
										.toResponseEntity();
	}

	@GetMapping("/{formId}")
	public ResponseEntity<RestSuccessResponse<Form>> getForm(@PathVariable("formId") String formId) {
		Form form = service.find(formId);
		return responseBuilderFactory	.getSingleDataBuilder(Form.class)
										.withData(form)
										.withStatus(HttpStatus.FOUND)
										.build()
										.toResponseEntity();
	}

}