package br.com.kafka.handson.models;

public class VipCustomer extends BaseCustomer {

	private String vipId;
	private Boolean isVip;

	public VipCustomer(String nome, String identificacao, String vipId) {
		super(nome, identificacao);
		this.vipId = vipId;
		this.isVip = true;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public Boolean getIsVip() {
		return isVip;
	}

	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}

	@Override
	public String toString() {
		return "VipCustomer [vipId=" + vipId + ", isVip=" + isVip + ", nome=" + nome + ", identificacao="
				+ identificacao + "]";
	}

}
