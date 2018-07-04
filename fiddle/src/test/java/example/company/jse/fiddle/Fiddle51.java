package example.company.jse.fiddle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class Fiddle51 {

	private static final int TARGET = 100;

	@Test
	public void fiddle() throws IOException {

		int num = 0;

		do {

			num = 0;

			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				XSSFSheet sheet = workbook.createSheet("Datatypes in Java");

				File f = new File("out.txt");
				try (PrintStream out = new PrintStream(new FileOutputStream(f))) {
					System.out.println("Collecting results in " + f.getAbsolutePath());

					Random rnd = new Random(System.currentTimeMillis());

					List<Integer> hand1 = new ArrayList<>();
					List<Integer> hand2 = new ArrayList<>();

					for (int i = 2; i <= 14; ++i) {
						hand1.add(i);
						hand1.add(i);
						hand2.add(i);
						hand2.add(i);
					}

					Collections.shuffle(hand1, rnd);
					Collections.shuffle(hand2, rnd);

					while (!hand1.isEmpty() && !hand2.isEmpty()) {

						out.println("----------------------------------------------");
						out.println("Tour #" + (++num));

						Row row = sheet.createRow(num);

						int power1 = power(hand1);
						int power2 = power(hand2);
						int avgpower1 = avgpower(hand1);
						int avgpower2 = avgpower(hand2);

						Cell cell1 = row.createCell(0);
						cell1.setCellValue(power1);
						Cell cell2 = row.createCell(1);
						cell2.setCellValue(power2);
						Cell cell3 = row.createCell(2);
						cell3.setCellValue(avgpower1);
						Cell cell4 = row.createCell(3);
						cell4.setCellValue(avgpower2);

						out.println("1's total power : " + power1);
						out.println("2's total power : " + power2);

						out.println("1's average power : " + avgpower1);
						out.println("2's average power : " + avgpower2);

						List<Integer> stack1 = new ArrayList<>();
						List<Integer> stack2 = new ArrayList<>();

						Integer c1 = hand1.remove(0);
						Integer c2 = hand2.remove(0);

						out.println("1 plays " + c1);
						out.println("2 plays " + c2);

						stack1.add(c1);
						stack2.add(c2);

						boolean stuck = false;

						while (stack1.get(stack1.size() - 1) == stack2.get(stack2.size() - 1)) {

							out.println("Battle");

							if (hand1.isEmpty()) {
								out.println("1 has no more cards");
							}
							if (hand2.isEmpty()) {
								out.println("2 has no more cards");
							}

							if (hand1.isEmpty() || hand2.isEmpty()) {
								stuck = true;
								break;
							}

							Integer hidden1 = hand1.remove(0);
							Integer hidden2 = hand2.remove(0);

							out.println("1's " + hidden1 + " at stake");
							out.println("2's " + hidden2 + " at stake");

							stack1.add(hidden1);
							stack2.add(hidden2);

							if (hand1.isEmpty()) {
								out.println("1 has no more cards");
							}
							if (hand2.isEmpty()) {
								out.println("2 has no more cards");
							}

							if (hand1.isEmpty() || hand2.isEmpty()) {
								stuck = true;
								break;
							}

							Integer more1 = hand1.remove(0);
							Integer more2 = hand2.remove(0);

							out.println("1 plays " + more1);
							out.println("2 plays " + more2);

							stack1.add(more1);
							stack2.add(more2);

						}

						if (!stuck) {
							Integer l1 = stack1.get(stack1.size() - 1);
							Integer l2 = stack2.get(stack2.size() - 1);

							StringBuilder sb = new StringBuilder();
							if (l1 > l2) {
								sb.append("1 wins");

								stack1.forEach(c -> sb.append(" ").append(c));
								stack2.forEach(c -> sb.append(" ").append(c));

								hand1.addAll(stack1);
								hand1.addAll(stack2);
							} else {
								sb.append("2 wins");

								stack2.forEach(c -> sb.append(" ").append(c));
								stack1.forEach(c -> sb.append(" ").append(c));

								hand2.addAll(stack2);
								hand2.addAll(stack1);
							}
							out.println(sb.toString());
						} else {
							out.println("Game stuck");
						}
					}

					if (hand1.isEmpty()) {
						out.println("2 wins");
					} else if (hand2.isEmpty()) {
						out.println("1 wins");
					} else {
						out.println("Nobody wins");
					}

					File file = new File("out.xlsx");
					workbook.write(new FileOutputStream(file));

					System.out.println("Data dumped to " + file.getAbsolutePath());

				}
			}
		} while (num < TARGET);
	}

	private int avgpower(List<Integer> hand1) {
		int[] power = new int[1];
		hand1.forEach(c -> power[0] += c);
		return power[0] / hand1.size();
	}

	private int power(List<Integer> hand1) {
		int[] power = new int[1];
		hand1.forEach(c -> power[0] += c);
		return power[0];
	}

}
