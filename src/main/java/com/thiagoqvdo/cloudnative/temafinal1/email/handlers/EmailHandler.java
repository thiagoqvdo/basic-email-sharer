package com.thiagoqvdo.cloudnative.temafinal1.email.handlers;

import com.thiagoqvdo.cloudnative.temafinal1.email.exceptions.InvalidParamsException;
import com.thiagoqvdo.cloudnative.temafinal1.email.entities.EmailRequest;
import com.thiagoqvdo.cloudnative.temafinal1.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Component
public class EmailHandler {

    @Autowired
    @Qualifier("service")
    EmailService service;

    public Mono<ServerResponse> sendEmail(ServerRequest request) {
        return request.bodyToMono(EmailRequest.class)
                .flatMap(email -> {
                        service.sendEmail(email);
                        return ok().build();
                })
                .onErrorResume(InvalidParamsException.class,
                        exception -> badRequest().bodyValue(exception.getMessage()))
                .onErrorResume(Exception.class,
                        exception -> status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(exception.getMessage()));
    }
}
