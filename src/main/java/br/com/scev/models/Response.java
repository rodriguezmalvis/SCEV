package br.com.scev.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Response {
	
	private List<String> messages = new ArrayList<String>();
	private TipoResposta tipo;
	private String header;
	
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
