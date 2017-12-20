package br.com.scev.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.scev.models.Estoque;

public class EstoqueValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Estoque.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors erros) {
		
		ValidationUtils.rejectIfEmpty(erros, "nome", "field.required.estoque.nome");
		ValidationUtils.rejectIfEmpty(erros, "descricao", "field.required.estoque.descricao");
		
	}

}
