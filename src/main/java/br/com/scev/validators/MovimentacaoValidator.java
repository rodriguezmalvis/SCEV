package br.com.scev.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.scev.models.Movimentacao;

public class MovimentacaoValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Movimentacao.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors erros) {

		ValidationUtils.rejectIfEmpty(erros, "quantidade", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "descricao", "field.required");
		
	}

}
