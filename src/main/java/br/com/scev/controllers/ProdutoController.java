package br.com.scev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.scev.models.Produto;
import br.com.scev.repos.EstoqueDao;

@Controller
@RequestMapping("produto")
public class ProdutoController {
	
	@Autowired
	private EstoqueDao estoqueDao;

	@GetMapping("form")
	public ModelAndView produtoForm() {
		
		ModelAndView view = new ModelAndView("produtoForm");
		
		view.addObject("produto", new Produto());
		view.addObject("estoques",estoqueDao.findAll());
		
		return view;
		
	}

}
