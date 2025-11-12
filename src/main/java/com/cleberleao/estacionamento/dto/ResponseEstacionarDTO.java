package com.cleberleao.estacionamento.dto;

import com.cleberleao.estacionamento.entity.Estacionar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEstacionarDTO {

    private Integer id;
    private ResponseVeiculoDTO veiculo;
    private Integer vaga;
    private String usuarioResponsavel;
    private Date entrada;
    private Date saida;

    // saída
    private Long horasCobradas;
    private Double valorPorHora;
    private Double valorTotal;

    public ResponseEstacionarDTO(Estacionar estacionar) {
        this.id = estacionar.getId();
        this.veiculo = new ResponseVeiculoDTO(estacionar.getVeiculo());
        this.vaga = estacionar.getVaga();
        this.usuarioResponsavel = estacionar.getUsuarioResponsavel();
        this.entrada = estacionar.getEntrada();
        this.saida = estacionar.getSaida();
    }
}
