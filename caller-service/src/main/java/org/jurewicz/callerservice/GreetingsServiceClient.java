package org.jurewicz.callerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

@Service
public class GreetingsServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(GreetingsServiceClient.class);

    private final RestClient restClient;

    public GreetingsServiceClient(@Value("${greetings-service}") String host) {
        System.out.println(host);
        System.out.println(host);
        System.out.println(host);
        System.out.println(host);
        System.out.println(host);
        System.out.println(host);
        System.out.println(host);
        System.out.println(host);

        this.restClient = RestClient.builder()
                .baseUrl(host)
                .build();
    }

    public GreetingResponseBody greet(String name) {
        return this.restClient.get()
                .uri(uriBuilder -> {
                    UriBuilder path = uriBuilder.path("/greetings");

                    if (name != null) path.queryParam("name", name);
                    return path.build(name);
                })
                .retrieve()
                .body(GreetingResponseBody.class);
    }
}
