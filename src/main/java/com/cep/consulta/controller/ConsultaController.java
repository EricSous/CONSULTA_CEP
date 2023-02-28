package com.cep.consulta.controller;

import com.cep.consulta.dto.ConsultaEnderecoDTO;
import com.cep.consulta.dto.ConsultaEnderecoResponseDTO;
import com.cep.consulta.exceptions.CepNaoEncontradoException;
import com.cep.consulta.exceptions.RegiaoNaoEncontradaException;
import com.cep.consulta.service.ConsultaEnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaEnderecoService consultaEnderecoService;

    @PostMapping("/consulta-endereco")
    public ResponseEntity<ConsultaEnderecoResponseDTO> consultaEndereco(@RequestBody ConsultaEnderecoDTO consultaEnderecoDTO)
            throws CepNaoEncontradoException, RegiaoNaoEncontradaException {

        ConsultaEnderecoResponseDTO enderecoDTO = consultaEnderecoService.consultarEnderecoPorCep(consultaEnderecoDTO.getCep());
        if (enderecoDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(enderecoDTO);
    }
}
