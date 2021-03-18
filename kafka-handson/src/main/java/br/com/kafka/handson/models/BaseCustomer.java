package br.com.kafka.handson.models;

public abstract class BaseCustomer {

	protected String nome;
	protected String identificacao;

	public BaseCustomer() {

	}

	public BaseCustomer(String nome, String identificacao) {
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

	@Override
	public String toString() {
		return "Customer [nome=" + nome + ", identificacao=" + identificacao + "]";
	}

}
