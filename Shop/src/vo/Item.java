package vo;

public class Item {
	String name;
	int price;
	String category; // 카테고리 - 육류, 과자, 어류, 과일 등등

	public Item(String name, String price, String category) {
		this.name = name;
		this.price = Integer.parseInt(price);
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return category + " " + name + " " + price;
	}

	public String saveData() {
		return name + "/" + price + "/" + category;
	}
}