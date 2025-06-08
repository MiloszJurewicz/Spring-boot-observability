package org.jurewicz.greetingsservice;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
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

    private final LongCounter greetingsHistogram;

    public GreetingsController(OpenTelemetry openTelemetry) {
        Meter meter = openTelemetry.getMeter(GreetingsApplication.class.getName());
        greetingsHistogram = meter.counterBuilder("greetings-count").setDescription("Counts how many time person was greeted").build();
    }

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
        MDC.put("caller_name", name);

        logger.info("Greeting request");
        greetingsHistogram.add(1);

        MDC.clear();


        return ResponseEntity.ok(
                GreetingResponseBody.of("Hello", name)
        );
    }
}
