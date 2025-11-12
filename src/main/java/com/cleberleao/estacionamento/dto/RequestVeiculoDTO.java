package com.cleberleao.estacionamento.dto;

import com.cleberleao.estacionamento.entity.TipoVeiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestVeiculoDTO {

    private String placa;
    private String modelo;
    private String marca;
    private String cor;
    private TipoVeiculo tipo;
}
