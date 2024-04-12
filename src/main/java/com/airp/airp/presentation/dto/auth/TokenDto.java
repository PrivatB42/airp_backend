package com.airp.airp.presentation.dto.auth;

import org.apache.commons.lang3.StringUtils;

import static java.util.List.of;

public class TokenDto {

	private static final String TYPE_TOKEN = "Bearer";

	private String accessToken;

	public TokenDto() {
	}

	public TokenDto(String accessToken) {
		this.accessToken = StringUtils.join(of(TYPE_TOKEN, accessToken), " ");
	}

	public String getAccessToken() {
		return accessToken;
	}

}
