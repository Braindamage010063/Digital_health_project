package com.example.dy.controller;


import com.example.dy.Model.Patient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "test")
@ControllerAdvice
public class test{
    
    @RequestMapping(value = "")
    public String testv(Model model){
        Patient patient = new Patient();
        patient.setOcrcontent("ssssss");
        patient.setPatientfhirid(025555);
        patient.setPatientname("adddd");

        model.addAttribute("year", "2013");
        model.addAttribute("patient", patient);
        return "/index";
    }
}
