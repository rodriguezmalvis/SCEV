package br.com.scev.models;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@JsonBackReference
	private List<Movimentacao> movimentacoes;
	
	public BigInteger getQuantidadeProdutos() {
		
		BigInteger quantidade;
		
		try {
			quantidade = this.produtosEstoque.stream().map(ProdutoEstoque::getQuantidade).reduce(BigInteger::add).get();
		} catch (Exception e) {
			quantidade = BigInteger.ZERO;
		}
		
		return quantidade;
		
	}
	
	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}
	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
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
