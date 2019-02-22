package com.pitong.house.user.common;

// 枚举类
public enum RestCode {
	OK(0, "OK"), UNKNOWN_ERROR(1, "用户服务异常"), TOKEN_INVALID(2, "TOKEN失效"), USER_NOT_FOUND(3, "用户未发现"), USER_NOT_EXIST(4,
			"用户不存在"), WRONG_PAGE(10100, "页码不存在");

	public final int code;
	public final String msg;

	// 构造函数必须是private
	private RestCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
