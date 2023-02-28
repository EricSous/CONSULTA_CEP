package com.cep.consulta.interfaces;

import com.cep.consulta.dto.ViaCepResponseDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ViaCepClientImplTest {

    private ViaCepClient viaCepClient;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        viaCepClient = new ViaCepClientImpl(new RestTemplateBuilder());
        String url = "https://viacep.com.br/ws/01001000/json/";
        Class<ViaCepResponseDTO> responseType = ViaCepResponseDTO.class;
        when(restTemplate.getForEntity(url, responseType))
                .thenReturn(new ResponseEntity(responseType, HttpStatus.OK));
    }

    @Test
    public void testConsultarEnderecoPorCep() throws CepNaoEncontradoException {
        ViaCepResponseDTO expectedResponse = new ViaCepResponseDTO();
        expectedResponse.setCep("01001-000");
        when(restTemplate.getForEntity(any(), any()))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        ViaCepResponseDTO actualResponse = viaCepClient.consultarEnderecoPorCep("01001000");

        assertEquals(expectedResponse.getCep(), actualResponse.getCep());
    }

    @Test
    public void testConsultarEnderecoPorCep_ThrowsCepNaoEncontradoException() {
        when(restTemplate.getForEntity(any(), any()))
                .thenThrow(new RestClientException("CEP não encontrado"));

        assertThrows(CepNaoEncontradoException.class, () -> viaCepClient.consultarEnderecoPorCep("312"));
    }
}
