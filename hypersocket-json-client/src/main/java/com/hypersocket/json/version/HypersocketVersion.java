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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.prefs.Preferences;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class HypersocketVersion {

	static Map<String,String> versions = Collections.synchronizedMap(new HashMap<>());
	
	public static String getSerial() {
		Preferences pref = Preferences.userNodeForPackage(HypersocketVersion.class);
		
		String hypersocketId = System.getProperty("hypersocket.id", "hypersocket-one");
		if(pref.get("hypersocket.serial", null)!=null) {
			pref.put(hypersocketId, pref.get("hypersocket.serial", UUID.randomUUID().toString()));
			pref.remove("hypersocket.serial");
		} 
		String serial = pref.get(hypersocketId, UUID.randomUUID().toString());
		pref.put(hypersocketId, serial);
		return serial;
	}
	
	public static String getVersion(String artifactCoordinate) {
		String fakeVersion = System.getProperty("hypersocket.development.version");
		if(fakeVersion != null) {
			return fakeVersion;
		}
		
	    String version = versions.get(artifactCoordinate);
	    if(version != null)
	    	return version;

	    // try to load from maven properties first

		InputStream is = HypersocketVersion.class.getResourceAsStream("/META-INF/maven/" + artifactCoordinate + "/pom.properties");
		if(is != null) {
			try {
		        Properties p = new Properties();
		        if (is != null) {
		            p.load(is);
		            version = p.getProperty("version", "");
		        }
		    } catch (Exception e) {
		        // ignore
		    }
			finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}

		if(version == null) {
		    ClassLoader cl = Thread.currentThread().getContextClassLoader();
		    if(cl != null) {
		    	is = cl.getResourceAsStream("META-INF/maven/" + artifactCoordinate + "/pom.properties");
		    	if(is != null) {
					try {
				        Properties p = new Properties();
			            p.load(is);
			            version = p.getProperty("version", "");
				    } catch (Exception e) {
				        // ignore
				    }
					try {
						is.close();
					} catch (IOException e) {
					}
		    	}
		    }
		}

	    // fallback to using Java API
	    if (version == null) {
	        Package aPackage = HypersocketVersion.class.getPackage();
	        if (aPackage != null) {
	            version = aPackage.getImplementationVersion();
	            if (version == null) {
	                version = aPackage.getSpecificationVersion();
	            }
	        }
	    }

	    if (version == null) {
	    	try {
	    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse (new File("pom.xml"));
	            version = doc.getDocumentElement().getElementsByTagName("version").item(0).getTextContent();
	    	} catch (Exception e) {
				version = "DEV_VERSION";
			} 
	        
	    }

	    versions.put(artifactCoordinate, version);
	    return version;
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
