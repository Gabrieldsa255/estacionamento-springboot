package com.cleberleao.estacionamento.dto;

import com.cleberleao.estacionamento.entity.Estacionar;
import com.cleberleao.estacionamento.entity.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVeiculoDTO {

    private int id;

    private String placa;

    private String modelo;

    private String cor;

    private LocalDate dataCriacao;

    private LocalDate dataUpdate;

    public ResponseVeiculoDTO(Veiculo veiculo) {
        this.cor = veiculo.getCor();
        this.placa = veiculo.getPlaca();
        this.modelo = veiculo.getModelo();
    }
}
