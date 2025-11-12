package com.cleberleao.estacionamento.service;

import com.cleberleao.estacionamento.dto.RequestVeiculoDTO;
import com.cleberleao.estacionamento.dto.ResponseVeiculoDTO;
import com.cleberleao.estacionamento.entity.Veiculo;
import com.cleberleao.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    @Autowired
    VeiculoRepository veiculoRepository;

    public ResponseVeiculoDTO cadastrar(RequestVeiculoDTO dto) {
        Veiculo veiculo = new Veiculo(dto);
        Veiculo save = veiculoRepository.save(veiculo);
        return new ResponseVeiculoDTO(save);
    }
}
