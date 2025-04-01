package com.teste_intuitite_care.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.teste_intuitite_care.model.Operadora;

@Repository
public interface OperadorasRepository extends JpaRepository<Operadora, Long> {

	@Query(name = "SELECT O.REGISTRO_ANS, O.CNPJ, O.RAZAO_SOCIAL, O.NOME_FANTASIA, O.MODALIDADE"
			+ "FROM OPERADORAS_ATIVAS O"
			+ "WHERE NOME_FANTASIA LIKE :nome "
			+ "ORDER BY NOME_FANTASIA DESC;", nativeQuery = true)
	List<Operadora> findByNomeFantasia(@Param("nome") String nome);
	
	Page<Operadora> findByNomeFantasiaContainingIgnoreCase(@Param("nomeFantasia") String nomeFantasia, Pageable pageable);
	
	
}
