package by.pliuta.forCredo.deleteComments;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
 * класс представляет методы для поиска и замены комментариев
 */
public class DeleteCommentsMethod {
	
	
	public static void delCommentsWithPatterns(List<String> lines, FileWriter writer) throws IOException{
		String commonLine = "";
		for (int i = 0; i < lines.size(); i++) {
			// убираем все однострочные
			String editLine = lines.get(i).replaceAll("//.*", "");
			commonLine = commonLine.concat(editLine).concat("\r\n");
		}
		// убираем все многострочные
		String finishLine = commonLine.replaceAll("/\\*([^\\*/]*\\s*[^\\*/]*)+\\*/", "");
		writer.append(finishLine);
		writer.flush();
		}
	
	public static void delCommentsWithSort(List<Character> textList, FileWriter writer) throws IOException{
		for (int i = 0; i < textList.size(); i++) {
			int end = i;// конец комментария
			if (textList.get(i) == '/') {
				// проверка на однострочный комментарий
				if (textList.get(i+1) == '/') {
					for (int j = i + 1; j < textList.size(); j++) {
						if (textList.get(j+1) == '\n') {
							end = j - 1;
							break;
						}
					}
					// проверка на многострочный комментарий
				} else if (textList.get(i+1) == '*') {

					// ищем конец многострочного
					for (int j = i + 2; j < textList.size(); j++) {
						if (textList.get(j) == '*' && textList.get(j+1) == '/') {
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
				// что осталось добaвляем в конечный текст
				writer.append(textList.get(i));
			}
		}
		writer.flush();
	}

}
