package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page/")
public class PageController {
    @RequestMapping("{modelName}")
    public String add(@PathVariable String modelName){
        return modelName;
    }
}
