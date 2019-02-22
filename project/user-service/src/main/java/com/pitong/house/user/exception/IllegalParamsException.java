package com.pitong.house.user.exception;

// 分页参数异常
public class IllegalParamsException extends RuntimeException implements WithTypeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Type type; // 枚举属性
	
	public IllegalParamsException(Type type, String msg) {
		super(msg);
		this.type= type;
	}
	
	public Type type() {
		return type;
	}
	
	public enum Type{
		WRONG_PAGE_NUM, WORNG_TYPE
	}
}
