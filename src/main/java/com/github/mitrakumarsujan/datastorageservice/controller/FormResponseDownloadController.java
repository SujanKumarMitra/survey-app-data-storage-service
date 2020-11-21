package com.github.mitrakumarsujan.datastorageservice.controller;

import com.github.mitrakumarsujan.datastorageservice.service.authentication.access.FormResponseResourceAccessService;
import com.github.mitrakumarsujan.datastorageservice.service.authentication.FormResponseAccessRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;

/**
 * @author skmitra
 * @since 2020-11-08
 */
@RestController
@RequestMapping("/v1/download")
@CrossOrigin
public class FormResponseDownloadController {

    @Autowired
    private FormResponseResourceAccessService resourceAccessService;

    @GetMapping("/{formId}")
    public ResponseEntity<Resource> getFileResource(
            @PathVariable("formId") String formId,
            @RequestParam(name = "formKey", required = false) String paramKey,
            @RequestHeader(name = "formKey", required = false) String headerKey) {

        FormResponseAccessRequest request =
                new FormResponseAccessRequestBuilder()
                        .withFormId(formId)
                        .withFormKeyIfNotNull(paramKey)
                        .withFormKeyIfNotNull(headerKey)
                        .build();
        Resource resource = resourceAccessService.getFormResource(request);

        String fileName = formId + ".csv";
        return ResponseEntity.status(HttpStatus.OK)
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .body(resource);
    }

}
