package com.teste_intuitite_care.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.teste_intuitite_care.model.dto.OperadoraDto;
import com.teste_intuitite_care.repository.OperadorasRepository;

@Service
public class OperadorasService {

    @Autowired
    private OperadorasRepository repository;

    public List<OperadoraDto> buscarOperadorasPorNome(String nome){
    	Pageable pageable = PageRequest.of(0, 10);
        return repository.findByNomeFantasiaContainingIgnoreCase(nome, pageable).stream()
        		.map(operadora -> operadora.criarDto(operadora))
        		.toList();
    }
}
