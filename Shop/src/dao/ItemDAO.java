package dao;

import java.util.ArrayList;
import Utils.InputManager;
import vo.Item;

public class ItemDAO {
	private ArrayList<Item> iList;
	private ArrayList<String> categori;

	public ItemDAO() {
		iList = new ArrayList<Item>();
		categori = new ArrayList<String>();
	}

	public ArrayList<Item> getiList() {
		return iList;
	}

	public ArrayList<String> getCategori() {
		return categori;
	}

//	아이템 관리
	public void itemManager(CartDAO cDAO) {
		if (categori.isEmpty()) {
			System.out.println("카테고리를 등록해 주세요");
			return;
		}
		while (true) {
			System.out.println("[1]아이템 추가");
			System.out.println("[2]아이템 수정");
			System.out.println("[3]아이템 삭제");
			System.out.println("[4]아이템 목록");
			System.out.println("[0]뒤로가기");
			int sel = InputManager.getInt("선택>>");
			sel = InputManager.selValue(sel, 0, 4);
			if (sel == 1) {
				addItem();
			} else if (sel == 2) {
				editItem(cDAO);
			} else if (sel == 3) {
				deleteItem(cDAO);
			} else if (sel == 4) {
				listItem();
			} else if (sel == 0) {
				System.out.println("관리자 목록으로 돌아 갑니다");
				break;
			} else {
				System.out.println("숫자 범위 선택 오류");
			}
		}
	}

//	아이템 추가
	private void addItem() {
		listCategori();
		int sel = InputManager.getInt("선택>>");
		sel = InputManager.selValue(sel, 1, categori.size()) - 1;
		if (sel == -2) {
			System.out.println("카테고리가 존재하지 않습니다");
			return;
		}
		String name = InputManager.getStr("아이템 이름 : ");
		int itemIdx = categoriItemValue(categori.get(sel), name);
		if (itemIdx != -1) {
			System.out.println("아이템이 존재합니다");
			return;
		}
		int price = InputManager.getInt("가격 : ");
		price = InputManager.selValue(price, 1, 100000);
		if (price == -1) {
			System.out.println("금액 오류 메뉴로 돌아갑니다");
			return;
		}
		iList.add(new Item(name, price + "", categori.get(sel)));
		System.out.println("추가 완료");
	}

//	아이템 수정
	private void editItem(CartDAO cDAO) {
		listCategori();
		int sel = InputManager.getInt("선택>>");
		sel = InputManager.selValue(sel, 1, categori.size()) - 1;
		if (sel == -1) {
			System.out.println("카테고리가 존재하지 않습니다");
			return;
		}
		String name = InputManager.getStr("아이템 이름 : ");
		int itemIdx = categoriItemValue(categori.get(sel), name);
		if (itemIdx == -1) {
			System.out.println("아이템이 존재 하지 않습니다");
			return;
		}
		String nextName = InputManager.getStr("새 이름 : ");
		int editItemIdx = categoriItemValue(categori.get(sel), nextName);
		if (editItemIdx != -1) {
			System.out.println("같은 이름이 존재합니다");
			return;
		}
		int price = InputManager.getInt("가격 : ");
		price = InputManager.selValue(price, 1, 100000);
		if (price == -1) {
			System.out.println("금액 오류 메뉴로 돌아갑니다");
			return;
		}
		iList.set(itemIdx, new Item(nextName, price + "", categori.get(sel)));
		cDAO.editItem(categori.get(sel), name, nextName);
		System.out.println("수정 완료");
	}

//	아이템 삭제
	private void deleteItem(CartDAO cDAO) {
		listCategori();
		int sel = InputManager.getInt("선택>>");
		sel = InputManager.selValue(sel, 1, categori.size()) - 1;
		if (sel == -1) {
			System.out.println("카테고리가 존재하지 않습니다");
			return;
		}
		String name = InputManager.getStr("아이템 이름 : ");
		int itemIdx = categoriItemValue(categori.get(sel), name);
		if (itemIdx == -1) {
			System.out.println("아이템이 존재 하지 않습니다");
			return;
		}
		iList.remove(itemIdx);
		cDAO.deleteItem(name); // 카트에 있는 아이템도 삭제
		System.out.println("삭제 완료");
	}

//	아이템 목록
	private void listItem() {
		for (int i = 0; i < categori.size(); i++) {
			System.out.println(categori.get(i));
			System.out.print("[");
			for (int k = 0; k < iList.size(); k++) {
				if (iList.get(k).getCategory().equals(categori.get(i))) {
					System.out.print(iList.get(k).getName() + " ");
				}
			}
			System.out.println("]");
		}
	}

//	쇼핑에서 카테고리를 고르면 해당하는 아이템 목록 띄워주는 메서드
	public int categoryToItem(int categoryIdx) {
		ArrayList<Integer> itemIdx = new ArrayList<Integer>();
		int idx = 1;
		for (int k = 0; k < iList.size(); k++) {
			if (iList.get(k).getCategory().equals(categori.get(categoryIdx))) {
				System.out.println("[" + (idx++) + "]" + iList.get(k).getName());
				itemIdx.add(k);
			}
		}
		int sel = InputManager.getInt("선택>>");
		sel = InputManager.selValue(sel, 1, categori.size()) - 1;
		if (sel == -2) {
			return -1;
		}
		return itemIdx.get(sel);
	}

//	카테고리 내 아이템 중복 확인
	private int categoriItemValue(String categori, String name) {
		for (int i = 0; i < iList.size(); i++) {
			if (iList.get(i).getCategory().equals(categori) && iList.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

//	카테고리 관리
	public void categoriManager(CartDAO cDAO) {
		while (true) {
			System.out.println("[1]카테고리 추가");
			System.out.println("[2]카티고리 수정");
			System.out.println("[3]카티고리 삭제");
			System.out.println("[4]카테고리 목록");
			System.out.println("[0]뒤로가기");
			int sel = InputManager.getInt("선택 : ");
			sel = InputManager.selValue(sel, 0, 4);
			if (sel == 1) {
				addCategori();
			} else if (sel == 2) {
				editCategory();
			} else if (sel == 3) {
				deleteCategori(cDAO);
			} else if (sel == 4) {
				listCategori();
			} else if (sel == 0) {
				System.out.println("관리자 목록으로 돌아 갑니다");
				break;
			} else {
				System.out.println("숫자 범위 선택 오류");
			}
		}
	}

//	카테고리 중복 확인
	private int categoriValue(String categori) {
		for (int i = 0; i < this.categori.size(); i++) {
			if (categori.equals(this.categori.get(i))) {
				return i;
			}
		}
		return -1;
	}

//	카테고리 추가
	private void addCategori() {
		String categori = InputManager.getStr("이름 : ");
		int categoriIdx = categoriValue(categori);
		if (categoriIdx != -1) {
			System.out.println("중복 카테고리가 있습니다");
			return;
		}
		this.categori.add(categori);
		System.out.println("카테고리 추가 완료");
	}

//	카테고리 수정
	private void editCategory() {
		if (categori.isEmpty()) {
			System.out.println("카테고리를 등록해 주세요");
			return;
		}
		String categori = InputManager.getStr("변경 전 이름 : ");
		int categoriIdx = categoriValue(categori);
		if (categoriIdx == -1) {
			System.out.println("카테고리가 존재하지 않습니다");
			return;
		}
		String nextCategori = InputManager.getStr("변경 후 이름 : ");
		this.categori.set(categoriIdx, nextCategori);
//		아이템 리스트에서 카테고리 이름 변경
		for (int i = 0; i < iList.size(); i++) {
			if (categori.equals(iList.get(i).getCategory())) {
				iList.set(i, new Item(iList.get(i).getName(), iList.get(i).getPrice() + "", nextCategori));
			}
		}
		System.out.println("수정 완료");
	}

//	카테고리 삭제
	private void deleteCategori(CartDAO cDAO) {
		if (categori.isEmpty()) {
			System.out.println("카테고리를 등록해 주세요");
			return;
		}
		String categori = InputManager.getStr("이름 : ");
		int categoriIdx = categoriValue(categori);
		if (categoriIdx == -1) {
			System.out.println("카테고리가 존재하지 않습니다");
			return;
		}
		this.categori.remove(categoriIdx);
		deleteCategoryItemList(categori);
		cDAO.deleteCategory(categori);
		System.out.println("카테고리 삭제 완료");
	}

//	아이템 리스트에 있는 카테고리와 일치하는 목록 전부 삭제
	private void deleteCategoryItemList(String categori) {
		for (int i = 0; i < iList.size(); i++) {
			if (categori.equals(iList.get(i).getCategory())) {
				iList.remove(i);
				i--;
			}
		}
	}

//	카테고리 목록
	public void listCategori() {
		if (categori.isEmpty()) {
			System.out.println("카테고리를 등록해 주세요");
			return;
		}
		for (int i = 0; i < categori.size(); i++) {
			System.out.println("[" + (i + 1) + "] " + categori.get(i));
		}

	}

//	저장할 파일 만들기
	public String datatoFile() {
		String data = "";
		for (int i = 0; i < iList.size(); i++) {
			data += iList.get(i).saveData() + "\n";
		}
		if (data.length() != 0) {
			data = data.substring(0, data.length() - 1);
		}
		return data;
	}

//	불러온 텍스트 파일 데이터로 만들기
	public void fileToData(String data) {
		if (data.length() == 0)
			return;
		String[] datas = data.split("\n");
		for (int i = 0; i < datas.length; i++) {
			String[] info = datas[i].split("/");
			iList.add(new Item(info[0], info[1], info[2]));
//			여기서 카테고리 추가
			addDataCategori(info[2]);
		}
	}

	private void addDataCategori(String categori) {
		for (int i = 0; i < this.categori.size(); i++) {
			if (this.categori.get(i).equals(categori)) {
				return;
			}
		}
		this.categori.add(categori);
	}
}