package VO;

public class Student {
	private int stuNo;
	private String stuName;
	private String stuId;

	public Student(String stuNo, String stuName, String stuId) {
		this.stuNo = Integer.parseInt(stuNo);
		this.stuName = stuName;
		this.stuId = stuId;
	}

	public int getStuNo() {
		return stuNo;
	}

	public String getStuName() {
		return stuName;
	}

	public String getStuId() {
		return stuId;
	}

	@Override
	public String toString() {
		return stuNo + "\t" + stuName + "\t" + stuId;
	}

	public String saveData() {
		return stuNo + "/" + stuName + "/" + stuId + "\n";
	}
}
