package Controller;

import DAO.StudentDAO;
import DAO.SubjectDAO;
import Util.FileManager;
import Util.InputManager;

/*
	무조건 파일 업로드 먼저
	
	처음부터 데이터가 연결된 상태
 */
public class Controller {
	private StudentDAO stuDAO;
	private SubjectDAO subDAO;
	private FileManager fm;

	public StudentDAO getStuDAO() {
		return stuDAO;
	}

	public SubjectDAO getSubDAO() {
		return subDAO;
	}

	public Controller() {
		stuDAO = new StudentDAO();
		subDAO = new SubjectDAO();
		fm = new FileManager();
		getStuDAO().fileToData(fm.fileLoad("student.txt"));
		getSubDAO().fileToData(fm.fileLoad("subject.txt"));
	}

	private void mainMenu() {
		System.out.println("[1] 학생 추가");
		System.out.println("[2] 학생 삭제");
		System.out.println("[3] 학생의 과목 추가");
		System.out.println("[4] 학생의 과목 삭제");
		System.out.println("[5] 전체 학생 목록");
		System.out.println("[6] 과목별 학생 목록");
		System.out.println("[7] 파일 저장");
		System.out.println("[8] 파일 로드");
		System.out.println("[0] 종료");
	}

	public void run() {
		while (true) {
			mainMenu();
			int sel = InputManager.getInt("메뉴");
			if (sel == 1) {
				stuDAO.addStudent();
			} else if (sel == 2) {
				stuDAO.delStudent(subDAO);
			} else if (sel == 3) {
				stuDAO.addStudentSub(subDAO);
			} else if (sel == 4) {
				stuDAO.delStudentSub(subDAO);
			} else if (sel == 5) {
				stuDAO.printStudent(subDAO);
			} else if (sel == 6) {
				subDAO.printSubject(stuDAO);
			} else if (sel == 7) {
				fm.fileSave(getStuDAO(), getSubDAO());
			} else if (sel == 8) {
				getStuDAO().fileToData(fm.fileLoad("student.txt"));
				getSubDAO().fileToData(fm.fileLoad("subject.txt"));
			} else {
				System.out.println("프로그램 종료");
				return;
			}
		}
	}
}
