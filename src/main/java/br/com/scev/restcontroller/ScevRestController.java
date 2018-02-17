package br.com.scev.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.scev.models.Estoque;
import br.com.scev.models.Movimentacao;
import br.com.scev.models.Produto;
import br.com.scev.models.ProdutoEstoque;
import br.com.scev.models.TransferenciaEstoque;
import br.com.scev.negocio.RegrasMovimentacao;
import br.com.scev.repos.EstoqueDao;
import br.com.scev.repos.ProdutoDao;
import br.com.scev.repos.ProdutoEstoqueDao;
import br.com.scev.validators.EstoqueValidator;
import br.com.scev.validators.MovimentacaoValidator;
import br.com.scev.validators.ProdutoValidator;
import br.com.scev.validators.TransferenciaValidator;

@RestController
@CrossOrigin
public class ScevRestController {
	
	@Autowired
	EstoqueDao estoqueDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	private ProdutoEstoqueDao produtoEstoqueDao;
	
	@Autowired
	private RegrasMovimentacao regrasMov;
	
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
	@InitBinder("movimentacao")
	public void InitBinderMovimentacao(WebDataBinder binder){
		binder.addValidators(
			new MovimentacaoValidator()
		);
	}
	@InitBinder("tranferencia")
	public void InitBinderTransferencia(WebDataBinder binder){
		binder.addValidators(
			new TransferenciaValidator()
		);
	}
	
	@PostMapping("cadastraTransferencia")
	public void tranfereEstoque(@Valid @RequestBody TransferenciaEstoque transferencia, BindingResult result) {
		
		if(result.hasErrors()) {
			System.out.println("erro");
		}
		
		List<Movimentacao> movimentaçoes = transferencia.getMovimentaçoes();
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(transferencia.getOrigem().getIdEstoque(), transferencia.getProduto().getIdProduto());
		
		String erro = regrasMov.verificaEstoqueMovimentacao(movimentaçoes.get(0), produtoEstoque);
		
		if(!erro.isEmpty() || result.hasErrors()) {
			System.out.println("erro");
		}else {
			
			transferencia.getMovimentaçoes().stream().forEach(m -> {
				ProdutoEstoque produtoEstoqueMovimentacao = 
						produtoEstoqueDao.findByEstoqueProduto(m.getEstoque().getIdEstoque(), m.getProduto().getIdProduto());
				regrasMov.realizaMovimentacao(m, produtoEstoqueMovimentacao);
			});
			
		}
		
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
	
	@PostMapping("cadastraMovimentacao")
	public void cadastraMovimentacao(@Valid @RequestBody Movimentacao movimentacao,BindingResult result) {
		
		if(result.hasErrors()) {
			System.out.println("erros");
		}
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(movimentacao.getEstoque().getIdEstoque(), movimentacao.getProduto().getIdProduto());
				
		String erro = regrasMov.verificaEstoqueMovimentacao(movimentacao, produtoEstoque);
			
		if(!erro.isEmpty()) {
			System.out.println(erro);
		}else {
			
			regrasMov.realizaMovimentacao(movimentacao, produtoEstoque);
		}
		
	}

}
