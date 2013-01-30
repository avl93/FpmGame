package ru.kubsu.fpm.game;

import java.util.ArrayList;

public class Xml {
	public ArrayList<Tag> tags = new ArrayList<Tag>();

	public Xml(String str) {
		parse(str);
	}

	private void parse(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '<') {
				int n = 0;
				String tag = new String();
				while (str.charAt(i + n) != '>') {
					tag += str.charAt(i + n);
					n++;
					if (i + n >= str.length()) {
						System.err
								.println("Xml parse error: tag is not closed");
						break;
					}
				}
				tag += '>';
				tags.add(new Tag(tag));
				i += n;
			}
		}
	}

	public String getName(int n) {
		return tags.get(n).name;
	}

	public int getIntAttr(int n, String attrName) {
		return Integer.parseInt(getStrAttr(n, attrName));
	}

	public float getFloatAttr(int n, String attrName) {	
		try {
			return Float.parseFloat(getStrAttr(n, attrName));
		} catch (NumberFormatException e) {
			System.err.println(attrName);
			e.printStackTrace();
		}
		return 0;	
	}

	public String getStrAttr(int n, String attrName) {
		int i = 0;
		for (i = 0; i < tags.get(n).attrs.size(); i++) {
			if (tags.get(n).attrs.get(i).name.equals(attrName)) {
				return tags.get(n).attrs.get(i).value;
			}
		}
		return "";
	}

}

class Tag {
	String name;
	ArrayList<Attr> attrs;

	public Tag(String str) {
		this.name = "";
		this.attrs = new ArrayList<Attr>();
		int i = 1;
		try {
			while (str.charAt(i) == ' ') {
				i++;
			}
			while (str.charAt(i) != ' ') {
				this.name += str.charAt(i);
				i++;
			}

			while (i < str.length()) {
				Attr currAttr = new Attr();
				while (str.charAt(i) == ' ') {
					i++;
				}
				while (str.charAt(i) != ' ' && str.charAt(i) != '=') {
					currAttr.name += str.charAt(i);
					i++;
				}
				while (str.charAt(i) == ' ' || str.charAt(i) == '=') {
					i++;
				}

				while (str.charAt(i) != ' ' && str.charAt(i) != '>') {
					currAttr.value += str.charAt(i);
					i++;
				}
				this.attrs.add(currAttr);
			}
		} catch (StringIndexOutOfBoundsException e) {
			// This is not an error
		}

	}
}

class Attr {
	String name;
	String value;

	public Attr() {
		this.name = "";
		this.value = "";
	}
}
