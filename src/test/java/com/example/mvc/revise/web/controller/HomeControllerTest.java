package com.example.mvc.revise.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mvc.revise.BaseTest;

@RunWith(SpringRunner.class)
public class HomeControllerTest extends BaseTest {

    @Test
    public void home() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));
        
        
    }
}