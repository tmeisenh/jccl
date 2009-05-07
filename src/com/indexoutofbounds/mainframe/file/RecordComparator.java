package com.indexoutofbounds.mainframe.file;

import java.util.Comparator;

/**
 * Simple Comparator implementation which allows us to sort a Collection of Record objects based on
 * a field's value.  This is limited to sorting/comparison against single columns only.
 * 
 * If the caller specifies a <code>fieldkey</code> value for which no field exists by that name,
 * then the comparison always returns 0 (which does not sort anything).
 * 
 * Example usage:
 * 
 * RecordComparator reccomp = new RecordComparator("myIndex");
 * Collections.sort(myList, reccomp);
 * 
 * @author Travis Meisenheimer
 *
 */

public class RecordComparator implements Comparator {

	private String fieldkey = "";

	public RecordComparator(String fieldkey) {
		this.fieldkey = fieldkey;
	}

	public int compare(Object obj1, Object obj2) {

		if ((obj1 != null || obj1 instanceof Record) && (obj2 != null || obj2 instanceof Record)) {
			
			Field col1 = ((Record) obj1).getFieldByName(fieldkey);
			Field col2 = ((Record) obj2).getFieldByName(fieldkey);
			
			return (col1 != null && col2 != null) ? col1.getValue().compareTo(col2.getValue()) : 0;
		}

		return 0;
	}

	public String getFieldkey() {
		return fieldkey;
	}


	public void setFieldkey(String string) {
		fieldkey = string;
	}

}