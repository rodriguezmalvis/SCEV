package br.com.scev.controllers;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.scev.models.Estoque;
import br.com.scev.models.Movimentacao;
import br.com.scev.models.Produto;
import br.com.scev.models.ProdutoEstoque;
import br.com.scev.models.TipoMovimentacao;
import br.com.scev.negocio.RegrasMovimentacao;
import br.com.scev.repos.EstoqueDao;
import br.com.scev.repos.MovimentacaoDao;
import br.com.scev.repos.ProdutoDao;
import br.com.scev.repos.ProdutoEstoqueDao;
import br.com.scev.validators.MovimentacaoValidator;

@Controller
@RequestMapping("movimentacao")
@CrossOrigin
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
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(new MovimentacaoValidator());
	}
	
	@GetMapping("tipos")
	public ModelAndView getTipos() {
		
		ModelAndView view = new ModelAndView();
		
		view.addObject("tipoMovimentacoes", TipoMovimentacao.values());
		
		return view;
		
	}
	
	@GetMapping("estoques")
	public ModelAndView getEstoques() {
		
		ModelAndView view = new ModelAndView();
		
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}
	
	@GetMapping("produtos/{idEstoque}")
	public ModelAndView getProdutosEstoque(@PathVariable Integer idEstoque) {
		
		ModelAndView view = new ModelAndView();
		
		Estoque estoque = estoqueDao.findOne(idEstoque);
		
		view.addObject("produtos",estoque.getProdutosEstoque());
		
		return view;
		
	}
	
	@GetMapping("produtos")
	public ModelAndView getProdutos() {
		
		ModelAndView view = new ModelAndView();
		
		view.addObject("produtos", produtoDao.findAll());
		
		return view;
		
	}

	@GetMapping("form")
	public ModelAndView movimentacaoForm(Movimentacao movimentacao) {
		
		ModelAndView view = new ModelAndView("movimentacaoForm");
		
		view.addObject("tipoMovimentacao", TipoMovimentacao.values());
		view.addObject("produtos",produtoDao.findAll());
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}
	
	@PostMapping("cadastra")
	public ModelAndView cadastraMovimentacao(@Valid Movimentacao movimentacao,BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			return movimentacaoForm(movimentacao);
		}
		
		ModelAndView view = new ModelAndView("redirect:form");
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(movimentacao.getEstoque().getIdEstoque(), movimentacao.getProduto().getIdProduto());
				
		String erro = regrasMov.verificaEstoqueMovimentacao(movimentacao, produtoEstoque);
			
		if(!erro.isEmpty()) {
			return movimentacaoForm(movimentacao).addObject("alerta", erro);
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
