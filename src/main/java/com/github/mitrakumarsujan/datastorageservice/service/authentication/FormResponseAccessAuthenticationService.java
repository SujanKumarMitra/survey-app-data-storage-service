package com.github.mitrakumarsujan.datastorageservice.service.authentication;

import com.github.mitrakumarsujan.formmodel.exception.BadCredentialsException;
import com.github.mitrakumarsujan.formmodel.model.dto.FormResponseAccessRequest;

public interface FormResponseAccessAuthenticationService {

    FormResponseAuthenticationResult authenticate(FormResponseAccessRequest request) throws BadCredentialsException;
}
