package br.com.scev.controllers;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.scev.models.Estoque;
import br.com.scev.models.TipoEstoque;
import br.com.scev.repos.EstoqueDao;
import br.com.scev.validators.EstoqueValidator;

@Controller
@RequestMapping("estoque")
public class EstoqueController {
	
	@Autowired
	EstoqueDao estoqueDao;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
		binder.addValidators(new EstoqueValidator());
	}

	@GetMapping("form")
	public ModelAndView estoqueForm(Estoque estoque) {
		
		ModelAndView view = new ModelAndView("estoqueForm");
		
		view.addObject("tiposEstoque", TipoEstoque.values());
		
		return view;
		
	}
	
	@PostMapping("cadastra")
	public ModelAndView cadastraEstoque(@Valid Estoque estoque, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			return estoqueForm(estoque);
		}
		
		estoqueDao.save(estoque);
		
		ModelAndView view = new ModelAndView("redirect:form");
		
		redirectAttributes.addFlashAttribute("mensagem", "Estoque cadastrado com sucesso");
		
		return view;
		
	}
	
	@GetMapping("lista")
	public ModelAndView listaEstoques() {
		
		ModelAndView view = new ModelAndView("listaEstoques");
		
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}
	
	@PostMapping("editar")
	public ModelAndView editarEstoque(Integer idEstoque) {
		
		ModelAndView view = new ModelAndView("estoqueForm");
		
		view.addObject("estoque", estoqueDao.findOne(idEstoque));
		view.addObject("tiposEstoque", TipoEstoque.values());
		
		return view;
		
	}
	
	@PostMapping("remover")
	public ModelAndView removerEstoque(Integer idEstoque, RedirectAttributes redirectAttributes) {
		
		ModelAndView view = new ModelAndView("redirect:lista");
		
		estoqueDao.delete(idEstoque);
		
		redirectAttributes.addFlashAttribute("mensagem", "Estoque Removido com sucesso");
		
		return view;
		
	}
	
	@GetMapping("produtos/{idEstoque}")
	public ModelAndView listaProdutosEstoque(@PathVariable Integer idEstoque) {
		
		ModelAndView view = new ModelAndView("produtosEstoque");
		
		view.addObject("estoque", estoqueDao.findOne(idEstoque));
		
		return view;
		
	}
	
	@GetMapping("movimentacoes/{idEstoque}")
	public ModelAndView listaMovimentacoesEstoque(@PathVariable Integer idEstoque) {
		
		ModelAndView view = new ModelAndView("movimentacoesEstoque");
		
		Estoque estoque = estoqueDao.findOne(idEstoque);
		
		Collections.sort(estoque.getMovimentacoes(),(p1,p2) -> p2.getIdMovimentacao().compareTo(p1.getIdMovimentacao()));
		
		view.addObject("estoque", estoque);
		
		return view;
		
	}
	
}
