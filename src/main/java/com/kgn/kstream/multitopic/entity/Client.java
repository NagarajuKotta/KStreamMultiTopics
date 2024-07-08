package com.kgn.kstream.multitopic.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Client {

    private Integer clientId;
    private String clientName;
    private Integer projectId;
}
