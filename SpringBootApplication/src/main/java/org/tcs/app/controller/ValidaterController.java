package org.tcs.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tcs.app.model.Product;
import org.tcs.app.request.ProductRequest;
import org.tcs.app.service.ProductService;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ValidaterController {
	@Autowired
	private ProductService productService;

	@PostMapping(value = "/insertproductdata")
	public ResponseEntity<String> inserProductData(@RequestBody @Validated ProductRequest productRequest)
			throws JsonProcessingException {

		Product prdt = new Product();
		prdt.setId(productRequest.getId());
		prdt.setName(productRequest.getName());
		prdt.setPrice(productRequest.getPrice());
		prdt.setColour(productRequest.getColour());
		prdt.setCountry(productRequest.getCountry());
		prdt.setCreatedDate(productRequest.getCreatedDate());
		prdt.setUpdatedDate(productRequest.getUpdatedDate());

		String str = productService.insertPrdtData(prdt);
		return new ResponseEntity<String>(str, HttpStatus.OK);

	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String, String>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return errors;

	}
}
