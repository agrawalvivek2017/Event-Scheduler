package com.events.scheduler;

import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/employees")
    String all() {
        return "{\"abc\":\"def\"}";
    }

    @PostMapping("/employees")
    int all(@RequestBody String something) {
        DbTest test = new DbTest();
        test.testDb(something);
        return Response.SC_OK;
    }
}
