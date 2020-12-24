package com.github.mitrakumarsujan.datastorageservice.entity;

import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.form.FormTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Objects;

@Document("forms")
public class MongoDbEntityForm implements Form {

    @MongoId
    private String id;
    private String key;
    private FormTemplate template;

    public MongoDbEntityForm() {
    }

    public MongoDbEntityForm(String id, String key, FormTemplate template) {
        this.id = id;
        this.key = key;
        this.template = template;
    }

    public MongoDbEntityForm(Form form) {
        this(form.getId(),form.getKey(),form.getTemplate());
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public FormTemplate getTemplate() {
        return template;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setTemplate(FormTemplate template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoDbEntityForm that = (MongoDbEntityForm) o;
        return Objects.equals(id, that.id) && Objects.equals(key, that.key) && Objects.equals(template, that.template);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, key, template);
    }

    @Override
    public String toString() {
        return "MongoDbEntityForm{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", template=" + template +
                '}';
    }
}
