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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPrincipal {

	String principalName;
	String principalDesc;
	Long id;
	JsonPrincipalType type;
	
	List<JsonPrincipal> groups = new ArrayList<JsonPrincipal>();
	
	public JsonPrincipal() {
		
	}
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public String getPrincipalDesc() {
		return principalDesc;
	}
	public void setPrincipalDesc(String principalDesc) {
		this.principalDesc = principalDesc;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<JsonPrincipal> getGroups() {
		return groups;
	}
	public void setGroups(List<JsonPrincipal> groups) {
		this.groups = groups;
	}
	public void setType(JsonPrincipalType type) {
		this.type = type;
	}
	public JsonPrincipalType getType() {
		return type;
	}
	
	
}
