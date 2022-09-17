package com.meetingdiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@Controller
@ApiIgnore
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/swagger-ui/";
    }

}

