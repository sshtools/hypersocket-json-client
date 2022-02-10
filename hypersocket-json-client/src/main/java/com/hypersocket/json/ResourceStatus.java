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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name="resourceStatus")
public class ResourceStatus<T> implements Serializable {

	private boolean success = true;
	private String message = "";
	private T resource;
	
	/* TODO: These appear to be unused on the Java side, but I will
	 * leave them here as I have no idea if any of the Javascript
	 * side expects them to at least exist
	 */
	private boolean confirmation = false;
	private String[] options;
	private Object[] args;
	
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

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}
