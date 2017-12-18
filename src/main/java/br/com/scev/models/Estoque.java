package br.com.scev.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Estoque {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idEstoque;
	private String nome;
	private String descricao;
	private TipoEstoque tipo;
	
	@OneToMany(mappedBy="estoque")
	private List<ProdutoEstoque> produtosEstoque;
	
	@OneToMany(mappedBy="estoque")
	private List<Movimentacao> movimentacoes;
	
	public List<ProdutoEstoque> getProdutosEstoque() {
		return produtosEstoque;
	}
	public void setProdutosEstoque(List<ProdutoEstoque> produtosEstoque) {
		this.produtosEstoque = produtosEstoque;
	}
	public Integer getIdEstoque() {
		return idEstoque;
	}
	public void setIdEstoque(Integer idEstoque) {
		this.idEstoque = idEstoque;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public TipoEstoque getTipo() {
		return tipo;
	}
	public void setTipo(TipoEstoque tipoEstoque) {
		this.tipo = tipoEstoque;
	}
	
	

}
