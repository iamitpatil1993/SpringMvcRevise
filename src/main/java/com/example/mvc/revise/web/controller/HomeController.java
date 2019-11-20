package com.example.mvc.revise.web.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * One of the Job of {@link WebApplicationContext} is to scan
 * {@link ServletConfigAware} classes and pass {@link ServletContext} to them.
 * So, this class implements {@link ServletConfigAware} to get
 * {@link ServletContext}
 * 
 * @author amipatil
 *
 */
@Controller
@RequestMapping(path = { "/", "/home" })
public class HomeController implements ServletConfigAware {

	@RequestMapping(method = { RequestMethod.GET })
	public String home(Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute("message", "random message");
		return "home";
	}

	/**
	 * we get {@link ServletContext} here, if we want to use it at bean
	 * initialization time.
	 */
	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		System.out.println("ServletContext injected ? " + servletConfig != null);
		Assert.notNull(servletConfig, "ServletContext injected null in ServletContext Aware class");
	}

}
