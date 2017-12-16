package br.com.scev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.scev.models.Produto;
import br.com.scev.models.TipoProduto;
import br.com.scev.negocio.FileUploader;
import br.com.scev.repos.EstoqueDao;
import br.com.scev.repos.ProdutoDao;

@Controller
@RequestMapping("produto")
public class ProdutoController {
	
	@Autowired
	private EstoqueDao estoqueDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	FileUploader uploader;

	@GetMapping("form")
	public ModelAndView produtoForm() {
		
		ModelAndView view = new ModelAndView("produtoForm");
		
		view.addObject("produto", new Produto());
		view.addObject("estoques",estoqueDao.findAll());
		view.addObject("tipos",TipoProduto.values());
		
		return view;
		
	}
	
	@PostMapping("cadastra")
	public ModelAndView cadastraProduto(MultipartFile file, Produto produto, RedirectAttributes redirectAttributes) {
		
		ModelAndView view = new ModelAndView("redirect:form");
		
		String UrlFoto = uploader.uploadFile(file,produto.getTitulo());
		
		produto.setUrlFoto(UrlFoto);
		
		produtoDao.save(produto);
		
		redirectAttributes.addFlashAttribute("mensagem","Produto cadastrado com sucesso");
		
		return view;
		
	}
	
	@GetMapping("lista")
	public ModelAndView listaProdutos() {
		
		ModelAndView view = new ModelAndView("listaProdutos");
		
		view.addObject("produtos",produtoDao.findAll());
		
		return view;
		
	}

}
