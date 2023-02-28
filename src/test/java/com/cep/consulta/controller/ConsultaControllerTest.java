package com.cep.consulta.controller;

import com.cep.consulta.dto.ConsultaEnderecoDTO;
import com.cep.consulta.dto.ConsultaEnderecoResponseDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;
import com.cep.consulta.exceptions.RegiaoNaoEncontradaException;
import com.cep.consulta.service.ConsultaEnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ConsultaControllerTest {

    private ConsultaController consultaController;

    @Mock
    private ConsultaEnderecoService consultaEnderecoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        consultaController = new ConsultaController(consultaEnderecoService);
    }

    @Test
    void consultaEndereco_cepEncontrado() throws CepNaoEncontradoException, RegiaoNaoEncontradaException {
        String cep = "12345678";
        ConsultaEnderecoDTO consultaEnderecoDTO = new ConsultaEnderecoDTO(cep);
        ConsultaEnderecoResponseDTO enderecoDTO = new ConsultaEnderecoResponseDTO(cep, "Rua Teste", "complemento teste","Bairro teste", "Cidade Teste", "Estado Teste", 15.98);
        when(consultaEnderecoService.consultarEnderecoPorCep(cep)).thenReturn(enderecoDTO);

        ResponseEntity<ConsultaEnderecoResponseDTO> responseEntity = consultaController.consultaEndereco(consultaEnderecoDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(enderecoDTO, responseEntity.getBody());
    }

    @Test
    void consultaEndereco_cepNaoEncontrado() throws CepNaoEncontradoException, RegiaoNaoEncontradaException {
        String cep = "12345678";
        ConsultaEnderecoDTO consultaEnderecoDTO = new ConsultaEnderecoDTO(cep);
        when(consultaEnderecoService.consultarEnderecoPorCep(anyString())).thenReturn(null);

        ResponseEntity<ConsultaEnderecoResponseDTO> responseEntity = consultaController.consultaEndereco(consultaEnderecoDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
