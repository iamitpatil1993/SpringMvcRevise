package com.example.mvc.revise.config;

import com.example.mvc.revise.config.web.WebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfiguration.class, RootConfiguration.class})
public class SpringConfiguration {
    // Nothing to do here for now
}
