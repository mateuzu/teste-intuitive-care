package com.teste_intuitite_care.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OperadoraDto {

	private Long registroAns;
	private String cnpj;
	private String nomeFantasia;
	private String modalidade;
	
	public OperadoraDto() {
		// TODO Auto-generated constructor stub
	}
}
