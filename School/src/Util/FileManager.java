package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import DAO.StudentDAO;
import DAO.SubjectDAO;

public class FileManager {
	private static final String CUR_PATH = System.getProperty("user.dir") + "//src//"
			+ FileManager.class.getPackageName() + "//";

	public void fileSave(StudentDAO stuDAO, SubjectDAO subDAO) {
		dataToFile(stuDAO.dataToFile(), "student.txt");
		dataToFile(subDAO.dataToFile(), "subject.txt");
	}

	public void dataToFile(String data, String fileName) {
		try (FileWriter fw = new FileWriter(CUR_PATH + fileName)) {
			fw.write(data);
			System.out.println(fileName + " 저장 성공");
		} catch (Exception e) {
			System.out.println(fileName + " 저장 실패");
		}
	}

	public String fileLoad(String fileName) {
		String data = "";
		try (FileReader fr = new FileReader(CUR_PATH + fileName); BufferedReader br = new BufferedReader(fr)) {
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				data += line + "\n";
			}
			data = data.substring(0, data.length() - 1);
			System.out.println(fileName + " 로드 성공");
		} catch (Exception e) {
			System.out.println(fileName + " 로드 실패");
		}
		return data;
	}
}
