package com.example.digital_health_1.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping(value = "demo")
@ControllerAdvice
public final class demo{

    @RequestMapping("")
    public String hello(Model model) {

        model.addAttribute("year", "2013");
        model.addAttribute("test","99");

        //Patient p = patientService.getPatient(1);

        //model.addAttribute("list",p.getPatientname());
        return "/index";
    }



}
