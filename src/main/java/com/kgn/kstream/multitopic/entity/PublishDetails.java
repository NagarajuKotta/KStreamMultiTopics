package com.kgn.kstream.multitopic.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PublishDetails {
    private Employee employee;
    private Project project;
    private Client client;
}
