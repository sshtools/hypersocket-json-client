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

public class ResourceStatus<T> {

	boolean success = true;
	String message = "";
	T resource;
	boolean confirmation = false;
	String[] options;
	Object[] args;
	
	public ResourceStatus() {
		super();
	}

	public ResourceStatus(T resource, String message) {
		this.resource = resource;
		this.message = message;
	}
	
	public ResourceStatus(T resource) {
		this.resource = resource;
	}
	
	public ResourceStatus(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ResourceStatus(boolean success, T resource, String message) {
		this.success = success;
		this.resource = resource;
		this.message = message;
	}
	
	public ResourceStatus(boolean success) {
		this(success, null);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResource() {
		return resource;
	}

	public void setResource(T resource) {
		this.resource = resource;
	}
}
