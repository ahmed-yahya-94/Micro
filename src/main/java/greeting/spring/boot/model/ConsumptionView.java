package greeting.spring.boot.model;

public class ConsumptionView {
	
	private String number;
	
	private String name;
	
	private String age;
	
	private String martial_status;

	private String passport_request;
	
	private String penalties_balance;
	

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMartial_status() {
		return martial_status;
	}

	public void setMartial_status(String martial_status) {
		this.martial_status = martial_status;
	}

	public String getPassport_request() {
		return passport_request;
	}

	public void setPassport_request(String passport_request) {
		this.passport_request = passport_request;
	}

	public String getPenalties_balance() {
		return penalties_balance;
	}

	public void setPenalties_balance(String penalties_balance) {
		this.penalties_balance = penalties_balance;
	}

}
