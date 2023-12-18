package DAO;

import java.util.ArrayList;
import java.util.Random;

import Util.InputManager;
import VO.Student;
import VO.Subject;

public class SubjectDAO {
	private ArrayList<Subject> subList;
	private Random random;

	public ArrayList<Subject> getSubList() {
		return subList;
	}

	public SubjectDAO() {
		subList = new ArrayList<Subject>();
		random = new Random();
	}

	private int subNoValue(int stuNo, String subName) {
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getStuNo() == stuNo && subList.get(i).getSubName().equals(subName)) {
				return i;
			}
		}
		return -1;
	}

	public void addStudentSub(int stuNo) {
		String subName = InputManager.getStr("과목");
		int idx = subNoValue(stuNo, subName);
		int score = random.nextInt(50) + 50;
		if (idx != -1) {
			subList.get(idx).getScore();
			return;
		}
		subList.add(new Subject(stuNo + "", subName, score + ""));
		System.out.println("과목 추가 완료");
	}

	public void delStudentSub(int stuNo) {
		if (subList.size() == 0) {
			System.err.println("과목 데이터가 존재하지 않습니다");
			return;
		}
		String subName = InputManager.getStr("과목");
		int idx = subNoValue(stuNo, subName);
		if (idx == -1) {
			System.err.println("과목이 존재하지 않습니다");
			return;
		}
		subList.remove(idx);
	}

	public void delStudentAllSub(int stuNo) {
		if (subList.size() == 0) {
			return;
		}
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getStuNo() == stuNo) {
				subList.remove(i);
				i -= 1;
			}
		}
	}

	public void printSubject(StudentDAO stuDAO) {
		String subName = InputManager.getStr("과목");
		boolean ck = true;
		int cnt = 0;
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getSubName().equals(subName)) {
				cnt += 1;
				ck = false;
			}
		}
		if (ck) {
			System.err.println("과목이 존재하지 않습니다");
			return;
		}
		ArrayList<Student> copy = new ArrayList<Student>();
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getSubName().equals(subName)) {
				for (int k = 0; k < stuDAO.getStuList().size(); k++) {
					if (subList.get(i).getStuNo() == stuDAO.getStuList().get(k).getStuNo()) {
						copy.add(stuDAO.getStuList().get(k));
						break;
					}
				}
			}
		}
		for (int i = 0; i < cnt; i++) {
			for (int k = 0; k < cnt; k++) {
				if (copy.get(i).getStuName().charAt(0) < copy.get(k).getStuName().charAt(0)) {
					Student info = copy.get(i);
					copy.set(i, copy.get(k));
					copy.set(k, info);
				}
			}
		}
		for (int i = 0; i < cnt; i++) {
			System.out.println(copy.get(i));
		}
	}

	public String dataToFile() {
		String data = "";
		for (int i = 0; i < subList.size(); i++) {
			data += subList.get(i).saveData();
		}
		data = data.substring(0, data.length() - 1);
		return data;
	}

	public void fileToData(String data) {
		subList.clear();
		if (data.length() == 0)
			return;
		String[] datas = data.split("\n");
		for (int i = 0; i < datas.length; i++) {
			String[] info = datas[i].split("/");
			subList.add(new Subject(info[0], info[1], info[2]));
		}
	}
}
