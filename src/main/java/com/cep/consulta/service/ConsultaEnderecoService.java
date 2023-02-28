package com.cep.consulta.service;

import com.cep.consulta.dto.ConsultaEnderecoResponseDTO;
import com.cep.consulta.dto.ViaCepResponseDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;
import com.cep.consulta.exceptions.RegiaoNaoEncontradaException;
import com.cep.consulta.interfaces.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConsultaEnderecoService {

    private static final Map<String, Double> FRETES_POR_REGIAO = Map.of(
            "SP,RJ,ES,MG", 7.85,
            "MT,MS,GO,DF", 12.50,
            "MA,PI,CE,RN,PB,PE,AL,SE,BA", 15.98,
            "PR,SC,RS", 17.30,
            "AC,AM,AP,PA,RO,RR,TO", 20.83
    );

    private final ViaCepClient viaCepClient;

    public ConsultaEnderecoResponseDTO consultarEnderecoPorCep(String cep) throws CepNaoEncontradoException, RegiaoNaoEncontradaException {
        ViaCepResponseDTO viaCepResponseDTO = viaCepClient.consultarEnderecoPorCep(cep);
        if (viaCepResponseDTO == null) {
            throw new CepNaoEncontradoException();
        }

        ConsultaEnderecoResponseDTO consultaEnderecoResponseDTO = new ConsultaEnderecoResponseDTO();
        consultaEnderecoResponseDTO.setCep(viaCepResponseDTO.getCep());
        consultaEnderecoResponseDTO.setRua(viaCepResponseDTO.getLogradouro());
        consultaEnderecoResponseDTO.setComplemento(viaCepResponseDTO.getComplemento());
        consultaEnderecoResponseDTO.setBairro(viaCepResponseDTO.getBairro());
        consultaEnderecoResponseDTO.setCidade(viaCepResponseDTO.getLocalidade());
        consultaEnderecoResponseDTO.setEstado(viaCepResponseDTO.getUf());

        consultaEnderecoResponseDTO.setFrete(calcularFrete(viaCepResponseDTO.getUf()));

        return consultaEnderecoResponseDTO;
    }

    Double calcularFrete(String uf) throws RegiaoNaoEncontradaException {
        for (String regiao : FRETES_POR_REGIAO.keySet()) {
            if (regiao.contains(uf)) {
                return FRETES_POR_REGIAO.get(regiao);
            }
        }

        throw new RegiaoNaoEncontradaException();
    }
}


