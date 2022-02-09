package com.hypersocket.json.input;

public class CountryField extends InputField {


	public CountryField(String resourceKey, String defaultValue, boolean required, String label) {
		super(InputFieldType.countries, resourceKey, defaultValue, required, label);
	}
}
