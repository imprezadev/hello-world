package com.smartware.common;

import lombok.Data;

public @Data class DBConfigParams {
	private String jdbcDriver;
	private String uri;
	private String userName;
	private String password;
}
