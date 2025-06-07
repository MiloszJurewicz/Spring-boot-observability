package org.jurewicz.greetingsservice;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/greetings")
public class GreetingsController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingsController.class);

    public record GreetingResponseBody(
            @NonNull String message
    ) {
        public static GreetingResponseBody of(
                @NonNull String message,
                @Nullable String name
        ) {
            if (Objects.isNull(name)) {
                return new GreetingResponseBody(message);
            }
            return new GreetingResponseBody(message + " " + name);
        }
    }

    @GetMapping
    public ResponseEntity<GreetingResponseBody> greet(
            @RequestParam(name = "name", required = false) String name
    ) {
        logger.info("Greeting request for {}", name == null ? "null" : name);

        return ResponseEntity.ok(
                GreetingResponseBody.of("Hello", name)
        );
    }
}
