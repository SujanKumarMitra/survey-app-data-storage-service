package com.github.mitrakumarsujan.datastorageservice.service;

import java.util.List;

import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponse;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
public interface FormResponseStorageService {

	void initFormStorage(Form form);

	FormResponse save(FormResponse response);

	List<FormResponse> getAll(String formId);
}
