package edu.buffalo.cse.irf14.analysis.FilterRules;

import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.analysis.TokenFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateRule extends TokenFilter {

	TokenStream ts;
	TokenStream stream;

	public DateRule(TokenStream stream) {
		super(stream);
		this.stream = stream;
		this.ts = stream;
		String txt = "";
		if (stream != null) {
			String token;

			while (stream.hasNext()) {
				token = stream.next().toString();
				txt = txt + " " + token;
			}
			stream.reset();
			String[] str, str1;
			String date; // stores the final date
			String dd = "01", mm = "01", yy = "1900";
			Boolean dateFound = false;
			Boolean timeFound = false;
			txt = txt.replaceAll(" +", " ");
			Pattern p1 = Pattern.compile("\\d+ \\w+ \\d{4}");
			Pattern p2 = Pattern.compile("\\w+ \\d+, \\d{4}");
			Pattern p3 = Pattern.compile("\\d+ ?(BC|AD)");
			Pattern p4 = Pattern.compile("\\d{4}");
			Pattern p5 = Pattern.compile("\\d{4}-\\d{2}");
			Pattern p6 = Pattern.compile("\\w+ \\d{1,2}");
			Pattern p7 = Pattern.compile("\\d{1,2} \\w+");
			Pattern p8 = Pattern
					.compile("\\d{1,2}:\\d{1,2} ?(AM|PM|am|pm)\\.{0,1}");
			Matcher m1 = p1.matcher(txt);
			Matcher m2 = p2.matcher(txt);
			Matcher m3 = p3.matcher(txt);
			Matcher m4 = p4.matcher(txt);
			Matcher m5 = p5.matcher(txt);
			Matcher m6 = p6.matcher(txt);
			Matcher m7 = p7.matcher(txt);
			Matcher m8 = p8.matcher(txt);

			if (!timeFound) {
				if (m8.find()) {
					System.out.println(m8.group(0));
					int hour = 0;
					String time = null, min = "", sec = "00", sp = "";
					timeFound = true;
					str = m8.group(0).split(" ");
					
					if (str.length == 2) {
						String[] str2 = str[0].split(":");
						hour = Integer.parseInt(str2[0]);
						min = str2[1];
						if (str[1].charAt(0) == 'p' || str[1].charAt(0) == 'P')
							hour += 12;
						time = Integer.toString(hour) + ":" + min + ":" + sec
								+ sp;

						while (stream.hasNext()) {

							String t2 = stream.next().toString();
							if (t2 != null && t2 != "") {
								if (t2.equalsIgnoreCase(str[0].trim())) {
									stream.remove();
								}
								if (t2.equalsIgnoreCase(str[1].trim())) {

									if (str[1].length() == 3) {
										if (str[1].charAt(2) == ".".charAt(0))
											stream.replace(time.trim() + ".");
									} else
										stream.replace(time.trim());
									// System.out.println(time);
								}
								if (t2.equalsIgnoreCase(str[1] + ",")) {
									stream.replace(time + ",");
								}

							}
						}
						stream.reset();

						while (stream.hasNext()) {
							String t = stream.next().toString();

						}
					}
					if (str.length == 1) {
						String sp2 = "";
						String[] str2 = str[0].split(":");
						hour = Integer.parseInt(str2[0]);
						if (str2[1].charAt(2) == 'p'
								|| str2[1].charAt(2) == 'P')
							hour += 12;
						min = str2[1].substring(0, 2);
						time = Integer.toString(hour) + ":" + min + ":" + sec;
						while (stream.hasNext()) {

							String t2 = stream.next().toString();
							if (t2 != null && t2 != "") {
								if (str[0].equalsIgnoreCase(t2)) {
									if (str[0].contains(".")) {
										stream.replace(time.trim() + ".");
									} else
										stream.replace(time.trim());
								}
								if (t2.equalsIgnoreCase(str[0] + ",")) {
									stream.replace(time + ",");
								}
							}
						}

					}
				}

			}
			if (!dateFound) {
				if (m5.find()) {
					str = m5.group(0).split("-");
					date = str[0];
					str[1] = str[0].substring(0, 2) + str[1];
					date = date + mm + dd + "-" + str[1] + mm + dd;
					// txt = txt.replace(m5.group(0), date);
					dateFound = true;
					while (stream.hasNext()) {
						String t2 = stream.next().toString();
						if (t2 != null && t2 != "") {
							if (t2.equalsIgnoreCase(m5.group(0).trim())) {
								stream.replace(date);
							}
							if (t2.equalsIgnoreCase(m5.group(0).trim() + ".")) {
								stream.replace(date + ".");
							}
							if (t2.equalsIgnoreCase(m5.group(0).trim() + ",")) {
								stream.replace(date + ",");
							}
						}
					}
				}
			}
			if (!dateFound) {
				if (m1.find()) {
					str = m1.group(0).split(" ");

					dd = str[0];
					mm = str[1];
					yy = str[2];
					if (dd.length() == 1)
						dd = "0" + dd;
					mm = getMonth(mm);
					date = yy + mm + dd;
					txt = txt.replace(m1.group(0), date);
					dateFound = true;

					while (stream.hasNext()) {
						String t2 = stream.next().toString();
						if (t2 != null && t2 != "") {
							if (t2.equalsIgnoreCase(str[0])) {
								stream.remove();
							}
							if (t2.equalsIgnoreCase(str[1])) {
								stream.remove();
							}
							if (t2.equalsIgnoreCase(str[2])) {
								stream.replace(date);
							}
						}
					}
				}
				if (!dateFound) {
					if (m2.find()) {
						str = m2.group(0).split(",");
						str1 = str[0].split(" ");
						mm = str1[0].trim();
						dd = str1[1].trim();
						yy = str[1].trim();
						mm = getMonth(mm);
						if (dd.length() == 1)
							dd = "0" + dd;
						date = yy + mm + dd;
						txt = txt.replace(m2.group(0), date);
						dateFound = true;
						stream.reset();
						while (stream.hasNext()) {
							String t2 = stream.next().toString();
							if (t2 != null && t2 != "") {
								if (t2.equalsIgnoreCase(str1[0].trim())) {
									stream.remove();
								}
								if (t2.equalsIgnoreCase(str1[1].trim() + ",")) {
									stream.remove();
								}
								if (t2.equalsIgnoreCase(str[1].trim() + ",")) {
									stream.replace(date + ",");
								}
								if (t2.equalsIgnoreCase(str[1].trim())) {
									stream.replace(date);
								}
							}
						}
					}
				}
				if (!dateFound) {
					if (m3.find()) {
						String temp = m3.group(0);
						str = temp.split(" ");
						int y2;
						String y = "";
						StringBuilder sb = new StringBuilder(temp.length());
						for (int i = 0; i < temp.length(); i++) {
							final char c = temp.charAt(i);
							if (c > 47 && c < 58) {
								sb.append(c);
							}
						}
						y2 = sb.toString().length();
						if (y2 == 1)
							y = "000";
						if (y2 == 2)
							y = "00";
						if (y2 == 3)
							y = "0";
						yy = y + sb.toString();
						if (temp.contains("BC"))
							yy = "-" + yy;
						date = yy + mm + dd;
						txt = txt.replace(m3.group(0), date);
						dateFound = true;
						while (stream.hasNext()) {
							String t2 = stream.next().toString();
							if (str.length == 2) {
								if (t2 != null && t2 != "") {
									if (str[1] != null) {
										if (t2.equalsIgnoreCase(str[0].trim())) {
											stream.remove();
										}
										if (t2.equalsIgnoreCase(str[1].trim())) {
											stream.replace(date);
										}
										if (t2.equalsIgnoreCase(str[1].trim()
												+ ".")) {
											stream.replace(date + ".");
										}
										if (t2.equalsIgnoreCase(str[1].trim()
												+ ",")) {
											stream.replace(date + ",");
										}
									}
								}
							}
							if (str.length == 1) {
								if (t2.equalsIgnoreCase(str[0].trim())) {
									stream.replace(date);
								}
								if (t2.equalsIgnoreCase(str[0].trim() + ".")) {
									stream.replace(date + ".");
								}
								if (t2.equalsIgnoreCase(str[0].trim() + ",")) {
									stream.replace(date + ",");
								}
							}
						}

					}
				}
				if (!dateFound) {
					if (m4.find()) {
						date = m4.group(0) + mm + dd;
						txt = txt.replace(m4.group(0), date);
						dateFound = true;
						while (stream.hasNext()) {
							String t2 = stream.next().toString();
							if (t2 != null && t2 != "") {
								if (t2.equalsIgnoreCase(m4.group(0).trim())) {
									stream.replace(date);
								}
							}
						}

					}
				}

				if (!dateFound) {
					if (m6.find()) {
						str = m6.group(0).split(" ");
						mm = getMonth(str[0]);
						dd = str[1];
						date = yy + mm + dd;
						txt = txt.replace(m6.group(0), date);
						dateFound = true;

						while (stream.hasNext()) {
							String t2 = stream.next().toString();
							if (t2 != null && t2 != "") {
								if (t2.equalsIgnoreCase(str[0])) {
									stream.remove();
								}
								if (t2.equalsIgnoreCase(str[1])) {
									stream.replace(date);
								}
								if (t2.equalsIgnoreCase(str[1] + ".")) {
									stream.replace(date + ".");
								}
								if (t2.equalsIgnoreCase(str[1] + ",")) {
									stream.replace(date + ",");
								}
							}
						}

					}
				}
				if (!dateFound) {
					if (m7.find()) {
						if (m7.group(0).contains("am")
								|| m7.group(0).contains("AM")
								|| m7.group(0).contains("PM")
								|| m7.group(0).contains("pm")) {
							// do nothing
						} else {
							str = m7.group(0).split(" ");
							mm = getMonth(str[1]);
							dd = str[0];
							date = yy + mm + dd;
							txt = txt.replace(m7.group(0), date);
							dateFound = true;

							while (stream.hasNext()) {
								String t2 = stream.next().toString();
								if (t2 != null && t2 != "") {
									if (t2.equalsIgnoreCase(str[0])) {
										stream.remove();
									}
									if (t2.equalsIgnoreCase(str[1])) {
										stream.replace(date);
									}
									if (t2.equalsIgnoreCase(str[1] + ".")) {
										stream.replace(date + ".");
									}
									if (t2.equalsIgnoreCase(str[1] + ",")) {
										stream.replace(date + ",");
									}
								}
							}

						}
					}
				}

			}
			stream.reset();
		}
	}

	private String getMonth(String mm) {
		String month = "01";
		if (mm.equalsIgnoreCase("January"))
			month = "01";
		if (mm.equalsIgnoreCase("February"))
			month = "02";
		if (mm.equalsIgnoreCase("March"))
			month = "03";
		if (mm.equalsIgnoreCase("April"))
			month = "04";
		if (mm.equalsIgnoreCase("May"))
			month = "05";
		if (mm.equalsIgnoreCase("June"))
			month = "06";
		if (mm.equalsIgnoreCase("July"))
			month = "07";
		if (mm.equalsIgnoreCase("August"))
			month = "08";
		if (mm.equalsIgnoreCase("September"))
			month = "09";
		if (mm.equalsIgnoreCase("October"))
			month = "10";
		if (mm.equalsIgnoreCase("November"))
			month = "11";
		if (mm.equalsIgnoreCase("December"))
			month = "12";

		return month;
	}

	@Override
	public boolean increment() throws TokenizerException {
		return false;
	}

	@Override
	public TokenStream getStream() {
		return stream;
	}

}
