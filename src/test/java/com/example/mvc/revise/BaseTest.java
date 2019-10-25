package com.example.mvc.revise;

import com.example.mvc.revise.config.SpringConfiguration;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(classes = {SpringConfiguration.class})
@WebAppConfiguration
public class BaseTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void beforeTest() {
        WebApplicationContext context;
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
}
