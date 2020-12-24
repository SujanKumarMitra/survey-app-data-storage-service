package com.github.mitrakumarsujan.datastorageservice.service;

import com.github.mitrakumarsujan.formmodel.model.formresponse.Response;
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
public class FormResponseRowMapper implements Function<List<Response>, List<String>> {

	@Override
	public List<String> apply(List<Response> responses) {

		return responses.stream()
						.map(Response::getAnswer)
						.collect(toCollection(LinkedList::new));
	}

}
