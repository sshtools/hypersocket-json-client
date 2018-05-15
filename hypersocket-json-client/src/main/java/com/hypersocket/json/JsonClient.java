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
package com.hypersocket.json;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hypersocket.ServerInfo;
import com.hypersocket.utils.HypersocketUtils;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class JsonClient {

	static Logger log = LoggerFactory.getLogger(JsonClient.class);
	
	protected ObjectMapper mapper = new ObjectMapper();
	protected JsonSession session;
	
	private OkHttpClient client = null;
	boolean debug = false;
	boolean allowSelfSigned = false;
	String hostname;
	int port;
	String path;
	String scheme = "basic";
	CookieJar cookies = null;
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
//	public JsonClient(String hostname, int port, String path) throws IOException {
//		this.hostname = hostname;
//		this.port = port;
//		this.path = path;
//	}
//	
//	public JsonClient(String hostname, int port) throws IOException {
//		this(hostname, port, false);
//	}
	
	public JsonClient(String hostname, int port, boolean allowSelfSigned) throws IOException {
		this.hostname = hostname;
		this.port = port;
		this.path = "";
		
		setAllowSelfSignedCertificates(allowSelfSigned);
		
		try {
			
			if(log.isInfoEnabled()) {
				log.info("Discovering server path configuration");
			}
			
			String json = doGet("/discover");
			ServerInfo info = mapper.readValue(json, ServerInfo.class);
			this.path = info.getBasePath();
			
			if(log.isInfoEnabled()) {
				log.info(String.format("Server application path is %s", this.path));
			}
			
		} catch (JsonStatusException e) {
			throw new IOException(e.getMessage(), e);
		}
	}
	
	protected OkHttpClient getClient() throws NoSuchAlgorithmException, KeyManagementException {
		
		if(client!=null) {
			return client;
		}
		
		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS);
	    clientBuilder.cookieJar(new CookieMonster());
	    
		if(allowSelfSigned) {
			final X509TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
	            @Override
	            public X509Certificate[] getAcceptedIssuers() {
	                X509Certificate[] cArrr = new X509Certificate[0];
	                return cArrr;
	            }
	
	            @Override
	            public void checkServerTrusted(final X509Certificate[] chain,
	                                           final String authType) throws CertificateException {
	            }
	
	            @Override
	            public void checkClientTrusted(final X509Certificate[] chain,
	                                           final String authType) throws CertificateException {
	            }
	        }};
	
	        SSLContext sslContext = SSLContext.getInstance("TLS");
	        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	        
	        clientBuilder.sslSocketFactory(sslContext.getSocketFactory(), trustAllCerts[0]);
	
	        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
	            @Override
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	        clientBuilder.hostnameVerifier( hostnameVerifier);
        
		}
		
		return client = clientBuilder.build();
	}
	public void logon(String username, String password) throws IOException, JsonStatusException {
		logon(username, password, false, null);
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public void setAuthenticationScheme(String scheme) {
		this.scheme = scheme;
	}
	
	public void setAllowSelfSignedCertificates(boolean allowSelfSigned) {
		this.allowSelfSigned = allowSelfSigned;
	}
	
	public void logon(String username, String password,
			boolean expectChangePassword, String newPassword) throws IOException, JsonStatusException {

		String logonJson = doPost(String.format("api/logon/%s", scheme),
				new RequestParameter("username", username),
				new RequestParameter("password", password));

		debugJSON(logonJson);

		AuthenticationResult logonResult = mapper.readValue(logonJson,
				AuthenticationResult.class);
		if (logonResult.getSuccess()) {
			JsonLogonResult logon = mapper.readValue(logonJson,
					JsonLogonResult.class);
			session = logon.getSession();
		} else {
			throw new IOException("Authentication failed");
		}
	}

	public boolean isLoggedOn() throws IOException {
		
		try {
			String logonJson = doGet("api/session/touch");
	
			debugJSON(logonJson);
	
			AuthenticationResult logonResult = mapper.readValue(logonJson,
					AuthenticationResult.class);
			if (logonResult.getSuccess()) {
				return true;
			} else {
				return false;
			}
		} catch(JsonStatusException ex) {
			return false;
		}
	}
	public String debugJSON(String json) throws JsonParseException,
			JsonMappingException, IOException {
		if(debug) {
			Object obj = mapper.readValue(json, Object.class);
			String ret = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(obj);
			log.info(ret);
			return ret;
		}
		return json;
	}

	public void logoff() throws JsonParseException,
			JsonMappingException, JsonStatusException, IOException, URISyntaxException {

		doGet("api/logoff");
		session = null;
	}

	public <T> T doPost(String url, Class<T> clz, RequestParameter... postVariables)
			throws IOException, JsonStatusException {
		
		String json = doPost(url, postVariables);
		
		debugJSON(json);
		
		return mapper.readValue(json, clz);
	}
	
	private String buildUrl(String uri) {
		StringBuffer buf = new StringBuffer();
		buf.append("https://");
		buf.append(hostname);
		if(port!=443) {
			buf.append(":");
			buf.append(port);
		}
		if(!uri.startsWith("/")) {
			buf.append(path);
			if(!path.endsWith("/")) {
				buf.append("/");
			}
			buf.append(uri);
		} else {
			buf.append(uri);
		}

		return buf.toString();
	}
	
	public String doPost(String url, RequestParameter... postVariables)
			throws IOException, JsonStatusException {

		url = HypersocketUtils.encodeURIPath(url);
		
		FormBody.Builder builder = new FormBody.Builder();
		for(RequestParameter param : postVariables) {
			builder.add(param.getName(), param.getValue());
		}
		        
		Request request = new Request.Builder()
		        .url(buildUrl(url))
		        .addHeader("X-Csrf-Token", session==null ? "<unknown>" : session.getCsrfToken())
		        .post(builder.build())
		        .build();
		
		try(Response response = getClient().newCall(request).execute()) {
			

			if(response.code()!=200) {
				throw new JsonStatusException(response.code());
			}
			
			return response.body().string();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage(), e);
		} 
	}

	public String doGet(String url) throws
			IOException, JsonStatusException {

		url = HypersocketUtils.encodeURIPath(url);
		
		Request request = new Request.Builder()
		        .url(buildUrl(url))
		        .addHeader("X-Csrf-Token", session==null ? "<unknown>" : session.getCsrfToken())
		        .get()
		        .build();
		
		try(Response response = getClient().newCall(request).execute()) {
			if(response.code()!=200) {
				throw new JsonStatusException(response.code());
			}
			
			return response.body().string();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage(), e);
		} 
	}

	public <T> T doDelete(String url, Class<T> clz)
			throws IOException, JsonStatusException {
		
		String json = doDelete(url);
		
		debugJSON(json);
		
		return mapper.readValue(json, clz);
	}
	
	public String doDelete(String url)
			throws IOException, JsonStatusException {

		url = HypersocketUtils.encodeURIPath(url);
		
		Request request = new Request.Builder()
		        .url(buildUrl(url))
		        .addHeader("X-Csrf-Token", session==null ? "<unknown>" : session.getCsrfToken())
		        .delete()
		        .build();
		
		try(Response response = getClient().newCall(request).execute()) {

			if(response.code()!=200) {
				throw new JsonStatusException(response.code());
			}
			
			return response.body().string();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage(), e);
		} 
	}

//	public String doPostMultiparts(String url,
//			PropertyObject[] properties, MultipartObject... files)
//			throws IOException, JsonStatusException {
//		
//		if (!url.startsWith("/")) {
//			url = "/" + url;
//		}
//		
//		url = HypersocketUtils.encodeURIPath(url);
//		
//		HttpPost postMethod = new HttpPost(String.format("https://%s:%d%s%s", hostname, port, path, url));
//		postMethod.addHeader("X-Csrf-Token", session==null ? "<unknown>" : session.getCsrfToken());
//		
//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		if (properties != null && properties.length > 0) {
//			for (PropertyObject property : properties) {
//				builder.addPart(property.getProertyName(), new StringBody(
//						property.getPropertyValue(),
//						ContentType.APPLICATION_FORM_URLENCODED));
//			}
//		}
//		for (MultipartObject file : files) {
//			builder.addPart(file.getProperty(), new FileBody(file.getFile()));
//		}
//		HttpEntity reqEntity = builder.build();
//		postMethod.setEntity(reqEntity);
//		
//		if(log.isInfoEnabled()) {
//			log.info("Executing request " + postMethod.getRequestLine());
//		}
//		
//		CloseableHttpResponse response = HttpUtilsHolder.getInstance().execute(postMethod, allowSelfSigned);
//		
//		if(log.isInfoEnabled()) {
//			log.info("Response: " + response.getStatusLine().toString());
//		}
//		
//		if (response.getStatusLine().getStatusCode() != 200) {
//			throw new JsonStatusException(response.getStatusLine().getStatusCode());
//		}
//
//		try {
//			return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
//		} finally {
//			response.close();
//		}
//	}

	public <T> T doGet(String url, Class<T> clz)
			throws JsonStatusException, IOException {
		
		String json = doGet(url);
		
		debugJSON(json);
		
		return mapper.readValue(json, clz);
	}

	public String doPost(String url, String json)
			throws URISyntaxException, JsonStatusException, IOException {

		url = HypersocketUtils.encodeURIPath(url);
		
		RequestBody body = RequestBody.create(JSON, json);
		
		Request request = new Request.Builder()
		        .url(buildUrl(url))
		        .addHeader("X-Csrf-Token", session==null ? "<unknown>" : session.getCsrfToken())
		        .post(body)
		        .build();
		
		try(Response response = getClient().newCall(request).execute()) {
			
			if(response.code()!=200) {
				throw new JsonStatusException(response.code());
			}
			
			return response.body().string();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage(), e);
		}

	}

	public String doPostJson(String url, Object jsonObject)
			throws URISyntaxException, IllegalStateException, IOException, JsonStatusException {


		url = HypersocketUtils.encodeURIPath(url);
		
		String json = mapper.writeValueAsString(jsonObject);

		return doPost(url, json);
	}

	public JsonSession getSession() {
		return session;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

	public InputStream doGetInputStream(String url) throws UnsupportedOperationException, IOException, JsonStatusException {

		url = HypersocketUtils.encodeURIPath(url);
		
		Request request = new Request.Builder()
		        .url(buildUrl(url))
		        .addHeader("X-Csrf-Token", session==null ? "<unknown>" : session.getCsrfToken())
		        .get()
		        .build();
		
		Response response = null;

		try {
			response = getClient().newCall(request).execute();

			if(response.code()!=200) {
				throw new JsonStatusException(response.code());
			}
			
			return response.body().byteStream();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage(), e);
		} 
	
	}
	
	class CookieMonster implements CookieJar {

		Map<String,Cookie> storedCookies = new HashMap<String,Cookie>();
		
		@Override
		public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
			for(Cookie c : cookies) {
				storedCookies.put(c.name(), c);
			}
		}

		@Override
		public List<Cookie> loadForRequest(HttpUrl url) {
			List<Cookie> results = new ArrayList<Cookie>();
			for(Cookie c : storedCookies.values()) {
				if(c.expiresAt() < System.currentTimeMillis()) {
					continue;
				}
				results.add(c);
			}
			return results;
		}
		
	}
}
