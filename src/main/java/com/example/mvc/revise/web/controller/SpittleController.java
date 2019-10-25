package com.example.mvc.revise.web.controller;

import com.example.mvc.revise.dao.SpittleRepository;
import com.example.mvc.revise.dto.Spittle;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(path = {"/spittles"})
public class SpittleController implements InitializingBean {

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = {RequestMethod.GET})
    public String getAllSpittles(final Model model) {
        final List<Spittle> spittles = spittleRepository.findAll();
        model.addAttribute("spittles", spittles);
        return "spittles";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(spittleRepository, "spittleRepository must be available in application context");
    }
}
