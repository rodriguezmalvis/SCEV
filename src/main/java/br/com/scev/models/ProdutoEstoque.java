package br.com.scev.models;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProdutoEstoque {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private BigInteger quantidade;
	
	@ManyToOne
	@JoinColumn(name="id_estoque",nullable=false,unique=true)
	private Estoque estoque;
	
	@ManyToOne
	@JoinColumn(name="id_produto",nullable=false,unique=true)
	private Produto produto;
	
	public ProdutoEstoque() {
	}
	
	public ProdutoEstoque(BigInteger quantidade, Estoque estoque, Produto produto) {
		super();
		this.quantidade = quantidade;
		this.estoque = estoque;
		this.produto = produto;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigInteger quantidade) {
		this.quantidade = quantidade;
	}
	
}
