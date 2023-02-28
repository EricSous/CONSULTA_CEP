package com.cep.consulta.interfaces;

import com.cep.consulta.dto.ViaCepResponseDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClientImpl implements ViaCepClient {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";

    private final RestTemplate restTemplate;

    public ViaCepClientImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ViaCepResponseDTO consultarEnderecoPorCep(String cep) throws CepNaoEncontradoException {
        String url = String.format(VIA_CEP_URL, cep);
        try {
            ResponseEntity<ViaCepResponseDTO> responseEntity = restTemplate.getForEntity(url, ViaCepResponseDTO.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                ViaCepResponseDTO viaCepResponseDTO = responseEntity.getBody();
                if (viaCepResponseDTO != null && viaCepResponseDTO.getCep() != null) {
                    return viaCepResponseDTO;
                }
            }
            return null;
        } catch (RestClientException e) {
            throw new CepNaoEncontradoException();
        }
    }
}
