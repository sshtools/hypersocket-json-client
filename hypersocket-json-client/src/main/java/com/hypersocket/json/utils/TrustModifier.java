/**
 * Copyright 2003-2016 SSHTOOLS Limited. All Rights Reserved.
 *
 * For product documentation visit https://www.sshtools.com/
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

import java.io.IOException;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TrustModifier {
   private static final TrustingHostnameVerifier 
      TRUSTING_HOSTNAME_VERIFIER = new TrustingHostnameVerifier();
   private static SSLSocketFactory factory;

   /** Call this with any HttpURLConnection, and it will 
    modify the trust settings if it is an HTTPS connection. */
   public static void relaxHostChecking(URLConnection conn) 
       throws IOException {

      if (conn instanceof HttpsURLConnection) {
         try {
			HttpsURLConnection httpsConnection = (HttpsURLConnection) conn;
			 SSLSocketFactory factory = prepFactory(httpsConnection);
			 httpsConnection.setSSLSocketFactory(factory);
			 httpsConnection.setHostnameVerifier(TRUSTING_HOSTNAME_VERIFIER);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new IOException(e.getMessage(), e);
		}
      }
   }

   static synchronized SSLSocketFactory 
            prepFactory(HttpsURLConnection httpsConnection) 
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

      if (factory == null) {
         SSLContext ctx = SSLContext.getInstance("TLS");
         ctx.init(null, new TrustManager[]{ new AlwaysTrustManager() }, null);
         factory = ctx.getSocketFactory();
      }
      return factory;
   }
   
   private static final class TrustingHostnameVerifier implements HostnameVerifier {
      public boolean verify(String hostname, SSLSession session) {
         return true;
      }
   }

   private static class AlwaysTrustManager implements X509TrustManager {
      public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
      public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
      public X509Certificate[] getAcceptedIssuers() { return null; }      
   }
   
}
