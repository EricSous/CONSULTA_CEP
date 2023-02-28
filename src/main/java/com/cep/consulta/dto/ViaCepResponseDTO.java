package com.cep.consulta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViaCepResponseDTO {

    @Schema(description = "CEP encontrado na resposta da requisição.")
    private String cep;
    @Schema(description = "Logradouro encontrado na resposta da requisição.")
    private String logradouro;
    @Schema(description = "Complemento encontrado na resposta da requisição.")
    private String complemento;
    @Schema(description = "Bairro encontrado na resposta da requisição.")
    private String bairro;
    @Schema(description = "Localidade encontrado na resposta da requisição.")
    private String localidade;
    @Schema(description = "UF encontrado na resposta da requisição.")
    private String uf;

}