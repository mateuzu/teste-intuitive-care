package com.teste_intuitite_care.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teste_intuitite_care.model.dto.OperadoraDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "OPERADORAS_ATIVAS")
@Getter
@Setter
public class Operadora {

    @Id
    private Long registroAns;

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String modalidade;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String ddd;
    private String telefone;
    private String fax;
    private String enderecoEletronico;
    private String representante;
    private String cargoRepresentante;
    private String regiaoDeComercializacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataRegistroAns;

    public Operadora() {
    	
    }

    public OperadoraDto criarDto(Operadora operadora) {
    	return new OperadoraDto(operadora.getRegistroAns(), operadora.getCnpj(), operadora.getNomeFantasia(), operadora.getModalidade());
    }
}
