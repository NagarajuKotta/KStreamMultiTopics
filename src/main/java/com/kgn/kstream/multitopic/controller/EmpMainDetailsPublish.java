package com.kgn.kstream.multitopic.controller;

import com.kgn.kstream.multitopic.entity.PublishDetails;
import com.kgn.kstream.multitopic.service.EmpMainDetailsPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employees")
@RestController
public class EmpMainDetailsPublish {

    @Autowired
    EmpMainDetailsPublishService empMainDetailsPublishService;

    @PostMapping("/publish")
    public String publishEmpManiDetails(@RequestBody PublishDetails publishDetails){
        return  empMainDetailsPublishService.publishEmpManiDetails(publishDetails);
    }
}
