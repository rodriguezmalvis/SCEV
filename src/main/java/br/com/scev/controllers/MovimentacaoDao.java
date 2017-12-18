package br.com.scev.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.scev.models.Movimentacao;

public interface MovimentacaoDao extends JpaRepository<Movimentacao, Integer> {
	
}
