package by.pliuta.forCredo.deleteComments;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DeleteCommentsTest {
	private static List<String> linesIn, linesOutSample;
	private static List<Character> textListIn, textListSample;
	private static String fileIn, fileOutSample, fileOutPatterns, fileOutSort;

	@BeforeClass
	public static void initValues() throws Exception {
		ResourceBundle resource = ResourceBundle.getBundle("filenameTest");
		// путь к входным файлам
		fileIn = resource.getString("fileInTest");
		fileOutSample = resource.getString("fileOutSample");
		// путь к выходным файлам
		fileOutPatterns = resource.getString("fileOutPatternsTest");
		fileOutSort = resource.getString("fileOutSortTest");
		//
		linesIn = Files.readAllLines(Paths.get(fileIn), StandardCharsets.UTF_8);
		linesOutSample = Files.readAllLines(Paths.get(fileOutSample), StandardCharsets.UTF_8);
		//
		textListIn = createList(fileIn);
		textListSample = createList(fileOutSample);

	}

	private static List<Character> createList(String fileName) {
		List<Character> textList = new ArrayList<Character>();
		try (FileReader reader = new FileReader(fileName)) {

			int symbolNumber;
			while ((symbolNumber = reader.read()) != -1) {
				char symbol = (char) symbolNumber;
				textList.add(symbol);
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return textList;

	}

	@Test
	public void delCommentsWithSortTest() {
		try (FileWriter writer = new FileWriter(fileOutSort, false)) {
			DeleteCommentsMethod.delCommentsWithSort(textListIn, writer);

			List<Character> textListOut = createList(fileOutSort);
			assertTrue("The expected result is not obtained", textListSample.equals(textListOut));
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Test
	@Ignore("недоработан паттерн многострочного комментария")
	public void delCommentsWithPatternsTest() {
		try (FileWriter writer = new FileWriter(fileOutPatterns, false)) {
			DeleteCommentsMethod.delCommentsWithPatterns(linesIn, writer);

			List<String> linesOutPatterns = Files.readAllLines(Paths.get(fileOutPatterns), StandardCharsets.UTF_8);
			assertTrue("The expected result is not obtained", linesOutSample.equals(linesOutPatterns));
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
