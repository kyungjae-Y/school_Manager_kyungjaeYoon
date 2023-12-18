package dao;

import vo.User;
import java.util.ArrayList;
import Utils.InputManager;

public class UserDAO {
	private ArrayList<User> uList;

	public UserDAO() {
		uList = new ArrayList<User>();
	}

//	가입
	public void joinUser() {
		String id = InputManager.getStr("ID : ");
		int idIdx = idValue(id);
		if (idIdx != -1) {
			System.out.println("중복 ID가 존재합니다");
			return;
		}
		String pw = InputManager.getStr("pw : ");
		String name = InputManager.getStr("name : ");
		uList.add(new User(id, pw, name, 1000 + ""));
		System.out.println(uList.get(uList.size() - 1));
		System.out.println("가입 완료");
	}

//	탈퇴 - 사용자용
	public boolean quitUser(String id, CartDAO cDAO) {
		int idIdx = idValue(id);
		String pw = InputManager.getStr("pw : ");
		int pwIdx = pwValue(pw);
		if (idIdx != pwIdx) {
			System.out.println("비밀번호가 틀렸습니다");
			return false;
		}
		uList.remove(idIdx);
		cDAO.quitUserCart(id);
		System.out.println("탈퇴 완료");
		return true;
	}

//	로그인
	public String loginUser() {
		String id = InputManager.getStr("ID : ");
		int idIdx = idValue(id);
		String pw = InputManager.getStr("pw : ");
		int pwIdx = pwValue(pw);
		if (idIdx != -1 && idIdx == pwIdx) {
			return id;
		}
		return "";
	}

//	id || pw 중복
	private int idValue(String id) {
		for (int i = 0; i < uList.size(); i++) {
			if (id.equals(uList.get(i).getId())) {
				return i;
			}
		}
		return -1;
	}

	private int pwValue(String pw) {
		for (int i = 0; i < uList.size(); i++) {
			if (pw.equals(uList.get(i).getPw())) {
				return i;
			}
		}
		return -1;
	}

//	유저 목록
	private void userList() {
		if (uList.isEmpty()) {
			System.out.println("유저가 없습니다");
			return;
		}
		for (int i = 0; i < uList.size(); i++) {
			System.out.println(uList.get(i));
		}
	}

//	유저 삭제
	private void deleteUser(CartDAO cDAO) {
		String id = InputManager.getStr("ID : ");
		int idIdx = idValue(id);
		if (idIdx == -1) {
			System.out.println("ID가 존재하지 않습니다");
			return;
		}
		uList.remove(idIdx);
		cDAO.quitUserCart(id);
		System.out.println("삭제 완료");
	}

//	유저관리 - 관리자 모드 : 삭제 혹은 목록 보기
	public void userManager(CartDAO cDAO) {
		if (uList.isEmpty()) {
			System.out.println("유저가 없습니다");
			return;
		}
		while (true) {
			System.out.println("[1] 유저 삭제");
			System.out.println("[2] 유저 목록");
			System.out.println("[0] 뒤로가기");
			int sel = InputManager.getInt("선택>>");
			sel = InputManager.selValue(sel, 0, 4);
			if (sel == 1) {
				deleteUser(cDAO);
			} else if (sel == 2) {
				userList();
			} else if (sel == 0) {
				System.out.println("관리자 목록으로 돌아 갑니다");
				break;
			} else {
				System.out.println("숫자 범위 선택 오류");
			}
		}
	}

//	저장할 파일 만들기
	public String datatoFile() {
		String data = "";
		for (int i = 0; i < uList.size(); i++) {
			data += uList.get(i).saveData() + "\n";
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
			uList.add(new User(info[0], info[1], info[2], info[3]));
		}
	}
}