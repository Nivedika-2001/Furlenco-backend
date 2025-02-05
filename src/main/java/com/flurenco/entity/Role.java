package com.flurenco.entity;

public enum Role {

	 USER("USER"),
	 ADMIN("ADMIN");
	
	private final String defaultValue;

    Role(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}

