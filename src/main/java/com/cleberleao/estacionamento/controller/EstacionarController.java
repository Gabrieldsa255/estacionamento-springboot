package com.cleberleao.estacionamento.controller;

import com.cleberleao.estacionamento.dto.RequestEstacionarDTO;
import com.cleberleao.estacionamento.dto.ResponseEstacionarDTO;
import com.cleberleao.estacionamento.service.EstacionarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/estacionar")
@CrossOrigin("*")
public class EstacionarController {

    @Autowired
    EstacionarService estacionarService;

    @PostMapping
    public ResponseEntity<ResponseEstacionarDTO> cadastrar(@RequestBody RequestEstacionarDTO dto){
        ResponseEstacionarDTO responseEstacionarDTO = estacionarService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseEstacionarDTO);
    }

    @GetMapping("/vagas-livres")
    public ResponseEntity<List<Integer>> vagasLivres() {
        List<Integer> vagas = estacionarService.buscarVagasLivres();
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/vagas-ocupadas")
    public ResponseEntity<List<ResponseEstacionarDTO>> vagasOcupadas() {
        List<ResponseEstacionarDTO> dto = estacionarService.buscarVagasOcupadas();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/resumo")
    public ResponseEntity<Map<String, Object>> resumo() {
        return ResponseEntity.ok(estacionarService.resumoVagas());
    }

    @PostMapping("/saida/{placa}")
    public ResponseEntity<ResponseEstacionarDTO> registrarSaida(@PathVariable String placa) {
        ResponseEstacionarDTO dto = estacionarService.registrarSaida(placa);
        return ResponseEntity.ok(dto);
    }
}
