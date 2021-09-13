package id.co.map.mapwebreportapplication.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateConverter {

	public MyDateConverter() {
		
	}
	
	public String convertDate(Integer date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy");
			Date date1 = sdf.parse(date.toString());
			return newFormat.format(date1);
		}catch(Exception ex) {
			return ex.getMessage();
		}
	}
	
	public String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		return sdf.format(new Date());
	}
}
