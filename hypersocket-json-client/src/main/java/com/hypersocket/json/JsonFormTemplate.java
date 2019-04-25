package com.hypersocket.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypersocket.json.input.FormTemplate;

@JsonIgnoreProperties(ignoreUnknown=true)
public class JsonFormTemplate extends AuthenticationResult {

	FormTemplate formTemplate;
	
	public JsonFormTemplate() {
	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate formTemplate) {
		this.formTemplate = formTemplate;
	}

	
}
