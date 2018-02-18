package br.com.scev.controllers;

import java.util.ArrayList;
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

import br.com.scev.models.Movimentacao;
import br.com.scev.models.ProdutoEstoque;
import br.com.scev.models.TipoMovimentacao;
import br.com.scev.models.TransferenciaEstoque;
import br.com.scev.negocio.RegrasMovimentacao;
import br.com.scev.repos.EstoqueDao;
import br.com.scev.repos.ProdutoDao;
import br.com.scev.repos.ProdutoEstoqueDao;
import br.com.scev.validators.TransferenciaValidator;

@Controller
@RequestMapping("transferencia")
@CrossOrigin
public class TransferenciaController {
	
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
		binder.addValidators(new TransferenciaValidator());
	}
	
	@GetMapping("outrosEstoques/{idEstoque}")
	public ModelAndView getOutrosEstoques(@PathVariable Integer idEstoque){
		
		ModelAndView view = new ModelAndView();
		
		view.addObject("estoques",  estoqueDao.getOutrosEstoques(idEstoque));
		
		return view;
	}

	@GetMapping("form")
	public ModelAndView tranferenciaForm(TransferenciaEstoque transferenciaEstoque) {
		
		ModelAndView view = new ModelAndView("transferenciaForm");
		
		view.addObject("tipoMovimentacao", TipoMovimentacao.Transferência);
		view.addObject("produtos",produtoDao.findAll());
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}
	
	@GetMapping("tipos")
	public ModelAndView getTipos() {
		ModelAndView view = new ModelAndView();
		
		List<TipoMovimentacao> movimentacoes = new ArrayList<>();
		movimentacoes.add(TipoMovimentacao.Consignação);
		movimentacoes.add(TipoMovimentacao.Transferência);
		
		view.addObject("tipoMovimentacoes", movimentacoes);
		
		return view;
	}
	

	@PostMapping("transfere")
	public ModelAndView tranfereEstoque(@Valid TransferenciaEstoque transferencia, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			return tranferenciaForm(transferencia);
		}
		
		ModelAndView view = new ModelAndView("redirect:form");
		
		List<Movimentacao> movimentaçoes = transferencia.getMovimentaçoes();
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(transferencia.getOrigem().getIdEstoque(), transferencia.getProduto().getIdProduto());
		
		String erro = regrasMov.verificaEstoqueMovimentacao(movimentaçoes.get(0), produtoEstoque);
		
		if(!erro.isEmpty() || result.hasErrors()) {
			return tranferenciaForm(transferencia).addObject("alerta", erro);
		}else {
			
			transferencia.getMovimentaçoes().stream().forEach(m -> {
				ProdutoEstoque produtoEstoqueMovimentacao = 
						produtoEstoqueDao.findByEstoqueProduto(m.getEstoque().getIdEstoque(), m.getProduto().getIdProduto());
				regrasMov.realizaMovimentacao(m, produtoEstoqueMovimentacao);
			});
			
			redirectAttributes.addFlashAttribute("mensagem", "Transferencia cadastrada com sucesso");
		}
		
		return view;
		
	}
	
}
