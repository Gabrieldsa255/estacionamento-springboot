package com.cleberleao.estacionamento.service;

import com.cleberleao.estacionamento.dto.RequestEstacionarDTO;
import com.cleberleao.estacionamento.dto.ResponseEstacionarDTO;
import com.cleberleao.estacionamento.entity.Estacionar;
import com.cleberleao.estacionamento.entity.TipoVeiculo;
import com.cleberleao.estacionamento.entity.Veiculo;
import com.cleberleao.estacionamento.exception.RegistroNaoEncontradoException;
import com.cleberleao.estacionamento.exception.VagaOcupadaException;
import com.cleberleao.estacionamento.repository.EstacionarRepository;
import com.cleberleao.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstacionarService {

    private static final int TOTAL_VAGAS = 50;

    @Autowired
    EstacionarRepository estacionarRepository;

    @Autowired
    VeiculoRepository veiculoRepository;

    // entrada do veículo
    public ResponseEstacionarDTO cadastrar(RequestEstacionarDTO dto) {

        // verifica se veículo existe
        Veiculo veiculo = veiculoRepository.findByPlaca(dto.getPlaca())
                .orElseThrow(() -> new RegistroNaoEncontradoException(
                        "Veículo com placa " + dto.getPlaca() + " não encontrado. Cadastre primeiro em /veiculos.")
                );

        // verifica se já está estacionado
        estacionarRepository.findByVeiculo_PlacaAndSaidaIsNull(dto.getPlaca())
                .ifPresent(e -> {
                    throw new RuntimeException("Já existe um registro ativo para a placa " + dto.getPlaca());
                });

        // verifica vaga existente
        if (dto.getVaga() == null || dto.getVaga() <= 0 || dto.getVaga() > TOTAL_VAGAS) {
            throw new RuntimeException("Número da vaga inválido. Use de 1 a " + TOTAL_VAGAS);
        }

        // verifica se vaga está ocupada
        estacionarRepository.findByVagaAndSaidaIsNull(dto.getVaga())
                .ifPresent(e -> {
                    throw new VagaOcupadaException("Vaga " + dto.getVaga() + " já está ocupada");
                });

        // verifica quantidade de vagas no estacionameto
        long ocupadas = estacionarRepository.countBySaidaIsNull();
        if (ocupadas >= TOTAL_VAGAS) {
            throw new VagaOcupadaException("Estacionamento lotado");
        }

        Estacionar entity = new Estacionar(veiculo, dto);
        Estacionar save = estacionarRepository.save(entity);
        return new ResponseEstacionarDTO(save);
    }

    // vagas livres
    public List<Integer> buscarVagasLivres() {
        List<Estacionar> ativos = estacionarRepository.findBySaidaIsNull();
        Set<Integer> ocupadas = ativos.stream()
                .map(Estacionar::getVaga)
                .collect(Collectors.toSet());

        List<Integer> vagasLivres = new ArrayList<>();
        for (int i = 1; i <= TOTAL_VAGAS; i++) {
            if (!ocupadas.contains(i)) {
                vagasLivres.add(i);
            }
        }
        return vagasLivres;
    }

    // vagas ocupadas
    public List<ResponseEstacionarDTO> buscarVagasOcupadas() {
        return estacionarRepository.findBySaidaIsNull()
                .stream()
                .map(ResponseEstacionarDTO::new)
                .collect(Collectors.toList());
    }


    public Map<String, Object> resumoVagas() {
        long ocupadas = estacionarRepository.countBySaidaIsNull();
        long livres = TOTAL_VAGAS - ocupadas;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalVagas", TOTAL_VAGAS);
        map.put("vagasOcupadas", ocupadas);
        map.put("vagasLivres", livres);
        map.put("totalCarros", ocupadas);
        return map;
    }

    // saída pola placa
    public ResponseEstacionarDTO registrarSaida(String placa) {
        Estacionar est = estacionarRepository.findByVeiculo_PlacaAndSaidaIsNull(placa)
                .orElseThrow(() -> new RegistroNaoEncontradoException(
                        "Não existe veículo com placa " + placa + " estacionado no momento.")
                );

        // registrar saída
        est.registrarSaida();
        estacionarRepository.save(est);

        // calcular horas
        long millis = est.getSaida().getTime() - est.getEntrada().getTime();
        long horas = (long) Math.ceil(millis / (1000.0 * 60 * 60));
        if (horas <= 0) horas = 1;

        double valorHora = valorPorHora(est.getVeiculo().getTipo());
        double total = valorHora * horas;

        ResponseEstacionarDTO dto = new ResponseEstacionarDTO(est);
        dto.setHorasCobradas(horas);
        dto.setValorPorHora(valorHora);
        dto.setValorTotal(total);

        return dto;
    }

    private double valorPorHora(TipoVeiculo tipo) {
        return switch (tipo) {
            case CARRO_PEQUENO -> 16.0;
            case CARRO_GRANDE -> 25.0;
            case MOTO -> 8.0;
        };
    }
}
