package com.example.springboot3demo.configuration;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

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
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ProblemDetail onException(HttpServletRequest request) {
		request.getAttributeNames().asIterator().forEachRemaining(attributeName ->
				System.out.println("AttributeName: " + attributeName));
		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Params is error.");
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
		String[] attributeNames = webRequest.getAttributeNames(RequestAttributes.SCOPE_REQUEST);
		for (String attributeName : attributeNames) {
			System.out.println(webRequest.getAttribute(attributeName, RequestAttributes.SCOPE_REQUEST));
		}

		BindingResult bindingResult = webDataBinder.getBindingResult();
		Map<String, Object> model = bindingResult.getModel();
	}
}
