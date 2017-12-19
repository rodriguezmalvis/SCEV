package br.com.scev.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TransferenciaEstoque {
	
	private BigInteger quantidade;
	private TipoMovimentacao tipo;
	private Estoque origem;
	private Estoque destino;
	private Produto produto;
	private String descricao;
	
	public List<Movimentacao> getMovimentaçoes() {
		List<Movimentacao> movimentacoes = new ArrayList<>();
		
		movimentacoes.add(new Movimentacao(this.tipo, this.descricao, this.quantidade, this.origem, this.produto));
		
		movimentacoes.add(new Movimentacao(TipoMovimentacao.Entrada, this.descricao, this.quantidade, this.destino, this.produto));
		
		return movimentacoes;
	}
	
	public BigInteger getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigInteger quantidade) {
		this.quantidade = quantidade;
	}
	public TipoMovimentacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	}
	public Estoque getOrigem() {
		return origem;
	}
	public void setOrigem(Estoque origem) {
		this.origem = origem;
	}
	public Estoque getDestino() {
		return destino;
	}
	public void setDestino(Estoque destino) {
		this.destino = destino;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
