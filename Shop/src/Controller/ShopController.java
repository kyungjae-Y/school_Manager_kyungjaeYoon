package Controller;

import Utils.InputManager;
import Utils.FileManager;
import dao.UserDAO;
import dao.ItemDAO;
import dao.CartDAO;

public class ShopController {
	private UserDAO uDAO;
	private ItemDAO iDAO;
	private CartDAO cDAO;
	private FileManager fm;

	public UserDAO getuDAO() {
		return uDAO;
	}

	public ItemDAO getiDAO() {
		return iDAO;
	}

	public CartDAO getcDAO() {
		return cDAO;
	}

	public ShopController() {
		uDAO = new UserDAO();
		iDAO = new ItemDAO();
		cDAO = new CartDAO();
		fm = new FileManager();
		getuDAO().fileToData(fm.fileLoad("user.txt"));
		getiDAO().fileToData(fm.fileLoad("item.txt"));
		getcDAO().fileToData(fm.fileLoad("cart.txt"));
	}

	public void run() {
		selectMenu();
		InputManager.scanClose();
//		종료 시 자동 저장
		fm.fileSave(getuDAO(), getiDAO(), getcDAO());
	}

//	숫자 범위
	private int selValue(int sel, int start, int end) {
		if (sel < start || sel > end) {
			return -1;
		}
		return sel;
	}

//	사용자 관리자 선택 메뉴
	private void selectMenu() {
		while (true) {
			System.out.println("[1] 사용자");
			System.out.println("[2] 관리자");
			System.out.println("[0] 종료");
			int sel = InputManager.getInt("선택 >>");
			sel = selValue(sel, 0, 2);
			if (sel == 1) {
				userLogoutSelect();
			} else if (sel == 2) {
				adminSelect();
			} else if (sel == 0) {
				System.out.println("종료");
				break;
			} else {
				System.out.println("숫자 범위 선택 오류");
			}
		}
	}

//	사용자 선택 메뉴 (로그아웃 상태)
	private void userLogoutSelect() {
		while (true) {
			System.out.println("[1] 가입");
			System.out.println("[2] 로그인");
			System.out.println("[0] 뒤로가기");
			int sel = InputManager.getInt("선택 >>");
			sel = selValue(sel, 0, 2);
			if (sel == 1) {
				uDAO.joinUser();
			} else if (sel == 2) {
				String id = uDAO.loginUser();
				if (id.equals("")) {
					System.out.println("로그인 실패");
					continue;
				}
				userLoginSelect(id);
			} else if (sel == 0) {
				System.out.println("사용자&관리자 선택 메뉴로 돌아갑니다");
				break;
			} else {
				System.out.println("숫자 범위 선택 오류");
			}
		}
	}

//	사용자 선택 메뉴 (로그인 상태)
	private void userLoginSelect(String id) {
		while (true) {
			System.out.println(id + "님 로그인 중");
			System.out.println("[1] 쇼핑");
			System.out.println("[2] 장바구니 목록 확인");
			System.out.println("[3] 장바구니 목록 삭제");
			System.out.println("[4] 회원탈퇴");
			System.out.println("[0] 로그아웃");
			int sel = InputManager.getInt("선택 >>");
			sel = selValue(sel, 0, 4);
			if (sel == 1) {
				cDAO.shopping(getiDAO(), id);
			} else if (sel == 2) {
				cDAO.myCartList(id);
			} else if (sel == 3) {
				cDAO.deleteCart(id);
			} else if (sel == 4) {
				boolean ck = uDAO.quitUser(id, getcDAO());
				if (ck)
					break;
			} else if (sel == 0) {
				System.out.println(id + "님 로그아웃");
				break;
			} else {
				System.out.println("숫자 범위 선택 오류");
			}
		}
	}

//	관리자 선택 메뉴
	private void adminSelect() {
		while (true) {
			System.out.println("[1] 아이템관리");
			System.out.println("[2] 카테고리관리");
			System.out.println("[3] 장바구니관리");
			System.out.println("[4] 유저관리");
			System.out.println("[5] 파일저장");
			System.out.println("[6] 파일로드");
			System.out.println("[0] 뒤로가기");
			int sel = InputManager.getInt("선택 >>");
			sel = selValue(sel, 0, 6);
			if (sel == 1) {
				iDAO.itemManager(getcDAO());
			} else if (sel == 2) {
				iDAO.categoriManager(getcDAO());
			} else if (sel == 3) {
				cDAO.cartManager();
			} else if (sel == 4) {
				uDAO.userManager(getcDAO());
			} else if (sel == 5) {
				fm.fileSave(getuDAO(), getiDAO(), getcDAO());
			} else if (sel == 6) {
				getuDAO().fileToData(fm.fileLoad("user.txt"));
				getiDAO().fileToData(fm.fileLoad("item.txt"));
				getcDAO().fileToData(fm.fileLoad("cart.txt"));
			} else if (sel == 0) {
				System.out.println("사용자&관리자 선택 메뉴로 돌아갑니다");
				break;
			} else {
				System.out.println("숫자 범위 선택 오류");
				return;
			}
		}
	}
}