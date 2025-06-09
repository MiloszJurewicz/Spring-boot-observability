package org.jurewicz.callerservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

record GreetingResponseBody(String message) {}

@RestController
@RequestMapping(path = "/call")
public class CallerController {

    private final GreetingsServiceClient greetingsServiceClient;

    public CallerController(GreetingsServiceClient greetingsServiceClient) {
        this.greetingsServiceClient = greetingsServiceClient;
    }

    @GetMapping()
    public ResponseEntity<Object> call(
            @RequestParam(name = "name", required = false) String name
    ) {
        var greetingsResponse = greetingsServiceClient.greet(name);

        return ResponseEntity
                .ok()
                .body(greetingsResponse);
    }
}
