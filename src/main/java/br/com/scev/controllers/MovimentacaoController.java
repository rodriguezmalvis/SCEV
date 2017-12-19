package br.com.scev.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.scev.models.Movimentacao;
import br.com.scev.models.ProdutoEstoque;
import br.com.scev.models.TipoMovimentacao;
import br.com.scev.repos.EstoqueDao;
import br.com.scev.repos.MovimentacaoDao;
import br.com.scev.repos.ProdutoDao;
import br.com.scev.repos.ProdutoEstoqueDao;

@Controller
@RequestMapping("movimentacao")
public class MovimentacaoController {
	
	@Autowired
	private MovimentacaoDao movimentacaoDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	private EstoqueDao estoqueDao;
	
	@Autowired
	private ProdutoEstoqueDao produtoEstoqueDao;

	@GetMapping("form")
	public ModelAndView movimentacaoForm() {
		
		ModelAndView view = new ModelAndView("movimentacaoForm");
		
		view.addObject("movimentacao", new Movimentacao());
		view.addObject("tipoMovimentacao", TipoMovimentacao.values());
		view.addObject("produtos",produtoDao.findAll());
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}
	
	@PostMapping("cadastra")
	public ModelAndView cadastraMovimentacao(Movimentacao movimentacao, RedirectAttributes redirectAttributes) {
		
		ModelAndView view = new ModelAndView("redirect:form");
		boolean hasErro = false;	
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(movimentacao.getEstoque().getIdEstoque(), movimentacao.getProduto().getIdProduto());
				
		if(produtoEstoque == null && movimentacao.getTipo() == TipoMovimentacao.Saida) {
			view.addObject("alerta", "Estoque selecionado não tem itens para o tipo de movimentação");
			hasErro = true;
		}else if(movimentacao.getTipo() == TipoMovimentacao.Saida && (produtoEstoque.getQuantidade().intValue() < movimentacao.getQuantidade().intValue())) {
			view.addObject("alerta", "Estoque selecionado não tem itens suficientes para o tipo de movimentação."
					+ " Movimentação: "+movimentacao.getQuantidade().intValue()+" / Estoque: "+produtoEstoque.getQuantidade().intValue());
			hasErro = true;
		}
			
		if(hasErro) {
			movimentacao.setProduto(produtoDao.findOne(movimentacao.getProduto().getIdProduto()));
			movimentacao.setEstoque(estoqueDao.findOne(movimentacao.getEstoque().getIdEstoque()));
			view.setViewName("movimentacaoForm");
			view.addObject("movimentacao",movimentacao);
			view.addObject("produtos",produtoDao.findAll());
			view.addObject("estoques",estoqueDao.findAll());
			view.addObject("tipoMovimentacao", TipoMovimentacao.values());
		}else {
			
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
			redirectAttributes.addFlashAttribute("mensagem", "Movimentação cadastrada com sucesso");
		}
			
		
		return view;
		
	}
	
	@GetMapping("lista")
	public ModelAndView listaMovimentações() {
		
		ModelAndView view = new ModelAndView("listaMovimentacoes");
		
		List<Movimentacao> movimentacoes = movimentacaoDao.findAll();
		
		Collections.sort(movimentacoes,(p1,p2) -> p2.getIdMovimentacao().compareTo(p1.getIdMovimentacao()));
		
		view.addObject("movimentacoes", movimentacoes);
		
		return view;
		
	}
	
}
