package com.github.mitrakumarsujan.datastorageservice.service;

import java.util.List;

import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponse;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
public interface FormResponseStorageService {

	void initFormResponseStorage(Form form);

	FormResponse save(FormResponse response);
	
	FormResponseCollection getResponses(String formId) throws FormNotFoundException;

}
