package br.edu.opet.biblioteca.dto;

import java.util.List;

public abstract class AbstractDTO<T> {
	private boolean ok;
	private String mensagem;
	private T objeto;
	private List<T> lista;

	public AbstractDTO(boolean ok, String mensagem) {
		super();
		this.ok = ok;
		this.mensagem = mensagem;
	}

	public AbstractDTO(boolean ok, String mensagem, T objeto) {
		super();
		this.ok = ok;
		this.mensagem = mensagem;
		this.objeto = objeto;
	}

	public AbstractDTO(boolean ok, String mensagem, List<T> lista) {
		super();
		this.ok = ok;
		this.mensagem = mensagem;
		this.lista = lista;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public T getObjeto() {
		return objeto;
	}

	public void setObjeto(T objeto) {
		this.objeto = objeto;
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

}
