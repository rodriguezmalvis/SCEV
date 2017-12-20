package br.com.scev.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import br.com.scev.models.Movimentacao;
import br.com.scev.models.ProdutoEstoque;
import br.com.scev.models.TipoMovimentacao;
import br.com.scev.repos.MovimentacaoDao;
import br.com.scev.repos.ProdutoEstoqueDao;

@Component
public class RegrasMovimentacao {

	@Autowired
	private ProdutoEstoqueDao produtoEstoqueDao;
	
	@Autowired
	private MovimentacaoDao movimentacaoDao;

	public void realizaMovimentacao(Movimentacao movimentacao, ProdutoEstoque produtoEstoque) {
		if(produtoEstoque == null) {
			produtoEstoque = new ProdutoEstoque(movimentacao.getQuantidade(), movimentacao.getEstoque(), movimentacao.getProduto());
			produtoEstoqueDao.save(produtoEstoque);
		}else {
			
			if(movimentacao.getTipo() == TipoMovimentacao.Saida) {
				produtoEstoque.setQuantidade(produtoEstoque.getQuantidade().subtract(movimentacao.getQuantidade()));
			}else {
				produtoEstoque.setQuantidade(produtoEstoque.getQuantidade().add(movimentacao.getQuantidade()));
			}
			
			produtoEstoqueDao.save(produtoEstoque);
		}
		
		movimentacaoDao.save(movimentacao);
	}

	public String verificaEstoqueMovimentacao(Movimentacao movimentacao, ProdutoEstoque produtoEstoque) {
		
		String erro = "";
		
		if(produtoEstoque == null && movimentacao.getTipo() == TipoMovimentacao.Saida) {
			erro = "Estoque selecionado não tem itens para o tipo de movimentação";
		}else if(movimentacao.getTipo() == TipoMovimentacao.Saida && (produtoEstoque.getQuantidade().intValue() < movimentacao.getQuantidade().intValue())) {
			erro = "Estoque selecionado não tem itens suficientes para o tipo de movimentação."
					+ " Movimentação: "+movimentacao.getQuantidade().intValue()+" / Estoque: "+produtoEstoque.getQuantidade().intValue();
		}
		
		return erro;
	}
	
}
