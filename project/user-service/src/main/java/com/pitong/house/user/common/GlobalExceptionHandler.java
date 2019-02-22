package com.pitong.house.user.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/*监测异常*/

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(HttpStatus.OK) // 返回200状态码
	@ExceptionHandler(value = Throwable.class) // 定义为处理异常的方法，throwable包括error和exception
	@ResponseBody // 将返回结果序列成json
	public RestResponse<Object> handler(HttpServletRequest req, Throwable throwable) {
		LOGGER.error(throwable.getMessage(), throwable); // 日志记录
		RestCode restCode = Exception2CodeRepo.getCode(throwable); // 异常到RestCode的映射
		RestResponse<Object> response = new RestResponse<Object>(restCode.code, restCode.msg); // 返回response
		return response;
	}
}
