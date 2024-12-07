package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.model.request.ExampleRequest;
import com.itm.space.auditservice.model.response.ExampleResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleControllerImpl implements ExampleController {

    @Override
    public ExampleResponse exampleRequest(ExampleRequest request) {
        return new ExampleResponse(String.format("Example response: %s", request.getMessage()));
    }
}
