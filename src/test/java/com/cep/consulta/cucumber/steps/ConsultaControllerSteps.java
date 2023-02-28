package com.cep.consulta.cucumber.steps;

import com.cep.consulta.dto.ConsultaEnderecoResponseDTO;
import com.cep.consulta.interfaces.ViaCepClientImpl;
import com.cep.consulta.service.ConsultaEnderecoService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cep.consulta.controller.ConsultaController;
import com.cep.consulta.dto.ConsultaEnderecoDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;
import com.cep.consulta.exceptions.RegiaoNaoEncontradaException;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;


public class ConsultaControllerSteps {

    private ConsultaController consultaController;
    private ResponseEntity<ConsultaEnderecoResponseDTO> response;

    private ConsultaController consultaControllerMock;

    private Exception exception;

    public ConsultaControllerSteps() {
        MockitoAnnotations.initMocks(this);
    }

    @Dado("um CEP válido")
    public void dado_um_cep_valido() {
        consultaController = new ConsultaController(new ConsultaEnderecoService(new ViaCepClientImpl(new RestTemplateBuilder())));
    }

    @Dado("um CEP inválido")
    public void dado_um_cep_invalido() {
        consultaController = new ConsultaController(new ConsultaEnderecoService(new ViaCepClientImpl(new RestTemplateBuilder())));
    }

    @Dado("um CEP com região não encontrada")
    public void dado_um_cep_com_regiao_nao_encontrada() {
        consultaController = new ConsultaController(new ConsultaEnderecoService(new ViaCepClientImpl(new RestTemplateBuilder())));
    }

    @Quando("enviar uma requisição para a consulta de endereço com o CEP {string}")
    public void quando_enviar_uma_requisicao_para_a_consulta_de_endereco(String cep) throws CepNaoEncontradoException, RegiaoNaoEncontradaException {
        ConsultaEnderecoDTO consultaEnderecoDTO = new ConsultaEnderecoDTO();
        consultaEnderecoDTO.setCep(cep);

        try {
            if (consultaControllerMock != null) {
                response = consultaControllerMock.consultaEndereco(consultaEnderecoDTO);
            } else {
                response = consultaController.consultaEndereco(consultaEnderecoDTO);
            }
        } catch (CepNaoEncontradoException e) {
            exception = e;
        }
    }

    @Então("o serviço retorna o endereço e o valor do frete")
    public void entao_o_servico_retorna_o_endereco_e_o_valor_do_frete() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("72155-613", response.getBody().getCep());
        Assert.assertEquals("Brasília", response.getBody().getCidade());
        Assert.assertEquals("DF", response.getBody().getEstado());
        Assert.assertEquals(12.5, response.getBody().getFrete(), 0.1);
    }

    @Então("o serviço retorna um erro informando que o CEP não foi encontrado")
    public void entao_o_servico_retorna_um_erro_informando_que_o_cep_nao_foi_encontrado() {
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof CepNaoEncontradoException);
    }

}
