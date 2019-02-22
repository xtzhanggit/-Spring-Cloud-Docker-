package com.pitong.house.user.common;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.reflect.FieldUtils;

import com.google.common.collect.ImmutableMap;
import com.pitong.house.user.exception.IllegalParamsException;
import com.pitong.house.user.exception.UserException;
import com.pitong.house.user.exception.WithTypeException;

/*异常到异常码的映射*/

public class Exception2CodeRepo {

	// 异常、异常type到RestCode的映射
	private static final ImmutableMap<Object, RestCode> MAP = ImmutableMap.<Object, RestCode>builder()
			.put(IllegalParamsException.Type.WRONG_PAGE_NUM, RestCode.WRONG_PAGE) // 分页参数异常映射
			.put(IllegalStateException.class, RestCode.UNKNOWN_ERROR) // 其他异常映射
			.put(UserException.Type.USER_NOT_LOGIN, RestCode.TOKEN_INVALID)
			.put(UserException.Type.USER_NOT_FOUND, RestCode.USER_NOT_EXIST)
			.put(UserException.Type.USER_AUTH_FAIL, RestCode.USER_NOT_EXIST).build();

	// 读取异常的type字段
	public static Object getType(Throwable throwable) {
		try {
			return FieldUtils.readDeclaredField(throwable, "type", true);
		} catch (Exception e) {
			return null;
		}
	}

	public static RestCode getCode(Throwable throwable) {
		if (throwable == null)
			return RestCode.UNKNOWN_ERROR; // 程序bug
		Object target = throwable;
		if (throwable instanceof WithTypeException) { // 属于异常集合接口内的异常
			Object type = getType(throwable); // 得到异常type字段
			if (type != null)
				target = type;
		}
		RestCode restCode = MAP.get(target);
		if (restCode != null) {
			return restCode;
		}
		Throwable rootCause = ExceptionUtils.getRootCause(throwable); // 如果当前异常映射的RestCode为空，则继续追查根异常映射的RestCode
		if (rootCause != null) {
			return getCode(rootCause);
		}
		return RestCode.UNKNOWN_ERROR; // 如果所有层次的异常映射RestCode均为空，则返回未知RestCode
	}
}
