package com.cliffe.flex.core.enums;

public enum StatusEnum {

    SUCCESS(200, "OK"),

    NOT_FOUND_SERVICE_PROVINDER(100001, "not found service provider");

    private Integer code;
    private String description;

    StatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}