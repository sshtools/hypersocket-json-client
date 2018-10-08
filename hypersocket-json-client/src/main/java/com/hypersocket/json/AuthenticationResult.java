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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypersocket.json.version.HypersocketVersion;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AuthenticationResult {

	protected String bannerMsg;
	protected String errorMsg;
	protected boolean showLocales;
	protected boolean success;
	protected String version = HypersocketVersion.getVersion();
	protected JsonResource principal;
	
	public AuthenticationResult() {	
	}
	
	public AuthenticationResult(String bannerMsg, String errorMsg, boolean showLocales) {
		this.bannerMsg = bannerMsg;
		this.errorMsg = errorMsg;
		this.showLocales = showLocales;
	}

	public String getBannerMsg() {
		return bannerMsg;
	}

	public void setBannerMsg(String info) {
		this.bannerMsg = info;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getVersion() {
		return version;
	}	
	
	public boolean isShowLocales() {
		return showLocales;
	}

	public void setShowLocales(boolean showLocales) {
		this.showLocales = showLocales;
	}

	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
