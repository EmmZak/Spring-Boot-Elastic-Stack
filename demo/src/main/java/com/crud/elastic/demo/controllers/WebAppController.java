package com.crud.elastic.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebAppController {
    


    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String app() {
        System.out.println("ici");
        return "index";
    }
}
