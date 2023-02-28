package com.cep.consulta.interfaces;

import com.cep.consulta.dto.ViaCepResponseDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;

public interface ViaCepClient {

    ViaCepResponseDTO consultarEnderecoPorCep(String cep) throws CepNaoEncontradoException;
}
