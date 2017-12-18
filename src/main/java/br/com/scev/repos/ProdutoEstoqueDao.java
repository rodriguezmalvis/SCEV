package br.com.scev.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.scev.models.ProdutoEstoque;

public interface ProdutoEstoqueDao extends JpaRepository<ProdutoEstoque, Integer>{
	
	@Query("select e from ProdutoEstoque e where e.estoque.idEstoque = ?1 and e.produto.idProduto = ?2")
	public ProdutoEstoque findByEstoqueProduto(Integer idEstoque, Integer idProduto);

}
