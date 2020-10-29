package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.github.mitrakumarsujan.formmodel.model.form.FormField;
import com.github.mitrakumarsujan.formmodel.model.form.FormTemplate;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Component
public class FormTemplateCsvHeaderMapper implements Function<FormTemplate, String[]> {

	@Override
	public String[] apply(FormTemplate t) {
		return t.getFields()
				.stream()
				.map(FormField::getQuestion)
				.toArray(String[]::new);

	}

}
