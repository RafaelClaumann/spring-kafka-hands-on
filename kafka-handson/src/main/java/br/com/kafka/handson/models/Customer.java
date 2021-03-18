package br.com.kafka.handson.models;

public class Customer {

	private String nome;
	private String identificacao;

	public Customer() {

	}

	public Customer(String nome, String identificacao) {

		this.nome = nome;
		this.identificacao = identificacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

}
