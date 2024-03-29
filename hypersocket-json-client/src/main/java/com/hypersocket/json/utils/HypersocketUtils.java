/**
 * Copyright 2003-2020 JADAPTIVE Limited. All Rights Reserved.
 *
 * For product documentation visit https://www.jadaptive.com/
 *
 * This file is part of Hypersocket JSON Client.
 *
 * Hypersocket JSON Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Hypersocket JSON Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Hypersocket JSON Client.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hypersocket.json.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HypersocketUtils {

	public static final int ONE_MINUTE = 60000;
	public static final int ONE_HOUR = ONE_MINUTE * 60;
	public static final int ONE_DAY = ONE_HOUR * 24;

	static ThreadLocal<Long> times = new ThreadLocal<Long>();

	static Map<String,SimpleDateFormat> dateFormats = new HashMap<String,SimpleDateFormat>();
	
	static DecimalFormat df = new DecimalFormat("0.00");
	static SecureRandom random = new SecureRandom();
	
	public static void resetInterval() {
		times.set(System.currentTimeMillis());
	}

	public static void logInterval(String msg) {
		resetInterval();
	}

	public static File getConfigDir() {
		return new File(System.getProperty("hypersocket.conf", "conf"));
	}
	/**
	 * Encapsulate a part of a string by a given character.useful in hiding part of a password
	 * 
	 * @param original
	 *            Original string
	 * @param start
	 *            Start position of masking
	 * @param maskcharacter
	 *            masking character of the rest of the String
	 * @return if the given string length is shorter than start position then
	 *         return the original string, otherwise return original string with
	 *         replace masking character from the start position onward
	 */
	public static String maskingString(String original, int start,String maskcharacter) {
		String result = null;
		if (start < 0) {
			throw new IllegalArgumentException("Start position should be greater than 0");
		}
		if (original.length() > start) {
			result = original.substring(0, start)+ original.substring(start).replaceAll(".", maskcharacter);
		} else {
			result = original;
		}
		return result;
	}
	
	/**
	 * Format a date with a given format. Formats are cached to prevent excessive use of 
	 * DateFormat.
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		
		if(!dateFormats.containsKey(format)) {
			dateFormats.put(format, new SimpleDateFormat(format));
		}
		
		if(date==null) {
			return "";
		}
		
		return dateFormats.get(format).format(date);
	}
	
	public static String formatDateTime(Long date) {
		return formatDateTime(new Date(date));
	}
	
	public static String formatDateTime(Date date) {
		return formatDate(date, "EEE, d MMM yyyy HH:mm:ss");
	}
	
	public static String formatShortDate(Date date) {
		return formatDate(date, "EEE, d MMM yyyy");
	}
	
	/**
	 * Parse a date on a given format. 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date, String format) throws ParseException {
		if(!dateFormats.containsKey(format)) {
			dateFormats.put(format, new SimpleDateFormat(format));
		}
		
		return dateFormats.get(format).parse(date);
	}
	
	public static Date today() {
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		return date.getTime();
	}
	
	public static Date tomorrow() {
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		date.add(Calendar.DAY_OF_MONTH, 1);
		
		return date.getTime();
	}
	
	public static Date yesterday() {
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		date.add(Calendar.DAY_OF_MONTH, -1);
		return date.getTime();
	}

	/**
	 * Strip the port from a host header.
	 * @param hostHeader
	 * @return
	 */
	public static String stripPort(String hostHeader) {
		int idx = hostHeader.indexOf(':');
		if(idx > -1) {
			return hostHeader.substring(0, idx);
		}
		return hostHeader;
	}

	public static String before(String value, String string) {
		int idx = value.indexOf(string);
		if(idx > -1) {
			return value.substring(0,idx);
		}
		return value;
	}
	
	public static String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	public static String base64Encode(String resourceKey) {
		try {
			return base64Encode(resourceKey.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System does not appear to support UTF-8!", e);
		}
	}

	public static byte[] base64Decode(String property) throws IOException {
		return Base64.getDecoder().decode(property);
	}
	
	public static boolean isValidJSON(final String json) {
		   try {
		      final JsonParser parser = new ObjectMapper().getFactory()
		            .createParser(json);
		      while (parser.nextToken() != null) {
		      }
		      return true;
		   } catch (JsonParseException jpe) {
		      return false;
		   } catch (IOException ioe) {
			   return false;
		   }

		}

	public static String format(Double d) {
		return df.format(d);
	}
	
	public static String format(Float f) {
		return df.format(f);
	}
	
	public static String prettyPrintXml(String unformattedXml) throws TransformerFactoryConfigurationError, UnsupportedEncodingException, TransformerException {

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new StringWriter());
		transformer.transform(
				new StreamSource(new ByteArrayInputStream(unformattedXml.getBytes("UTF-8"))),
				result);
		return result.getWriter().toString();
	}
	
	public static String checkNull(String str) {
		if(str==null) {
			return "";
		}
		return str;
	}
	
	public static String checkNull(String str, String def) {
		if(str==null) {
			return def;
		}
		return str;
	}

	public static String urlEncode(String message) {
		try {
			return URLEncoder.encode(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System does not appear to support UTF-8!", e);
		}
	}
	
	public static String urlDecode(String message) {
		try {
			return URLDecoder.decode(message, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System does not appear to support UTF-8!", e);
		}
	}

	public static boolean isUUID(String attachment) {
		 try {
			UUID.fromString(attachment);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String stripQuery(String url) {
		int idx = url.indexOf('?');
		if(idx > -1) {
			url = url.substring(0,  idx);
		}
		return url;
	}

	public static boolean isIPAddress(String ip) {
		return IPAddressValidator.getInstance().validate(ip);
	}
	
	public static String generateRandomAlphaNumericString(int length) {
	    return new BigInteger(length * 8, random).toString(32).substring(0,  length);
	}


	public static <T> List<T> nullSafe(List<T> list){
		if(list == null){
			return Collections.<T>emptyList();
		}
		return list;
	}

	public static <T> Set<T> nullSafe(Set<T> set){
		if(set == null){
			return Collections.<T>emptySet();
		}
		return set;
	}


	public static <S extends Throwable,D extends Throwable> S chain(S source, D target) {
		try {
			FieldUtils.writeField(source, "cause", target, true);
			return source;
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Problem in chaining exceptions", e);
		}
	}

	public static String csv(String[] items) {
		StringBuffer b = new StringBuffer();
		for(String i : items) {
			if(b.length() > 0) {
				b.append(",");
			}
			b.append(i);
		}
		return b.toString();
	}
	
	public static String csv(Object[] items) {
		StringBuffer b = new StringBuffer();
		for(Object i : items) {
			if(b.length() > 0) {
				b.append(",");
			}
			b.append(i.toString());
		}
		return b.toString();
	}

	public static String prettyPrintJson(String output) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(output, Object.class));
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);

		}
	}

	public static String encodeURIPath(String url) {
		return url.replace(" ", "%20");
	}

	public static Date thirtyDays() {
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		date.add(Calendar.DAY_OF_MONTH, 30);
		
		return date.getTime();
	}
	
	public static Date sevenDays() {
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		
		date.add(Calendar.DAY_OF_MONTH, 7);
		
		return date.getTime();
	}

	public static byte[] getUTF8Bytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public static Date convertToUTC(Date date, TimeZone tz) {
		return new Date(date.getTime() + tz.getOffset(date.getTime()));
	}

}
