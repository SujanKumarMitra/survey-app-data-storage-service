package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.mitrakumarsujan.formmodel.model.formresponse.Response;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Component
public class FormResponseCsvRowMapper implements Function<List<Response>, CharSequence> {

	@Override
	public CharSequence apply(List<Response> responses) {

		return responses.stream()
						.map(Response::getAnswer)
						.collect(Collectors.joining(", "));
	}

}
