package vo;

public class User extends shop {
	String pw;
	String name;
	int money;

	public User(String id, String pw, String name, String money) {
		super(id);
		this.pw = pw;
		this.name = name;
		this.money = Integer.parseInt(money);
	}

	public String getId() {
		return super.getId();
	}

	public String getPw() {
		return pw;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return super.toString() + " " + pw + " " + name;
	}

	public String saveData() {
		return super.getId() + "/" + pw + "/" + name + "/" + money;
	}
}