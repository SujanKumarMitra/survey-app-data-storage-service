package com.github.mitrakumarsujan.datastorageservice.service;

import com.github.mitrakumarsujan.formmodel.model.form.FormField;
import com.github.mitrakumarsujan.formmodel.model.form.FormTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toCollection;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Component
public class FormTemplateHeaderMapper implements Function<FormTemplate, List<String>> {

	@Override
	public List<String> apply(FormTemplate t) {
		return t.getFields()
				.stream()
				.map(FormField::getQuestion)
				.collect(toCollection(LinkedList::new));

	}

}
