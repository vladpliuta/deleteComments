package by.pliuta.forCredo.deleteComments;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Программа удаляющая комментарии из текстого файла Пути к файлам прописываются
 * в файле filena.properties в папке resources
 */
public class Runner {
	public static void main(String[] args) throws IOException {
		// название нужного файла храним в файле ресурсов

		ResourceBundle resource = ResourceBundle.getBundle("filename");
		// получение пути к входному файлу
		String fileIn = resource.getString("fileIn");
		// пути к выходным файлам
		String fileOutJava = resource.getString("fileOutJava");
		String fileOut = resource.getString("fileOut");

		// РЕШЕНИЕ ДЛЯ JAVA

		List<String> lines = Files.readAllLines(Paths.get(fileIn), StandardCharsets.UTF_8);

		try (FileWriter writer = new FileWriter(fileOutJava, false)) {
			String commonLine = "";
			for (int i = 0; i < lines.size(); i++) {
				// убираем все однострочные
				String editLine = lines.get(i).replaceAll("//.*", "");
				commonLine = commonLine.concat(editLine).concat("\r\n");
			}
			// убираем все многострочные
			String finishLine = commonLine.replaceAll("/\\*([^\\*/]*\\s*[^\\*/]*)+\\*/", "");
			// пишем итог
			writer.append(finishLine);
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		// РЕШЕНИЕ в логике С++ как я ее понимаю

		try (FileReader reader = new FileReader(fileIn); FileWriter writer = new FileWriter(fileOut, false)) {
			// читаем посимвольно
			int symbolNumber;
			List<Character> textList = new ArrayList<Character>();
			while ((symbolNumber = reader.read()) != -1) {
				char symbol = (char) symbolNumber;
				textList.add(symbol);
			}
			Character[] text = {}; // конвертируем ArrayList в массив
			text = textList.toArray(new Character[textList.size()]);

			for (int i = 0; i < text.length; i++) {
				int end = i;// конец комментария
				if (text[i] == '/') {
					// проверка на однострочный комментарий
					if (text[i + 1] == '/') {
						for (int j = i + 1; j < text.length; j++) {
							if (text[j] == '\n') {
								end = j - 1;
								break;
							}
						}
						// проверка на многострочный комментарий
					} else if (text[i + 1] == '*') {

						// ищем конец многострочного
						for (int j = i + 2; j < text.length; j++) {
							if (text[j] == '*' && text[j + 1] == '/') {
								end = j + 1;
								break;
							}
						}
					}
				}
				// если что-то нашли переходим за комментарий
				if (end != i) {
					i = end;
				} else {
					// что осталось добовляем в конечный текст
					writer.append(text[i]);
				}
			}
			writer.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
