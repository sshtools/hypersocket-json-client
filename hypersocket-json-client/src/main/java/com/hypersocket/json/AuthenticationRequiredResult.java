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

import com.hypersocket.input.FormTemplate;


public class AuthenticationRequiredResult extends AuthenticationResult {

	FormTemplate formTemplate;
	boolean lastErrorIsResourceKey;
	boolean isNew;
	boolean isFirst;
	boolean isLast;
	boolean lastResultSuccessful;
	boolean inPostAuthentication;
	String lastButtonResourceKey;
	JsonRealm realm;
	
	public AuthenticationRequiredResult() {

	}

	public FormTemplate getFormTemplate() {
		return formTemplate;
	}

	public void setFormTemplate(FormTemplate template) {
		this.formTemplate = template;
	}

	public boolean getLastErrorIsResourceKey() {
		return lastErrorIsResourceKey;
	}

	public boolean isLastResultSuccessfull() {
		return lastResultSuccessful;
	}

	public boolean isNewSession() {
		return isNew;
	}

	public boolean isFirst() {
		return isFirst;
	}
	
	public boolean isLast() {
		return isLast;
	}
	
	public boolean isPostAuthentication() {
		return inPostAuthentication;
	}
	
	public String getLastButtonResourceKey() {
		return lastButtonResourceKey;
	}

	public JsonRealm getRealm() {
		return realm;
	}

	@Override
	public String toString() {
		return "AuthenticationRequiredResult [formTemplate=" + formTemplate
				+ ", lastErrorIsResourceKey=" + lastErrorIsResourceKey
				+ ", isNew=" + isNew + ", isLast=" + isLast
				+ ", lastResultSuccessful=" + lastResultSuccessful
				+ ", bannerMsg=" + bannerMsg + ", errorMsg=" + errorMsg
				+ ", showLocales=" + showLocales + ", success=" + success
				+ ", version=" + version + ", principal=" + principal + "]";
	}

}
