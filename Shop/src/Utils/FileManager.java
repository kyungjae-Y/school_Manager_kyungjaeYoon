package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import dao.CartDAO;
import dao.ItemDAO;
import dao.UserDAO;

public class FileManager {
//	cart.txt
//	user.txt
// 	item.txt
	private static final String CUR_PATH = System.getProperty("user.dir") + "//src//"
			+ FileManager.class.getPackageName() + "//";

//	파일 저장
	public void fileSave(UserDAO uDAO, ItemDAO iDAO, CartDAO cDAO) {
		dataToFile(uDAO.datatoFile(), "user.txt");
		dataToFile(iDAO.datatoFile(), "item.txt");
		dataToFile(cDAO.datatoFile(), "cart.txt");
	}

	private void dataToFile(String data, String fileName) {
		try (FileWriter fw = new FileWriter(CUR_PATH + fileName)) {
			fw.write(data);
			System.out.println(fileName + "저장 성공");
		} catch (Exception e) {
			System.out.println(fileName + "저장 실패");
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
			if (data.length() != 0)
				data = data.substring(0, data.length() - 1);
			System.out.println(fileName + " 로드 성공");
		} catch (Exception e) {
			System.out.println(fileName + " 로드 실패");
		}
		return data;
	}
}