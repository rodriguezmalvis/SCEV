package br.com.scev.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Component
public class Response {
		
	private List<String> messages = new ArrayList<String>();
	private TipoResposta tipo;
	private String header;
	
	public Response(List<String> messages, TipoResposta tipo, String header) {
		super();
		this.messages = messages;
		this.tipo = tipo;
		this.header = header;
	}
	
	public Response(String message, TipoResposta tipo, String header) {
		super();
		this.messages.add(message);
		this.tipo = tipo;
		this.header = header;
	}
	
	public Response(BindingResult result, MessageSource messageSource, TipoResposta tipo, String header) {
		super();
		for (ObjectError erro : result.getAllErrors()) {
			for (String codigo : erro.getCodes()) {
				String message = messageSource.getMessage(codigo, null, "DefaultTitle", new Locale("pt-br"));
				if(!message.contains("Default")) {
					this.getMessages().add(message);
				}
			}
		}
		this.tipo = tipo;
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Response() {
		this.setMessages(new ArrayList<>());
	}
	
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public TipoResposta getTipo() {
		return tipo;
	}
	public void setTipo(TipoResposta tipo) {
		this.tipo = tipo;
	}
	
}
