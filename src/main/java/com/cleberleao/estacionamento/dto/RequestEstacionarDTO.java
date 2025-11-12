package com.cleberleao.estacionamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestEstacionarDTO {

    // placa veículo
    private String placa;

    // vaga
    private Integer vaga;

    // motorista
    private String usuarioResponsavel;
}
