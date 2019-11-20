package com.example.mvc.revise.web.controller;

import com.example.mvc.revise.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
public class SpittleControllerTest extends BaseTest {

    @Test
    public void testGetAllSpittles() throws Exception {
        mockMvc.perform(get("/spittles"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"));
    }

    @Test
    public void testGetAllSpittlesWithPagination() throws Exception {
        mockMvc.perform(get("/spittles?pageIndex=1&pageSize=5"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList", hasSize(5)));
    }
}