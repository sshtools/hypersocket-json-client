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

public class ImgField extends InputField {

	private String url;
	private String alt;
	private Integer width;
	private String _class;
	
	public ImgField() {
	}

	public ImgField(String resourceKey, String defaultValue) {
		super(InputFieldType.img, resourceKey, defaultValue, true, null);
	}
	
	public ImgField(String resourceKey, String defaultValue, String url) {
		this(resourceKey, defaultValue, url, null, null, null);
	}
	
	public ImgField(String resourceKey, String defaultValue, String url, String alt, Integer width) {
		this(resourceKey, defaultValue, url, alt, width, null);
	}
	
	public ImgField(String resourceKey, String defaultValue, String url, String alt, Integer width, String _class) {
		super(InputFieldType.img, resourceKey, defaultValue, true, null);
		this.url = url;
		this.alt = alt;
		this.width = width;
		this._class = _class;
	}
	
	public String getAlt() {
		return alt;
	}
	
	public String getUrl() {
		return url;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getStyleClass() {
		return _class;
	}
}
