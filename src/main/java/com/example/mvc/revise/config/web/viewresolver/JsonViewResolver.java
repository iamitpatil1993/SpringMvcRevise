package com.example.mvc.revise.config.web.viewresolver;

import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * This is custom implementation of {@link ViewResolver} for JSON. Spring will
 * detect this bean as ViewResolver and hence will use it for content
 * negotiation process. ContentNegotiatingViewResolver will use this
 * ViewResolver as a candiate ViewResolver. And since
 * {@link MappingJackson2JsonView} supports 'application/json', this
 * {@link ViewResolver} will be selected as a best View when Accept header in
 * request is 'application/json'. Hence this view will be used to render Model
 * objects from controller to response.
 * 
 * @author amipatil
 *
 */
@Component
public class JsonViewResolver implements ViewResolver {

	/*
	 * We simply need to return MappingJackson2JsonView from here, and this view
	 * class knows how to convert model object passed to it json and write to
	 * HttpServlertResponse passed to it.
	 */
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		final MappingJackson2JsonView jackson2JsonView = new MappingJackson2JsonView();

		// this enables sending model attribute as a response if model has single key.
		// So, there won't be any key in responses
		// just flat single model attribute
		jackson2JsonView.setExtractValueFromSingleKeyModel(true);
		
		return jackson2JsonView;
	}

}
