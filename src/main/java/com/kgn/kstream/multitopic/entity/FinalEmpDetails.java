package com.kgn.kstream.multitopic.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class FinalEmpDetails {
    private Integer empId;
    private String empName;
    private String companyName;
    private String clientName;
    private String projectName;
}
