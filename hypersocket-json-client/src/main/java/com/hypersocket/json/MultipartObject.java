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
package com.hypersocket.json;

import java.io.File;

public class MultipartObject {
	String property;
	File   file;
	
	public MultipartObject(String property, File file) {
		super();
		this.property = property;
		this.file = file;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
