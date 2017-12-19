package br.com.scev.models;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames= {
				"id_estoque",
				"id_produto"
		})
})
public class ProdutoEstoque {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private BigInteger quantidade;
	
	@ManyToOne
	@JoinColumn(name="id_estoque",nullable=false)
	private Estoque estoque;
	
	@ManyToOne
	@JoinColumn(name="id_produto",nullable=false)
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
