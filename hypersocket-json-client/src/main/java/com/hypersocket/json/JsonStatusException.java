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
 * along with Hypersocket JSON Client.  If not, see http://www.gnu.org/licenses/.
 */
package com.hypersocket.json;

public class JsonStatusException extends Exception {

	private static final long serialVersionUID = 8359564351774025775L;
	int statusCode;
	
	public JsonStatusException(int statusCode) {
		super(selectErrorMessage(statusCode));
		this.statusCode = statusCode;
	}
	
	private static String selectErrorMessage(int statusCode) {
		switch(statusCode) {
		case 403:
			return "The user does not have permission to access the page requested.";
		case 404:
			return "The page could not be found.";
		default:
			return "The server returned HTTP status " + statusCode;
		}
	}

	public int getStatusCode() {
		return statusCode;
	}
}
