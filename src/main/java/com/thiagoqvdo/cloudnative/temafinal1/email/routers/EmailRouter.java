package com.thiagoqvdo.cloudnative.temafinal1.email.routers;

import com.thiagoqvdo.cloudnative.temafinal1.email.handlers.EmailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class EmailRouter {

    @Bean
    public RouterFunction<ServerResponse> route(EmailHandler handler) {
        return RouterFunctions
                .route(POST("/sendEmail")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::sendEmail);
    }
}
