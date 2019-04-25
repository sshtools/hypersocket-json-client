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
package com.hypersocket.json.input;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormTemplate {

	protected String resourceKey;
	protected String scheme;
	protected List<InputField> fields = new ArrayList<InputField>();
	protected boolean showLogonButton = true;
	protected boolean showStartAgain = true;
	protected boolean overrideStartAgain = false;
	protected String logonButtonResourceKey = null;
	protected String logonButtonIcon = null;
	protected String formClass = null;
	
	public FormTemplate() {
	}
	
	public FormTemplate(String scheme) {
		this.scheme = scheme;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getScheme() {
		return scheme;
	}
	
	public List<InputField> getInputFields() {
		return fields;
	}

	public void setInputFields(List<InputField> fields) {
		this.fields = fields;
	}
	
	public boolean isShowLogonButton() {
		return showLogonButton;
	}
	
	public void setShowLogonButton(boolean showLogonButton) {
		this.showLogonButton = showLogonButton;
	}
	
	public String getFormClass() {
		return formClass;
	}
	
	public void setFormClass(String formClass) {
		this.formClass = formClass;
	}

	public String getLogonButtonResourceKey() {
		return logonButtonResourceKey;
	}

	public void setLogonButtonResourceKey(String logonButtonResourceKey) {
		this.logonButtonResourceKey = logonButtonResourceKey;
	}

	public String getLogonButtonIcon() {
		return logonButtonIcon;
	}

	public void setLogonButtonIcon(String logonButtonIcon) {
		this.logonButtonIcon = logonButtonIcon;
	}

	public boolean isShowStartAgain() {
		return showStartAgain;
	}

	public void setShowStartAgain(boolean showStartAgain) {
		this.showStartAgain = showStartAgain;
	}

	public void setOverrideStartAgain(boolean overrideStartAgain) {
		this.overrideStartAgain = overrideStartAgain;
	}
	
	public boolean isOverrideStartAgain() {
		return overrideStartAgain;
	}

}
