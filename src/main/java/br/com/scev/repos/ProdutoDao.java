package br.com.scev.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.scev.models.Produto;

public interface ProdutoDao extends JpaRepository<Produto, Integer> {

}
