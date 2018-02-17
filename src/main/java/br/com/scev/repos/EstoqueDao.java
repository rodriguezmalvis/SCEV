package br.com.scev.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.scev.models.Estoque;

public interface EstoqueDao extends JpaRepository<Estoque, Integer> {
	
	@Query(" select e from Estoque e where e.idEstoque <> ? ")
	public List<Estoque> getOutrosEstoques(Integer idEstoque);

}
