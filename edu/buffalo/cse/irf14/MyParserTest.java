package edu.buffalo.cse.irf14;

import java.io.File;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;

public class MyParserTest {
	private static int d = 0, f = 0;

	public static void main(String... args) {
		final long startTime = System.currentTimeMillis();
		// System.out.println(startTime);

		File[] files = new File(
				"/Users/Pradeep/Desktop/Sem-1/training/")
				.listFiles();
		// File[] files = new File("/Users/Pradeep/Desktop/untitled folder/")
		// .listFiles();
		showFiles(files);
		final long endTime = System.currentTimeMillis();
		System.out.println(d);
		System.out.println(f);
		System.out.println("Total execution time: " + (endTime - startTime));
	}

	private static void showFiles(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				 System.out.println("Directory: " + file.getName());
				d++;
				showFiles(file.listFiles());
			} else {
				Document d;
				try {
					d = Parser.parse(file.getAbsolutePath());
				} catch (ParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f++;
			}
		}
	}
}
