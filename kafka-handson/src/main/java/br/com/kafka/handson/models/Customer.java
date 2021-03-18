package br.com.kafka.handson.models;

public class Customer extends BaseCustomer {

	public Customer() {

	}

	public Customer(String nome, String identificacao) {
		super(nome, identificacao);
	}

	@Override
	public String toString() {
		return "Customer [nome=" + nome + ", identificacao=" + identificacao + "]";
	}

}
