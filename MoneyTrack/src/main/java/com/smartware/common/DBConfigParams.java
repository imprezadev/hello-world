package com.smartware.common;

import lombok.Data;

public @Data class DBConfigParams {
	private String driver;
	private String uri;
	private String username;
	private String password;
}
