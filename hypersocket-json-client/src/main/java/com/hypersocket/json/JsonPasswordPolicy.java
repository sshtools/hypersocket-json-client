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

public class JsonPasswordPolicy extends JsonResource {

	private static final long serialVersionUID = 1L;
	
	private Integer minimumLength;
	private Integer maximumLength;
	private Boolean containUsername;
	private Boolean containDictionaryWord;
	private Integer priority;
	private Integer minimumCriteriaMatches;
	private Integer minimumDigits;
	private Integer minimumLower;
	private Integer minimumUpper;
	private Integer minimumSymbol;
	private String validSymbols;
	private Integer passwordHistory;
	private Integer minimumAge;
	private Integer maximumAge;
	private Integer hashValue;
	private String dn;
	private String provider;
	private Boolean allowEdit;
	private Boolean defaultPolicy;
	
	public Integer getMinimumLength() {
		return minimumLength;
	}
	public void setMinimumLength(Integer minimumLength) {
		this.minimumLength = minimumLength;
	}
	public Integer getMaximumLength() {
		return maximumLength;
	}
	public void setMaximumLength(Integer maximumLength) {
		this.maximumLength = maximumLength;
	}
	public Boolean getContainUsername() {
		return containUsername;
	}
	public void setContainUsername(Boolean containUsername) {
		this.containUsername = containUsername;
	}
	public Boolean getContainDictionaryWord() {
		return containDictionaryWord;
	}
	public void setContainDictionaryWord(Boolean containDictionaryWord) {
		this.containDictionaryWord = containDictionaryWord;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getMinimumCriteriaMatches() {
		return minimumCriteriaMatches;
	}
	public void setMinimumCriteriaMatches(Integer minimumCriteriaMatches) {
		this.minimumCriteriaMatches = minimumCriteriaMatches;
	}
	public Integer getMinimumDigits() {
		return minimumDigits;
	}
	public void setMinimumDigits(Integer minimumDigits) {
		this.minimumDigits = minimumDigits;
	}
	public Integer getMinimumLower() {
		return minimumLower;
	}
	public void setMinimumLower(Integer minimumLower) {
		this.minimumLower = minimumLower;
	}
	public Integer getMinimumUpper() {
		return minimumUpper;
	}
	public void setMinimumUpper(Integer minimumUpper) {
		this.minimumUpper = minimumUpper;
	}
	public Integer getMinimumSymbol() {
		return minimumSymbol;
	}
	public void setMinimumSymbol(Integer minimumSymbol) {
		this.minimumSymbol = minimumSymbol;
	}
	public String getValidSymbols() {
		return validSymbols;
	}
	public void setValidSymbols(String validSymbols) {
		this.validSymbols = validSymbols;
	}
	public Integer getPasswordHistory() {
		return passwordHistory;
	}
	public void setPasswordHistory(Integer passwordHistory) {
		this.passwordHistory = passwordHistory;
	}
	public Integer getMinimumAge() {
		return minimumAge;
	}
	public void setMinimumAge(Integer minimumAge) {
		this.minimumAge = minimumAge;
	}
	public Integer getMaximumAge() {
		return maximumAge;
	}
	public void setMaximumAge(Integer maximumAge) {
		this.maximumAge = maximumAge;
	}
	public Integer getHashValue() {
		return hashValue;
	}
	public void setHashValue(Integer hashValue) {
		this.hashValue = hashValue;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public Boolean getAllowEdit() {
		return allowEdit;
	}
	public void setAllowEdit(Boolean allowEdit) {
		this.allowEdit = allowEdit;
	}
	public Boolean getDefaultPolicy() {
		return defaultPolicy;
	}
	public void setDefaultPolicy(Boolean defaultPolicy) {
		this.defaultPolicy = defaultPolicy;
	}

	
}
