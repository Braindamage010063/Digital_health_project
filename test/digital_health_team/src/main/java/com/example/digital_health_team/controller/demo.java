package com.example.digital_health_team.controller;
import com.example.digital_health_team.Model.Patient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "demo")
@ControllerAdvice
public class demo{

    @RequestMapping("")
    public String hello(Model model) {



        model.addAttribute("year", "2013");
        model.addAttribute("test","99");
        Patient patient = new Patient();
        patient.setOcrcontent("ssssss");
        patient.setPatientfhirid(025555);
        patient.setPatientname("adddd");
        model.addAttribute("patient",patient);

        //Patient p = patientService.getPatient(1);

        //model.addAttribute("list",p.getPatientname());
        return "/index";
    }



}
