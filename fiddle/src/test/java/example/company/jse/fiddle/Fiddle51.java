package example.company.jse.fiddle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class Fiddle51 {

	private static final int TARGET = 2790;

	@Test
	public void fiddle() throws IOException {

		int num = 0;
		int count = 0;
		int maxsofar = 0;
		Random rnd = new Random(System.currentTimeMillis());

		List<Integer> powers1 = new ArrayList<>();
		List<Integer> sizes1 = new ArrayList<>();
		StringWriter sw = new StringWriter();

		do {

			num = 0;
			sw = new StringWriter();
			powers1.clear();
			sizes1.clear();

			try (PrintWriter out = new PrintWriter(new WriterOutputStream(sw, Charset.forName("UTF-8")))) {

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

					int power1 = power(hand1);
					powers1.add(power1);
					sizes1.add(hand1.size());

					out.println("1's total power : " + power1);

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

			}

			++count;
			if (num > maxsofar) {
				maxsofar = num;
				System.out.println(count + " " + num);
			}

		} while (num < TARGET);

		File f = new File("out.txt");
		System.out.println("Collecting results in " + f.getAbsolutePath());

		IOUtils.copy(new ByteArrayInputStream(sw.toString().getBytes()), new FileOutputStream(f));

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet("Powers");
			for (int i = 0; i < powers1.size(); ++i) {
				Row row = sheet.createRow(i);
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(powers1.get(i));
				Cell cell2 = row.createCell(0);
				cell2.setCellValue(sizes1.get(i));
			}
			File file = new File("out.xlsx");
			workbook.write(new FileOutputStream(file));
			System.out.println("XLSX Data dumped to " + file.getAbsolutePath());
		}
	}

	private int power(List<Integer> hand) {
		int[] power = new int[1];
		hand.forEach(c -> power[0] += c);
		return power[0];
	}

}
