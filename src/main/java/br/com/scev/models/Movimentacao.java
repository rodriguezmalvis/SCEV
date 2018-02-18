package br.com.scev.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Movimentacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idMovimentacao;
	private TipoMovimentacao tipo;
	private String descricao;
	private BigInteger quantidade;
	private BigDecimal preco;
	
	@ManyToOne
	@JoinColumn(name="id_estoque",nullable=false)
	private Estoque estoque;
	
	@ManyToOne
	@JoinColumn(name="id_produto",nullable=false)
	private Produto produto;
	
	public Movimentacao(TipoMovimentacao tipo, String descricao, BigInteger quantidade, Estoque estoque,
			Produto produto) {
		super();
		this.tipo = tipo;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.estoque = estoque;
		this.produto = produto;
	}
	
	public Movimentacao() {
	}
	
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Integer getIdMovimentacao() {
		return idMovimentacao;
	}
	public void setIdMovimentacao(Integer idMovimentacao) {
		this.idMovimentacao = idMovimentacao;
	}
	public TipoMovimentacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigInteger getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigInteger quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
}
