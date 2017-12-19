package br.com.scev.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.scev.models.Produto;

public class ProdutoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors erros) {

		ValidationUtils.rejectIfEmpty(erros, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "autor", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "paginas", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "formato", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "ISBN", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "preco", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "contato", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "sinopse", "field.required");
		
		Produto produto = (Produto) target;
		
	}

}
