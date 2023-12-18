package vo;

public class Cart extends shop {
	String itemName; // 구입한 아이템
	String category;

	public Cart(String id, String itemName, String category) {
		super(id);
		this.itemName = itemName;
		this.category = category;
	}

	public String getId() {
		return super.getId();
	}

	public String getItemName() {
		return itemName;
	}

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return super.toString() + " " + itemName + " " + category;
	}

	public String saveData() {
		return super.getId() + "/" + itemName + "/" + category;
	}
}