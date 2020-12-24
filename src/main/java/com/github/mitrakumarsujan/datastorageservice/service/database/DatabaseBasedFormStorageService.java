package com.github.mitrakumarsujan.datastorageservice.service.database;

import com.github.mitrakumarsujan.datastorageservice.entity.MongoDbEntityForm;
import com.github.mitrakumarsujan.datastorageservice.service.FormResponseStorageService;
import com.github.mitrakumarsujan.datastorageservice.service.FormStorageService;
import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service("db-based-form-storage-service")
public class DatabaseBasedFormStorageService implements FormStorageService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("db-response-storage")
    private FormResponseStorageService responseStorageStrategy;

    @Override
    public Form save(Form form) {
        Form entityForm = new MongoDbEntityForm(form);
        Form savedForm = mongoTemplate.save(entityForm);
        responseStorageStrategy.initFormResponseStorage(form);
        return savedForm;
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
