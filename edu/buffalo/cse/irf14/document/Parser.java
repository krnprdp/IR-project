package edu.buffalo.cse.irf14.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	/**
	 * Static method to parse the given file into the Document object
	 * 
	 * @param filename
	 *            : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException
	 *             In case any error occurs during parsing
	 */

	public static Document parse(String filename) throws ParserException {

		String FileID, Category, Title = null, Author = null, AuthorOrg = null, Place = null, NewsDate = null, Content = null, FileN;
		Document d = new Document();
		if (filename == null || filename == "")
			throw new ParserException();
		FileN = filename;
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		BufferedReader r = null;
		File file = new File(FileN);
		if (!file.exists())
			throw new ParserException();
		FileID = file.getName();
		Category = file.getParentFile().getName();
		try {
			r = new BufferedReader(new FileReader(file));
			int i = 1, j = 3;
			Boolean isTitle = true, hasAuthor = false;
			String line;
			while ((line = r.readLine()) != null) {
				line = line.trim();
				if (!line.equals("")) {
					map.put(i, line);
					if (isTitle) {
						Title = line;
						isTitle = false;
					}
					if (hasAuthor == false && i == 2) {
						Pattern pattern = Pattern
								.compile("<AUTHOR>(.+?)</AUTHOR>");
						Matcher matcher = pattern.matcher(line);
						if (matcher.find()) {
							Author = matcher.group(1).replace("BY", "");
							AuthorOrg = Author.substring(
									Author.lastIndexOf(",") + 1).trim();
							Author = Author.split(",")[0].trim();
							hasAuthor = true;
						}
					}
					if (!hasAuthor && i == 2) {
						String[] str = checkPlace(line);
						Content = str[0];
						Place = str[1];
						NewsDate = str[2];
					} else if (hasAuthor && i == 3) {
						String[] str = checkPlace(line);
						Content = str[0];
						Place = str[1];
						NewsDate = str[2];
						j = 4;
					}
					if (i >= j) {
						Content = Content + map.get(i) + "\n";
					}
					i++;
				}
			}
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (Author != null) {
			Author = Author.replace("By", "").trim();
			d.setField(FieldNames.AUTHOR, Author);
			d.setField(FieldNames.AUTHORORG, AuthorOrg);
		} 
		d.setField(FieldNames.FILEID, FileID);
		d.setField(FieldNames.CATEGORY, Category);
		d.setField(FieldNames.TITLE, Title);
		d.setField(FieldNames.PLACE, Place);
		d.setField(FieldNames.NEWSDATE, NewsDate);
		d.setField(FieldNames.CONTENT, Content);

		return d;
	}

	private static String[] checkPlace(String line) {
		String Place = null, NewsDate = null, Content;
		String[] list = line.split("-");
		try {
			Content = list[1].trim();
		} catch (Exception e) {
			Content = "Blah blah blah.\n&#3";
		}
		String[] list2 = list[0].split(",");
		int len = list2.length;

		if (len == 2) {
			Place = list2[0].trim();
			NewsDate = list2[1].trim();
		}
		if (len == 3) {
			Place = list2[0].trim() + ", " + list2[1].trim();
			NewsDate = list2[2].trim();
		}
		String[] str = { Content, Place, NewsDate };
		return str;
	}
}
