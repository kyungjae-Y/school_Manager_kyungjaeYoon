package vo;

public class shop {
	private String id;

	public shop(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + " ";
	}

}