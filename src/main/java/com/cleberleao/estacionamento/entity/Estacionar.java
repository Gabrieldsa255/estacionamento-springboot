package com.cleberleao.estacionamento.entity;

import com.cleberleao.estacionamento.dto.RequestEstacionarDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estacionar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // veículo
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    // vaga
    @Column(nullable = false)
    private Integer vaga;

    // horário de entrada e saída
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date saida;

    // motorista
    @Column(nullable = false)
    private String usuarioResponsavel;

    private LocalDate dataCriacao;
    private LocalDate dataUpdate;

    public Estacionar(Veiculo veiculo, RequestEstacionarDTO dto) {
        this.veiculo = veiculo;
        this.vaga = dto.getVaga();
        this.usuarioResponsavel = dto.getUsuarioResponsavel();
        this.entrada = new Date();
        this.dataCriacao = LocalDate.now();
    }

    public void registrarSaida() {
        this.saida = new Date();
        this.dataUpdate = LocalDate.now();
    }

    public boolean estaAtivo() {
        return this.saida == null;
    }
}
