package com.cleberleao.estacionamento.entity;

import com.cleberleao.estacionamento.dto.RequestVeiculoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String marca;

    private String cor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipo;

    private LocalDate dataCriacao;
    private LocalDate dataUpdate;

    public Veiculo(RequestVeiculoDTO dto) {
        this.modelo = dto.getModelo();
        this.cor = dto.getCor();
        this.placa = dto.getPlaca();
        this.marca = dto.getMarca();
        this.tipo = dto.getTipo();
        this.dataCriacao = LocalDate.now();
    }
}
