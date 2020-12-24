package com.github.mitrakumarsujan.datastorageservice.controller;

import com.github.mitrakumarsujan.datastorageservice.service.authentication.FormResponseAccessRequestBuilder;
import com.github.mitrakumarsujan.datastorageservice.service.authentication.access.FormResponseResourceAccessService;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                        .withFormKeyIfNotPresentAndNotNull(paramKey)
                        .withFormKeyIfNotPresentAndNotNull(headerKey)
                        .build();
        Resource resource = resourceAccessService.getFormResource(request);

        String fileName = formId + ".csv";
        return ResponseEntity.status(HttpStatus.OK)
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .body(resource);
    }

}
