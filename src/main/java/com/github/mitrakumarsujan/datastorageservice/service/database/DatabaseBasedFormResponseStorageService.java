package com.github.mitrakumarsujan.datastorageservice.service.database;

import com.github.mitrakumarsujan.datastorageservice.entity.MongoDbEntityFormResponseCollection;
import com.github.mitrakumarsujan.datastorageservice.service.FormResponseRowMapper;
import com.github.mitrakumarsujan.datastorageservice.service.FormResponseStorageService;
import com.github.mitrakumarsujan.datastorageservice.service.FormTemplateHeaderMapper;
import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponse;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service("db-response-storage")
@ConditionalOnProperty(prefix = "app", name = "storage-strategy", havingValue = "database")
public class DatabaseBasedFormResponseStorageService implements FormResponseStorageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FormTemplateHeaderMapper headerMapper;

    @Autowired
    private FormResponseRowMapper rowMapper;

    @Override
    public void initFormResponseStorage(Form form) {
        List<String> questions = headerMapper.apply(form.getTemplate());
        questions.add(0, "Timestamp");

        MongoDbEntityFormResponseCollection formResponseCollection = new MongoDbEntityFormResponseCollection();
        formResponseCollection.setFormId(form.getId());
        formResponseCollection.setQuestions(questions);
        formResponseCollection.setResponses(Collections.emptyList());

        mongoTemplate.save(formResponseCollection);
    }

    @Override
    public FormResponse save(FormResponse response) {
        List<String> responses = prepareResponses(response);

        String formId = response.getFormId();
        mongoTemplate.updateFirst(
                query(where("_id").is(formId)),
                new Update()
                        .push("responses", responses),
                MongoDbEntityFormResponseCollection.class);
        return response;
    }

    @Override
    public FormResponseCollection getResponses(String formId) {
        MongoDbEntityFormResponseCollection responses = mongoTemplate.findOne(
                query(where("_id").is(formId)),
                MongoDbEntityFormResponseCollection.class);
        if (responses == null)
            throw new FormNotFoundException(format("form not found for id=[{0}]", formId));
        return responses;
    }

    private List<String> prepareResponses(FormResponse response) {
        List<String> responses = rowMapper.apply(response.getResponses());
        responses.add(0, response.getTimestamp().toString());
        return responses;
    }
}