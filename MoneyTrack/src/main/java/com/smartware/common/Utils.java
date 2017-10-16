package com.smartware.common;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
	private static final Logger logger = Logger.getLogger(Utils.class.getName());

	public static final String DATE_TIME_DEFAULT_FORMAT = "dd/MM/yyyy HH:mm";
	public static final String DATE_DEFAULT_FORMAT = "dd/MM/yyyy";
	public static final String TIME_DEFAULT_FORMAT = "HH:mm";

	public static final String AMOUNT_FORMAT_PATTERN = "%.2f";

	public Properties getFileProperties(String propertiesFileName) {
		Properties prop = null;
		
		try {
			InputStream configPropStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			
			if (configPropStream != null) {
				prop = new Properties();
				prop.load(configPropStream);
			} else {
				logger.log(Level.SEVERE, "property file '" + propertiesFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prop;
	}

	public static String getFormattedDateTime(Date dateTime, String dateTimeFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		return sdf.format(dateTime);
	}

	public static String getFormattedDateTime(Date dateTime) {
		return getFormattedDateTime(dateTime, DATE_TIME_DEFAULT_FORMAT);
	}

	public static String getFormattedDate(Date dateTime) {
		return getFormattedDateTime(dateTime, DATE_DEFAULT_FORMAT);
	}

	public static String getFormattedTime(Date dateTime) {
		return getFormattedDateTime(dateTime, TIME_DEFAULT_FORMAT);
	}

	public static boolean isValidDateTimeString(String dateTimeStr, String dateTimeFormat) {
		boolean valid = true;
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		try {
			sdf.parse(dateTimeStr);
		}
		catch (ParseException ex) {
			valid = false;
		}
		return valid;
	}

	public static boolean isValidDateTimeString(String dateTimeStr) {
		return isValidDateTimeString(dateTimeStr, DATE_TIME_DEFAULT_FORMAT);
	}

	public static boolean isValidDateString(String dateStr) {
		return isValidDateTimeString(dateStr, DATE_DEFAULT_FORMAT);
	}

	public static boolean isValidTimeString(String timeStr) {
		return isValidDateTimeString(timeStr, TIME_DEFAULT_FORMAT);
	}

	public static Date getDateTimeFromString(String dateTimeStr, String dateTimeFormat) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		try {
			date = sdf.parse(dateTimeStr);
		}
		catch (ParseException ex) {
			logger.log(Level.SEVERE, "Date: " + dateTimeStr + " in format: '" + dateTimeFormat + "' is not valid." + ex.getMessage());
		}
		return date;
	}

	public static Date getDateTimeFromString(String dateTimeStr) {
		return getDateTimeFromString(dateTimeStr, DATE_TIME_DEFAULT_FORMAT);
	}

	public static Date getDateFromString(String dateTimeStr) {
		return getDateTimeFromString(dateTimeStr, DATE_DEFAULT_FORMAT);
	}

	public static Date getTimeFromString(String dateTimeStr) {
		return getDateTimeFromString(dateTimeStr, TIME_DEFAULT_FORMAT);
	}

	public static String getFormattedFloat(float floatNumber) {
		return String.format(AMOUNT_FORMAT_PATTERN, floatNumber);
	}

	public static boolean isValidFloatNumberString(String strFloatNumber) {
		boolean valid = true;
		try {
			Float.valueOf(strFloatNumber);
		}
		catch (Exception ex) {
			valid = false;
		}
		return valid;
	}

	public static Float getFloatNumberFromString(String strFloatNumber) {
		return Float.valueOf(strFloatNumber);
	}

}
