package com.kgn.kstream.multitopic.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Integer empId;
    private String empName;
    private Integer clientId;
    private String companyName;
    private Integer projectId;
}
