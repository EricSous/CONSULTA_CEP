package com.cep.consulta.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleExceptionMethodArgumentNotValidException() {
        String field1 = "field1";
        String message1 = "Message for field1";
        String field2 = "field2";
        String message2 = "Message for field2";

        Object targetObject = new Object();
        String objectName = "objectName";

        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError(objectName, field1, message1));
        fieldErrors.add(new FieldError(objectName, field2, message2));

        BindingResult bindingResult = new BeanPropertyBindingResult(targetObject, objectName);
        for (FieldError error : fieldErrors) {
            bindingResult.addError(error);
        }

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assertNotNull(responseBody);

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("status"));

        @SuppressWarnings("unchecked")
        List<String> errors = (List<String>) responseBody.get("errors");
        assertNotNull(errors);
        assertEquals(fieldErrors.size(), errors.size());
        assertTrue(errors.contains(field1 + ": " + message1));
        assertTrue(errors.contains(field2 + ": " + message2));
    }


    @Test
    void handleCepNaoEncontradoException() {
        CepNaoEncontradoException exception = new CepNaoEncontradoException();

        ResponseEntity<Object> response = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.get("status"));
        assertEquals("CEP não encontrado", responseBody.get("message"));
    }

    @Test
    void handleRegiaoNaoEncontradaException() {
        RegiaoNaoEncontradaException exception = new RegiaoNaoEncontradaException();

        ResponseEntity<Object> response = globalExceptionHandler.handleException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.get("status"));
        assertEquals("Região não encontrada", responseBody.get("message"));
    }

}
