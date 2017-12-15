package br.com.scev.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.scev.models.Estoque;

public interface EstoqueDao extends JpaRepository<Estoque, Integer> {

}
