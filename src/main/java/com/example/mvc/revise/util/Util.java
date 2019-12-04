package com.example.mvc.revise.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class Util implements InitializingBean {

	private static ModelMapper modelmapper;

	public static <T, S> T convertUsingModelMapper(final S sourceObject, final Class<T> targetType) {
		return modelmapper.map(sourceObject, targetType);
	}

	public static <T, S> T convertUsingModelMapper(final S sourceObject, final T targetObject) {
		modelmapper.map(sourceObject, targetObject);
		return targetObject;
	}

	public static <T, S> Collection<T> convertUsingModelMapper(final List<S> sourceObjectList, final Class<T> targetType) {
		if (sourceObjectList == null) {
			return null;
		}
		return sourceObjectList.stream().map(sourceObject -> modelmapper.map(sourceObject, targetType))
				.collect(Collectors.toList());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(modelmapper, " Model mapper injected null inside Util");
	}

	@Autowired
	public void setModelmapper(ModelMapper modelmapper) {
		Util.modelmapper = modelmapper;
	}

}
