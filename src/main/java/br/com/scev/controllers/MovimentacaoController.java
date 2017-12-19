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
import br.com.scev.models.TransferenciaEstoque;
import br.com.scev.negocio.RegrasMovimentacao;
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
	
	@Autowired
	private RegrasMovimentacao regrasMov;

	@GetMapping("form")
	public ModelAndView movimentacaoForm() {
		
		ModelAndView view = new ModelAndView("movimentacaoForm");
		
		view.addObject("movimentacao", new Movimentacao());
		view.addObject("tipoMovimentacao", TipoMovimentacao.values());
		view.addObject("produtos",produtoDao.findAll());
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}
	
	@GetMapping("transferenciaForm")
	public ModelAndView tranferenciaForm() {
		
		ModelAndView view = new ModelAndView("transferenciaForm");
		
		view.addObject("transferenciaEstoque", new TransferenciaEstoque());
		view.addObject("tipoMovimentacao", TipoMovimentacao.Saida);
		view.addObject("produtos",produtoDao.findAll());
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}
	
	@PostMapping("transfere")
	public ModelAndView tranfereEstoque(TransferenciaEstoque transferencia) {
		
		ModelAndView view = new ModelAndView("redirect:transferenciaForm");
		
		List<Movimentacao> movimentaçoes = transferencia.getMovimentaçoes();
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(transferencia.getOrigem().getIdEstoque(), transferencia.getProduto().getIdProduto());
		
		boolean hasErro = regrasMov.verificaEstoqueMovimentacao(movimentaçoes.get(0), view, produtoEstoque);
		
		if(hasErro) {
			transferencia.setProduto(produtoDao.findOne(transferencia.getProduto().getIdProduto()));
			transferencia.setOrigem(estoqueDao.findOne(transferencia.getOrigem().getIdEstoque()));
			transferencia.setDestino(estoqueDao.findOne(transferencia.getDestino().getIdEstoque()));
			view.setViewName("transferenciaForm");
			view.addObject("transferenciaEstoque",transferencia);
			view.addObject("produtos",produtoDao.findAll());
			view.addObject("estoques",estoqueDao.findAll());
			view.addObject("tipoMovimentacao", TipoMovimentacao.Saida);
		}else {
			
			transferencia.getMovimentaçoes().stream().forEach(m -> {
				ProdutoEstoque produtoEstoqueMovimentacao = 
						produtoEstoqueDao.findByEstoqueProduto(m.getEstoque().getIdEstoque(), m.getProduto().getIdProduto());
				regrasMov.realizaMovimentacao(m, produtoEstoqueMovimentacao);
			});
			
		}
		
		return view;
		
	}
	
	@PostMapping("cadastra")
	public ModelAndView cadastraMovimentacao(Movimentacao movimentacao, RedirectAttributes redirectAttributes) {
		
		ModelAndView view = new ModelAndView("redirect:form");
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(movimentacao.getEstoque().getIdEstoque(), movimentacao.getProduto().getIdProduto());
				
		boolean hasErro = regrasMov.verificaEstoqueMovimentacao(movimentacao, view, produtoEstoque);
			
		if(hasErro) {
			movimentacao.setProduto(produtoDao.findOne(movimentacao.getProduto().getIdProduto()));
			movimentacao.setEstoque(estoqueDao.findOne(movimentacao.getEstoque().getIdEstoque()));
			view.setViewName("movimentacaoForm");
			view.addObject("movimentacao",movimentacao);
			view.addObject("produtos",produtoDao.findAll());
			view.addObject("estoques",estoqueDao.findAll());
			view.addObject("tipoMovimentacao", TipoMovimentacao.values());
		}else {
			
			regrasMov.realizaMovimentacao(movimentacao, produtoEstoque);
			
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
