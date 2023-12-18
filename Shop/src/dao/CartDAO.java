package dao;

import java.util.ArrayList;
import Utils.InputManager;
import vo.Cart;

public class CartDAO {
	ArrayList<Cart> cList;

	public CartDAO() {
		cList = new ArrayList<Cart>();
	}

//	장바구니 관리 - 관리자 : 목록만 띄워주기
	public void cartManager() {
		if (cList.isEmpty()) {
			System.out.println("장바구니가 비었습니다");
			return;
		}
		for (int i = 0; i < cList.size(); i++) {
			System.out.println(cList.get(i));
		}
	}

//	쇼핑 - 사용자
	public void shopping(ItemDAO iDAO, String id) {
//		카테고리 먼저 띄워주기
		iDAO.listCategori();
		int categoriIdx = InputManager.getInt("카테고리 선택 : ");
		categoriIdx = InputManager.selValue(categoriIdx, 1, iDAO.getCategori().size()) - 1;
		if (categoriIdx == -2) {
			System.out.println("선택 오류");
			return;
		}
		int itemIdx = iDAO.categoryToItem(categoriIdx);
		if (itemIdx == -1) {
			System.out.println("선택 오류");
			return;
		}
//		다 골랐으면 장바구니 추가
		cList.add(new Cart(id, iDAO.getiList().get(itemIdx).getName(), iDAO.getCategori().get(categoriIdx)));
		System.out.println("장바구니 추가 완료");
	}

//	내 카트 목록
	public void myCartList(String id) {
		for (int i = 0; i < cList.size(); i++) {
			if (cList.get(i).getId().equals(id)) {
				System.out.println(cList.get(i));
			}
		}
	}

//	아이템 이름 변경 시 카트안에 있는 아이템 이름도 변경
	public void editItem(String category, String name, String nextName) {
		for (int i = 0; i < cList.size(); i++) {
			if (cList.get(i).getCategory().equals(category)) {
				if (cList.get(i).getItemName().equals(name)) {
					cList.set(i, new Cart(cList.get(i).getId(), nextName, category));
				}
			}
		}
	}

//	장바구니 목록 1개 삭제
	public void deleteCart(String id) {
		ArrayList<Integer> itemIdx = new ArrayList<Integer>();
		int idx = 1;
		for (int i = 0; i < cList.size(); i++) {
			if (cList.get(i).getId().equals(id)) {
				System.out.println("[" + (idx++) + "]" + cList.get(i));
				itemIdx.add(i);
			}
		}
		int sel = InputManager.getInt("선택 : ");
		sel = InputManager.selValue(sel, 1, itemIdx.size()) - 1;
		if (sel == -2) {
			return;
		}
		cList.remove((int) itemIdx.get(sel));
		System.out.println("장바구니 삭제 완료");
	}

//	유저가 탈퇴 혹은 삭제시 카트 리스트 제거 메서드
	public void quitUserCart(String id) {
		for (int i = 0; i < cList.size(); i++) {
			if (id.equals(cList.get(i).getId())) {
				cList.remove(i);
				i--;
			}
		}
	}

//	관리자가 아이템 삭제시 카트 목록도 제거 메서드
	public void deleteItem(String itemName) {
		for (int i = 0; i < cList.size(); i++) {
			if (itemName.equals(cList.get(i).getItemName())) {
				cList.remove(i);
				i--;
			}
		}
	}

//	카테고리 삭제시 아이템 삭제 메서드
	public void deleteCategory(String categori) {
		for (int i = 0; i < cList.size(); i++) {
			if (categori.equals(cList.get(i).getCategory())) {
				cList.remove(i);
				i--;
			}
		}
	}

//	저장할 파일 만들기
	public String datatoFile() {
		String data = "";
		for (int i = 0; i < cList.size(); i++) {
			data += cList.get(i).saveData() + "\n";
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
			cList.add(new Cart(info[0], info[1], info[2]));
		}
	}
}