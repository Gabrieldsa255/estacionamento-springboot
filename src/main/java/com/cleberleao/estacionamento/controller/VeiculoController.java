package com.cleberleao.estacionamento.controller;

import com.cleberleao.estacionamento.dto.RequestVeiculoDTO;
import com.cleberleao.estacionamento.dto.ResponseVeiculoDTO;
import com.cleberleao.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculos")
@CrossOrigin("*")
public class VeiculoController {

    @Autowired
    VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<ResponseVeiculoDTO> cadastrar(@RequestBody RequestVeiculoDTO dto) {
        ResponseVeiculoDTO responseVeiculoDTO = veiculoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseVeiculoDTO);
    }
}
