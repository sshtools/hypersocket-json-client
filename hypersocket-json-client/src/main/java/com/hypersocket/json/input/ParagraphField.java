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
package com.hypersocket.json.input;

public class ParagraphField extends InputField {

	private boolean isValueResourceKey;
	private boolean isAlert;
	private String alertType = "danger";
	
	public ParagraphField(String defaultValue, boolean isValueResourceKey) {
		super(InputFieldType.p, "paragraphField", defaultValue, false, "");
		this.isValueResourceKey = isValueResourceKey;
	}
	
	public ParagraphField(String defaultValue, boolean isValueResourceKey, boolean isAlert, String alertType) {
		super(InputFieldType.p, "paragraphField", defaultValue, false, "");
		this.isValueResourceKey = isValueResourceKey;
		this.isAlert = isAlert;
		this.alertType = alertType;
	}
	
	public boolean isValueResourceKey() {
		return isValueResourceKey;
	}

	public boolean isAlert() {
		return isAlert;
	}

	public String getAlertType() {
		return alertType;
	}
	
	
}
