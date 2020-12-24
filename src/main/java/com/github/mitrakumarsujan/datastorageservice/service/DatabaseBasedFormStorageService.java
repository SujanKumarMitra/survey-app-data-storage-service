package com.github.mitrakumarsujan.datastorageservice.service;

import com.github.mitrakumarsujan.datastorageservice.entity.MongoDbEntityForm;
import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static java.text.MessageFormat.format;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service("db-based-form-storage-service")
public class DatabaseBasedFormStorageService implements FormStorageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Form save(Form form) {
        Form entityForm = new MongoDbEntityForm(form);
        return mongoTemplate.save(entityForm);
    }

    @Override
    public Form find(String formId) throws FormNotFoundException {
        MongoDbEntityForm form = mongoTemplate.findOne(
                query(
                        where("_id")
                                .is(formId)
                ), MongoDbEntityForm.class);
        if(form == null)
            throw new FormNotFoundException(format("form not found for id [{0}]",formId));
        return form;
    }
}
