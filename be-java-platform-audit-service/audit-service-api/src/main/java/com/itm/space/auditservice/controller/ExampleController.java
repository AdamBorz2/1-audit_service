package com.itm.space.auditservice.controller;

import com.itm.space.auditservice.model.request.ExampleRequest;
import com.itm.space.auditservice.model.response.ExampleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.auditservice.constant.ApiConstant.EXAMPLE_URL;

@RequestMapping(EXAMPLE_URL)
public interface ExampleController {

    @PostMapping
    ExampleResponse exampleRequest(@RequestBody ExampleRequest request);
}
