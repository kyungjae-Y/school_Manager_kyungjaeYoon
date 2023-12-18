package School_윤경재Ver3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Utils {

	private Scanner scan;
	private String CUR_PATH = "src//School_윤경재//";
	private static Utils instance = new Utils();

	public static Utils getInstance() {
		return instance;
	}

	public Utils() {
		scan = new Scanner(System.in);
	}

	public Scanner getScan() {
		return scan;
	}

	public void closeScanner() {
		scan.close();
	}

	public void nextLine() {
		scan.nextLine();
	}

	public String getStr(String msg) {
		System.out.printf("▶ %s 입력 : ", msg);
		return scan.next();
	}

	public int getInt(String msg) {
		System.out.printf("▶ %s 입력 : ", msg);
		int sel = -1;
		try {
			sel = scan.nextInt();
		} catch (Exception e) {
			System.out.println("숫자를 입력하세요");
		}
		scan.nextLine();
		return sel;
	}

	public void fileSave(String data, String fileName) {
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
			e.printStackTrace();
			System.out.println(fileName + " 로드 실패");
		}
		return data;
	}
}
