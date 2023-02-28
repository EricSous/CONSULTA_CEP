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
public class ConsultaEnderecoResponseDTO {

    @Schema(description = "CEP encontrado, resposta da aplicação.")
    private String cep;
    @Schema(description = "Rua referente ao CEP enviado.")
    private String rua;
    @Schema(description = "Complemento referente ao CEP enviado.")
    private String complemento;
    @Schema(description = "Bairro referente ao CEP enviado.")
    private String bairro;
    @Schema(description = "Cidade referente ao CEP enviado.")
    private String cidade;
    @Schema(description = "Estado referente ao CEP enviado.")
    private String estado;
    @Schema(description = "Frete calculado para sua região.")
    private Double frete;

}
