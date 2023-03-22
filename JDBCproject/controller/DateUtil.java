package JDBCproject.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static Date convertToDate(String sdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date sqlDate = null;
		try {
			java.util.Date d = sdf.parse(sdate); //util의 date만들기
			sqlDate = new Date(d.getTime()); //sql의 date만들기
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;
	}
}
