package com.teste_intuitite_care.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste_intuitite_care.service.OperadorasService;

@RestController
@RequestMapping(value = "/api/v1/operadoras")
@CrossOrigin("*")
public class OperadoraController {

    @Autowired
    private OperadorasService service;

    @GetMapping("/{name}")
    public ResponseEntity<?> buscarPorNome(@PathVariable("name")String name){
        var resposta = service.buscarOperadorasPorNome(name);
        return ResponseEntity.ok(resposta);
    }
}
