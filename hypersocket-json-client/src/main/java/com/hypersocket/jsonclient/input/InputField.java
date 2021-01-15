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
package com.hypersocket.jsonclient.input;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "inputField")
public class InputField {

	private InputFieldType type;
	private String resourceKey;
	private String defaultValue;
	private boolean required;
	private String label;
	private List<Option> options = new ArrayList<Option>();
	private String infoKey;
	private String onChange;
	
	public InputField() {

	}

	protected InputField(InputFieldType type, String resourceKey,
			String defaultValue, boolean required, String label) {
		this.type = type;
		this.resourceKey = resourceKey;
		this.defaultValue = defaultValue;
		this.required = required;
		this.label = label;
	}

	protected InputField(InputFieldType type, String resourceKey,
						 String defaultValue, boolean required, String label, String infoKey) {
		this(type, resourceKey, defaultValue, required, label);
		this.infoKey = infoKey;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@XmlElement(name = "option")
	public List<Option> getOptions() {
		return options;
	}

	public void addOption(Option option) {
		options.add(option);
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public InputFieldType getType() {
		return type;
	}

	public void setType(InputFieldType type) {
		this.type = type;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getInfoKey() {
		return infoKey;
	}

	public void setInfoKey(String infoKey) {
		this.infoKey = infoKey;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onclick) {
		this.onChange = onclick;
	}
	
	
}
