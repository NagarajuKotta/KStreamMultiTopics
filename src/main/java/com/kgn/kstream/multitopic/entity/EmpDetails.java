package com.kgn.kstream.multitopic.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmpDetails {

    private Integer empId;
    private String empName;
    private String companyName;
    private String clientName;
    private String projectName;
    private Integer clientId;
    private Integer projectId;

    public EmpDetails(Integer empId, String empName, String companyName, String clientName, String projectName) {
        this.empId = empId;
        this.empName = empName;
        this.companyName = companyName;
        this.clientName = clientName;
        this.projectName = projectName;
    }

}
