package com.example.springboot3demo.configuration;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalControllerAdvice {

	public static final Log LOGGER = LogFactory.getLog(GlobalControllerAdvice.class);

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ProblemDetail onException(HttpServletRequest request) {
		if (LOGGER.isDebugEnabled()) {
			request.getAttributeNames().asIterator().forEachRemaining(attributeName ->
					LOGGER.debug("AttributeName: " + attributeName));
		}
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Params is error.");
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		String[] attributeNames = webRequest.getAttributeNames(RequestAttributes.SCOPE_REQUEST);
		if (LOGGER.isDebugEnabled()) {
			for (String attributeName : attributeNames) {
				LOGGER.debug(webRequest.getAttribute(attributeName, RequestAttributes.SCOPE_REQUEST));
			}
		}

		BindingResult bindingResult = webDataBinder.getBindingResult();
		Map<String, Object> model = bindingResult.getModel();
	}
}
