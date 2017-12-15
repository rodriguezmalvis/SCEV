package br.com.scev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.scev.models.Estoque;
import br.com.scev.models.TipoEstoque;
import br.com.scev.repos.EstoqueDao;

@Controller
@RequestMapping("estoque")
public class EstoqueController {
	
	@Autowired
	EstoqueDao estoqueDao;

	@GetMapping("form")
	public ModelAndView estoqueForm() {
		
		ModelAndView view = new ModelAndView("estoqueForm");
		
		view.addObject("estoque", new Estoque());
		view.addObject("tiposEstoque", TipoEstoque.values());
		
		
		return view;
		
	}
	
	@PostMapping("cadastra")
	public ModelAndView cadastraEstoque(Estoque estoque, RedirectAttributes redirectAttributes) {
		
		estoqueDao.save(estoque);
		
		ModelAndView view = new ModelAndView("redirect:form");
		
		redirectAttributes.addFlashAttribute("mensagem", "Estoque cadastrado com sucesso");
		
		return view;
		
	}
	
}
