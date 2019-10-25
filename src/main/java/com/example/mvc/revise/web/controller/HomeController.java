package com.example.mvc.revise.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = {"/", "/home"})
public class HomeController {

    @RequestMapping(method = {RequestMethod.GET})
    public String home(Model model) {
        System.out.println("HomeController.home called ... returning view");
        model.addAttribute("message", "random message");
        return "home";
    }
}
