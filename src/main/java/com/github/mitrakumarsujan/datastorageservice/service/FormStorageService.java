package com.github.mitrakumarsujan.datastorageservice.service;

import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.form.Form;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
public interface FormStorageService {

	Form save(Form form);

	Form find(String formId) throws FormNotFoundException;

}
