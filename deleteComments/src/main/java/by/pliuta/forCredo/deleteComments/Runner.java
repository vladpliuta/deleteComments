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
 * Программа удаляющая комментарии из текстого файла. Пути к файлам
 * прописываются в файле filename.properties в папке resources
 */
public class Runner {
	public static void main(String[] args) throws IOException {
		// название нужного файла храним в файле ресурсов

		ResourceBundle resource = ResourceBundle.getBundle("filename");
		// путь к входному файлу
		String fileIn = resource.getString("fileIn");
		// путь к выходным файлам
		String fileOutPatterns = resource.getString("fileOutPatterns");
		String fileOutSort = resource.getString("fileOutSort");

		try (FileWriter writer = new FileWriter(fileOutPatterns, false)) {
			// читаем исходник построчно
			List<String> lines = Files.readAllLines(Paths.get(fileIn), StandardCharsets.UTF_8);

			DeleteCommentsMethod.delCommentsWithPatterns(lines, writer);

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		try (FileReader reader = new FileReader(fileIn); FileWriter writer = new FileWriter(fileOutSort, false)) {
			// читаем исходник посимвольно
			int symbolNumber;
			List<Character> textList = new ArrayList<Character>();
			while ((symbolNumber = reader.read()) != -1) {
				char symbol = (char) symbolNumber;
				textList.add(symbol);
			}

			DeleteCommentsMethod.delCommentsWithSort(textList, writer);

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
