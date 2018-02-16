package br.com.scev.restcontroller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.scev.models.Estoque;
import br.com.scev.models.Produto;
import br.com.scev.repos.EstoqueDao;
import br.com.scev.repos.ProdutoDao;
import br.com.scev.validators.EstoqueValidator;
import br.com.scev.validators.ProdutoValidator;

@RestController
@CrossOrigin
public class ScevRestController {
	
	@Autowired
	EstoqueDao estoqueDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@InitBinder("estoque")
	public void InitBinderEstoque(WebDataBinder binder){
		binder.addValidators(
			new EstoqueValidator()
		);
	}
	
	@InitBinder("produto")
	public void InitBinderProduto(WebDataBinder binder){
		binder.addValidators(
			new ProdutoValidator()
		);
	}
	
	@PostMapping("cadastraEstoque")
	public void cadastraEstoque(@Valid @RequestBody Estoque estoque, BindingResult result) {
		
		if(result.hasErrors()) {
			System.out.println("erros");
		}
		
		estoqueDao.save(estoque);
		
	}
	
	@PostMapping("cadastraProduto")
	public void cadastraProduto(@Valid @RequestBody Produto produto, BindingResult result) {
		
		if(result.hasErrors()) {
			System.out.println("erros");
		}else {
			produtoDao.save(produto);
		}
		
		
	}

}
