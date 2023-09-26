package com.example.dy.controller;
import com.example.dy.DyApplication;
import com.example.dy.Model.Patient;
import com.example.dy.service.OcrSample;
import com.example.dy.service.OcrSample1;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
;import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RequestMapping(value = "demo")
@ControllerAdvice
public class demo {
    @RequestMapping("")
    public String hello(Model model) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        Patient patient = new Patient();
        patient.setOcrcontent("ssssss");
        patient.setPatientfhirid(025555);
        patient.setPatientname("adddd");

        model.addAttribute("year", "2013");
        model.addAttribute("patient", patient);

        OcrSample1.processOcrDocument();


        return "/index";
    }
    @RequestMapping("landing")
    public String landing(Model model) throws IOException, ExecutionException, InterruptedException, TimeoutException {

        Patient patient = new Patient();
        patient.setOcrcontent("ssssss");
        patient.setPatientfhirid(025555);
        patient.setPatientname("adddd");

        model.addAttribute("year", "2013");
        model.addAttribute("patient", patient);

        OcrSample1.processOcrDocument();


        return "/Landing";
    }

    @RequestMapping("ll")
    public String handleException() {
        // 执行重定向到指定URL
        ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8080/fhir/");
        return  "redirect:http://localhost:8090/fhir/";
    }


    @RequestMapping("/postToExternalSite")
    public ResponseEntity<String> postToExternalSite() {
        // 创建RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 设置请求体
        String requestBody = "{\"key\": \"value\"}"; // 替换成你要发送的数据
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发起POST请求
        String externalUrl = "https://example.com/api/endpoint"; // 替换成目标网站的URL
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                externalUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        org.hl7.fhir.r4.model.Patient p = new org.hl7.fhir.r4.model.Patient();

        return responseEntity;
    }

}
