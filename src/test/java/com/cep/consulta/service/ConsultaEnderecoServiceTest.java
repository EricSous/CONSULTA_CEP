package com.cep.consulta.service;

import com.cep.consulta.dto.ConsultaEnderecoResponseDTO;
import com.cep.consulta.dto.ViaCepResponseDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;
import com.cep.consulta.exceptions.RegiaoNaoEncontradaException;
import com.cep.consulta.interfaces.ViaCepClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ConsultaEnderecoServiceTest {

    private static final Map<String, Double> FRETES_POR_REGIAO = Map.of(
            "SP,RJ,ES,MG", 7.85,
            "MT,MS,GO,DF", 12.50,
            "MA,PI,CE,RN,PB,PE,AL,SE,BA", 15.98,
            "PR,SC,RS", 17.30,
            "AC,AM,AP,PA,RO,RR,TO", 20.83
    );

    private ConsultaEnderecoService consultaEnderecoService;

    @Mock
    private ViaCepClient viaCepClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        consultaEnderecoService = new ConsultaEnderecoService(viaCepClient);
    }

    @Test
    @DisplayName("Testa o cálculo do frete para o estado de São Paulo")
    public void testCalcularFrete_SP() throws RegiaoNaoEncontradaException {
        Double expectedFrete = 7.85;
        Double actualFrete = consultaEnderecoService.calcularFrete("SP");
        Assertions.assertEquals(expectedFrete, actualFrete);
    }

    @Test
    @DisplayName("Testa o cálculo do frete para o estado de Rondônia")
    public void testCalcularFrete_RO() throws RegiaoNaoEncontradaException {
        Double expectedFrete = 20.83;
        Double actualFrete = consultaEnderecoService.calcularFrete("RO");
        Assertions.assertEquals(expectedFrete, actualFrete);
    }

    @Test
    @DisplayName("Testa a consulta de endereço por CEP")
    public void testConsultarEnderecoPorCep() throws CepNaoEncontradoException, RegiaoNaoEncontradaException {
        String cep = "12345678";

        ViaCepResponseDTO viaCepResponseDTO = new ViaCepResponseDTO();
        viaCepResponseDTO.setCep("12345678");
        viaCepResponseDTO.setLogradouro("Rua Teste");
        viaCepResponseDTO.setComplemento("Complemento Teste");
        viaCepResponseDTO.setBairro("Bairro Teste");
        viaCepResponseDTO.setLocalidade("Cidade Teste");
        viaCepResponseDTO.setUf("SP");

        when(viaCepClient.consultarEnderecoPorCep(anyString())).thenReturn(viaCepResponseDTO);

        ConsultaEnderecoResponseDTO expectedResponseDTO = new ConsultaEnderecoResponseDTO();
        expectedResponseDTO.setCep("12345678");
        expectedResponseDTO.setRua("Rua Teste");
        expectedResponseDTO.setComplemento("Complemento Teste");
        expectedResponseDTO.setBairro("Bairro Teste");
        expectedResponseDTO.setCidade("Cidade Teste");
        expectedResponseDTO.setEstado("SP");
        expectedResponseDTO.setFrete(7.85);

        ConsultaEnderecoResponseDTO actualResponseDTO = consultaEnderecoService.consultarEnderecoPorCep(cep);

        Assertions.assertEquals(expectedResponseDTO.getCep(), actualResponseDTO.getCep());
        Assertions.assertEquals(expectedResponseDTO.getRua(), actualResponseDTO.getRua());
        Assertions.assertEquals(expectedResponseDTO.getComplemento(), actualResponseDTO.getComplemento());
        Assertions.assertEquals(expectedResponseDTO.getBairro(), actualResponseDTO.getBairro());
        Assertions.assertEquals(expectedResponseDTO.getCidade(), actualResponseDTO.getCidade());
        Assertions.assertEquals(expectedResponseDTO.getEstado(), actualResponseDTO.getEstado());
        Assertions.assertEquals(expectedResponseDTO.getFrete(), actualResponseDTO.getFrete());
    }


}
