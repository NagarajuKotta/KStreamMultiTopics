package com.kgn.kstream.multitopic.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    private Integer projectId;
    private String projectName;
    private String department;
}
