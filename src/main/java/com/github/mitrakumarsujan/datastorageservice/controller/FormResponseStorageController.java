package com.github.mitrakumarsujan.datastorageservice.controller;

import com.github.mitrakumarsujan.datastorageservice.service.authentication.access.FormResponseStorageAccessService;
import com.github.mitrakumarsujan.datastorageservice.service.authentication.FormResponseAccessRequestBuilder;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import com.github.mitrakumarsujan.formmodel.model.restresponse.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
public class FormResponseStorageController {

    @Autowired
    @Qualifier("db-response-storage")
    private FormResponseStorageService responseStorageService;

    @Autowired
    private FormResponseStorageAccessService storageAccessService;

    @Autowired
    private RestSuccessResponseBuilderFactory responseBuilderFactory;

    @Autowired
    private FormResponseCollectionDtoConverter dtoConverter;

    @PostMapping
    public ResponseEntity<RestSuccessResponse<SuccessMessage>> saveFormResponse(
            @RequestBody FormResponse formResponse) {

        FormResponse savedForm = responseStorageService.save(formResponse);
        return responseBuilderFactory.getSingleDataBuilder(SuccessMessage.class)
                                     .withData(new SuccessMessage("response successfully saved"))
                                     .withStatus(HttpStatus.CREATED)
                                     .build()
                                     .toResponseEntity();

    }

    @GetMapping("/{formId}")
    public ResponseEntity<RestSuccessResponse<FormResponseCollectionDto>> getResponses(
            @PathVariable("formId") String formId,
            @RequestParam(name = "formKey", required = false) String paramKey,
            @RequestHeader(name = "formKey", required = false) String headerKey) {

        FormResponseAccessRequest request =
                new FormResponseAccessRequestBuilder()
                        .withFormId(formId)
                        .withFormKeyIfNotPresentAndNotNull(headerKey)
                        .withFormKeyIfNotPresentAndNotNull(paramKey)
                        .build();

        FormResponseCollection responses = storageAccessService.getResponses(request);
        FormResponseCollectionDto responsesDto = dtoConverter.convert(responses);

        return responseBuilderFactory.getSingleDataBuilder(FormResponseCollectionDto.class)
                                     .withStatus(HttpStatus.OK)
                                     .withData(responsesDto)
                                     .build()
                                     .toResponseEntity();
    }

}
