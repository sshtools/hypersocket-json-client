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
package com.hypersocket.json.version;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.jar.Manifest;
import java.util.prefs.Preferences;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

public class HypersocketVersion {

	static Map<String,String> versions = Collections.synchronizedMap(new HashMap<>());
	
	public static String getVersion() {
		return getVersion("hypersocket-common");
	}
	
	public static String getSerial() {
		Preferences pref = Preferences.userRoot().node("com").node("hypersocket");
		
		String hypersocketId = System.getProperty("hypersocket.id", "hypersocket-one");
		if(pref.get("hypersocket.serial", null)!=null) {
			pref.put(hypersocketId, pref.get("hypersocket.serial", UUID.randomUUID().toString()));
			pref.remove("hypersocket.serial");
		} 
		String serial = pref.get(hypersocketId, null);
		if(serial == null) {
			pref.put(hypersocketId, serial = UUID.randomUUID().toString());
		}
		return serial;
	}
	
	public static String getVersion(String artifactCoordinate) {
		int idx = artifactCoordinate.indexOf('/'); 
		if(idx == -1) {
			artifactCoordinate = "com.hypersocket/" + artifactCoordinate;
		}
		
		String fakeVersion = Boolean.getBoolean("hypersocket.development") ? 
				System.getProperty("hypersocket.development.version", System.getProperty("hypersocket.devVersion")) : null;
		if(fakeVersion != null) {
			return fakeVersion;
		}
		
	    String detectedVersion = versions.get(artifactCoordinate);
	    if(detectedVersion != null)
	    	return detectedVersion;

	    /* Load the MANIFEST.MF from all jars looking for the X-Extension-Version
	     * attribute. Any jar that has the attribute also can optionally have
	     * an X-Extension-Priority attribute. The highest priority is the
	     * version that will be used.
	     */
	    ClassLoader cl = Thread.currentThread().getContextClassLoader();
	    if(cl == null)
	    	cl = HypersocketVersion.class.getClassLoader();
		try {
			int highestPriority = -1;
			String highestPriorityVersion = null;
		    for(Enumeration<URL> en = cl.getResources("META-INF/MANIFEST.MF");
		    		en.hasMoreElements(); ) {
		    	URL url = en.nextElement();
		    	try(InputStream in = url.openStream()) {
			    	Manifest mf = new Manifest(in);	
			    	String extensionVersion = mf.getMainAttributes().getValue("X-Extension-Version");
			    	if(StringUtils.isNotBlank(extensionVersion)) {
				    	String priorityStr = mf.getMainAttributes().getValue("X-Extension-Priority");
				    	int priority = StringUtils.isBlank(priorityStr) ? 0 : Integer.parseInt(priorityStr);
				    	if(priority > highestPriority) {
				    		highestPriorityVersion = extensionVersion;
				    	}
			    	}
		    	}
		    }
		    if(highestPriorityVersion != null)
		    	detectedVersion = highestPriorityVersion;
	    }
	    catch(Exception e) {

	    }

		if(detectedVersion == null) {
		    if(cl != null) {
		    	InputStream is = cl.getResourceAsStream("META-INF/maven/" + artifactCoordinate + "/pom.properties");
		    	if(is != null) {
					try {
				        Properties p = new Properties();
			            p.load(is);
			            detectedVersion = p.getProperty("version", "");
				    } catch (Exception e) {
				        // ignore
				    } finally {
						try {
							is.close();
						} catch (IOException e) {
						}
					}
		    	}
		    }
		}

	    // fallback to using Java API
		if(StringUtils.isBlank(detectedVersion)) {
	        Package aPackage = HypersocketVersion.class.getPackage();
	        if (aPackage != null) {
	            detectedVersion = aPackage.getImplementationVersion();
	            if (detectedVersion == null) {
	                detectedVersion = aPackage.getSpecificationVersion();
	            }
	        }
	    }

		if(StringUtils.isBlank(detectedVersion)) {
	    	try {
	    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse (new File("pom.xml"));
	            detectedVersion = doc.getDocumentElement().getElementsByTagName("version").item(0).getTextContent();
	    	} catch (Exception e) {
			} 
	    }

		if(StringUtils.isBlank(detectedVersion)) {
			detectedVersion = "DEV_VERSION";
		}

	    /* Treat snapshot versions as build zero */
	    if(detectedVersion.endsWith("-SNAPSHOT")) {
	    	detectedVersion = detectedVersion.substring(0, detectedVersion.length() - 9) + "-0";
	    }

	    versions.put(artifactCoordinate, detectedVersion);

	    return detectedVersion;
	}

	public static String getProductId() {
		return System.getProperty("hypersocket.id", "hypersocket-one");
	} 
	
	public static String getBrandId() {
		String id = getProductId();
		int idx = id.indexOf('-');
		if(idx==-1) {
			throw new IllegalStateException("Product id must consist of string formatted like <brand>-<product>");
		}
		return id.substring(0, idx);
	} 
}
