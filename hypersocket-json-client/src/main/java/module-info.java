module com.hypersocket.jsonclient {
	exports com.hypersocket.jsonclient;
	exports com.hypersocket.jsonclient.input;
	exports com.hypersocket.jsonclient.utils;
	exports com.hypersocket.jsonclient.version;
	requires transitive com.fasterxml.jackson.annotation;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive okhttp3;
	requires transitive okio;
	requires transitive java.xml;
	requires transitive java.prefs;
	requires org.apache.commons.lang3;
}