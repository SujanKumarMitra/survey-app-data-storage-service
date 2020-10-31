package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.github.mitrakumarsujan.formmodel.model.formresponse.Response;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Component
public class FormResponseCsvRowMapper implements Function<List<Response>, String[]> {

	@Override
	public String[] apply(List<Response> responses) {

		return responses.stream()
						.map(Response::getAnswer)
						.toArray(String[]::new);
	}

}
