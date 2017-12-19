package br.com.scev.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.scev.models.Movimentacao;

public interface MovimentacaoDao extends JpaRepository<Movimentacao, Integer> {
	
}
