package com.github.mitrakumarsujan.datastorageservice.entity;

import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Objects;

@Document("responses")
public class MongoDbEntityFormResponseCollection implements FormResponseCollection {

    @MongoId
    private String formId;
    private List<String> questions;
    private List<List<String>> responses;

    public MongoDbEntityFormResponseCollection() {
    }


    @Override
    public String toString() {
        return "MongoDbEntityFormResponseCollection{" +
                "formId='" + formId + '\'' +
                ", questions=" + questions +
                ", responses=" + responses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoDbEntityFormResponseCollection that = (MongoDbEntityFormResponseCollection) o;
        return Objects.equals(formId, that.formId) && Objects.equals(questions, that.questions) && Objects.equals(responses, that.responses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formId, questions, responses);
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<List<String>> getResponses() {
        return responses;
    }

    public void setResponses(List<List<String>> responses) {
        this.responses = responses;
    }
}
