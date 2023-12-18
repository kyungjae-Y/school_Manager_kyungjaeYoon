package School_윤경재Ver3;

import java.util.ArrayList;

public class StudentDAO {
	private ArrayList<Student> stuList;
	private int maxNo;

	public ArrayList<Student> getStuList() {
		return stuList;
	}

	public int getMaxNo() {
		return maxNo;
	}

	public StudentDAO() {
		stuList = new ArrayList<Student>();
		maxNo = 1001;
	}

	public void addStudent() {
		String id = Utils.getInstance().getStr("ID");
		Utils.getInstance().nextLine();
		if (stuIdValue(id) != -1) {
			System.err.println("아이디 중복");
			return;
		}
		String name = Utils.getInstance().getStr("이름");
		Utils.getInstance().nextLine();
		stuList.add(new Student("" + (maxNo++), name, id));
		System.out.println("학생 추가 완료");
	}

	private int stuIdValue(String id) {
		for (int i = 0; i < stuList.size(); i++) {
			if (stuList.get(i).getStuId().equals(id)) {
				return i;
			}
		}
		return -1;
	}

	private int stuNoValue(int stuNo) {
		for (int i = 0; i < stuList.size(); i++) {
			if (stuList.get(i).getStuNo() == stuNo) {
				return i;
			}
		}
		return -1;
	}

	public void delStudent(SubjectDAO subDAO) {
		if (stuList.size() == 0) {
			System.err.println("데이터가 없습니다");
			return;
		}
		String id = Utils.getInstance().getStr("ID");
		Utils.getInstance().nextLine();
		int idx = stuIdValue(id);
		if (idx == -1) {
			System.err.println("ID 가 존재하지 않습니다");
			return;
		}
		subDAO.delStudentAllSub(stuList.get(idx).getStuNo());
		stuList.remove(idx);
		System.out.println(id + " 삭제 완료");
	}

	public void addStudentSub(SubjectDAO subDAO) {
		int stuNo = Utils.getInstance().getInt("학번");
		int idx = stuNoValue(stuNo);
		if (idx == -1) {
			System.err.println("ID 가 존재하지 않습니다");
			return;
		}
		subDAO.addStudentSub(stuNo);
	}

	public void delStudentSub(SubjectDAO subDAO) {
		int stuNo = Utils.getInstance().getInt("학번");
		int idx = stuNoValue(stuNo);
		if (idx == -1) {
			System.err.println("ID 가 존재하지 않습니다");
			return;
		}
		subDAO.delStudentSub(stuNo);
	}

	public void printStudent(SubjectDAO subDAO) {
		ArrayList<Double> score = new ArrayList<Double>();
		ArrayList<Student> copy = new ArrayList<Student>();
		for (int i = 0; i < stuList.size(); i++) {
			copy.add(stuList.get(i));
		}
		for (int i = 0; i < copy.size(); i++) {
			int cnt = 0;
			for (int k = 0; k < subDAO.getSubList().size(); k++) {
				if (stuList.get(i).getStuNo() == subDAO.getSubList().get(k).getStuNo()) {
					if (cnt == 0) {
						score.add(1.0 * subDAO.getSubList().get(k).getScore());
					} else {
						score.set(i, score.get(i) + subDAO.getSubList().get(k).getScore());
					}
					cnt += 1;
				}
			}
			if (cnt == 0) {
				score.add(0.0);
			} else {
				score.set(i, cnt == 0 ? 0 : 1.0 * score.get(i) / cnt);
			}
		}
		for (int i = 0; i < score.size(); i++) {
			for (int k = i; k < score.size(); k++) {
				if (score.get(i) < score.get(k)) {
					double temp = score.get(i);
					score.set(i, score.get(k));
					score.set(k, temp);

					Student data = copy.get(i);
					copy.set(i, copy.get(k));
					copy.set(k, data);
				}
			}
		}
		System.out.println("학번\t이름\t점수");
		System.out.println("-----------------");
		for (int i = 0; i < copy.size(); i++) {
			System.out.println(copy.get(i));
			for (int k = 0; k < subDAO.getSubList().size(); k++) {
				if (copy.get(i).getStuNo() == subDAO.getSubList().get(k).getStuNo()) {
					System.out.println(subDAO.getSubList().get(k).toString());
				}
			}
			if (score.get(i) > 0) {
				System.out.printf("%.2f\n", score.get(i));
			}
			System.out.println("-----------------");
		}
	}

	public String dataToFile() {
		String data = "";
		for (int i = 0; i < stuList.size(); i++) {
			data += stuList.get(i).saveData();
		}
		data = data.substring(0, data.length() - 1);
		return data;
	}

	public void fileToData(String data) {
		String[] datas = data.split("\n");
		for (int i = 0; i < datas.length; i++) {
			String[] info = datas[i].split("/");
			stuList.add(new Student(info[0], info[1], info[2]));
			if (maxNo < Integer.parseInt(info[0])) {
				maxNo = Integer.parseInt(info[0]);
			}
		}
		maxNo += 1;
	}
}
