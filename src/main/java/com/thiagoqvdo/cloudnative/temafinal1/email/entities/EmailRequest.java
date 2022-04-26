package com.thiagoqvdo.cloudnative.temafinal1.email.entities;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private String recipient;
    private String title;
    private String message;
}
