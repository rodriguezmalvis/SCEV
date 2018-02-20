package br.com.scev.restcontroller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.scev.models.Estoque;
import br.com.scev.models.Movimentacao;
import br.com.scev.models.Produto;
import br.com.scev.models.ProdutoEstoque;
import br.com.scev.models.Response;
import br.com.scev.models.TipoResposta;
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
	private MessageSource messageSource;
	
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
	public Response tranfereEstoque(@Valid @RequestBody TransferenciaEstoque transferencia, BindingResult result) {
		
		if(result.hasErrors()) {
			return new Response(result, messageSource, TipoResposta.danger, "Alerta");
		}
		
		List<Movimentacao> movimentaçoes = transferencia.getMovimentaçoes();
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(transferencia.getOrigem().getIdEstoque(), transferencia.getProduto().getIdProduto());
		
		String erro = regrasMov.verificaEstoqueMovimentacao(movimentaçoes.get(0), produtoEstoque);
		
		if(!erro.isEmpty()) {
			return new Response(erro, TipoResposta.danger, "Alerta");
		}else {
			
			transferencia.getMovimentaçoes().stream().forEach(m -> {
				ProdutoEstoque produtoEstoqueMovimentacao = 
						produtoEstoqueDao.findByEstoqueProduto(m.getEstoque().getIdEstoque(), m.getProduto().getIdProduto());
				regrasMov.realizaMovimentacao(m, produtoEstoqueMovimentacao);
			});
			return new Response("Transferencia realizada com sucesso", TipoResposta.success, "Sucesso");
		}
		
	}
	
	@PostMapping("cadastraEstoque")
	public Response cadastraEstoque(@Valid @RequestBody Estoque estoque, BindingResult result) {
		
		Response response = null;
		
		if(result.hasErrors()) {
			response = new Response(result, messageSource, TipoResposta.warning, "Alerta");
			return response;
		}
		
		estoqueDao.save(estoque);
		response = new Response("Estoque "+estoque.getNome()+" cadastrado com sucesso", TipoResposta.success, "Sucesso");
			
		return response;
		
	}
	
	@PostMapping("cadastraProduto")
	public Response cadastraProduto(@Valid @RequestBody Produto produto, BindingResult result) {
		
		if(result.hasErrors()) {
			return new Response(result, messageSource, TipoResposta.danger, "Alerta");
		}else {
			produtoDao.save(produto);
			return new Response("Produto "+produto.getTitulo()+" cadastrado com sucesso", TipoResposta.success, "Sucesso");
		}
		
	}
	
	@PostMapping("cadastraMovimentacao")
	public Response cadastraMovimentacao(@Valid @RequestBody Movimentacao movimentacao,BindingResult result) {
		
		if(result.hasErrors()) {
			return new Response(result, messageSource, TipoResposta.danger, "Alerta");
		}
		
		ProdutoEstoque produtoEstoque = 
				produtoEstoqueDao.findByEstoqueProduto(movimentacao.getEstoque().getIdEstoque(), movimentacao.getProduto().getIdProduto());
				
		String erro = regrasMov.verificaEstoqueMovimentacao(movimentacao, produtoEstoque);
			
		if(!erro.isEmpty()) {
			return new Response(erro, TipoResposta.danger, "Alerta");
		}else {
			
			regrasMov.realizaMovimentacao(movimentacao, produtoEstoque);
			return new Response("Movimentação relizada com sucesso", TipoResposta.success, "Sucesso");
		}
		
	}

}
