package com.pitong.house.user.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanHelper {

	private static final String updateTimeKey = "updateTime";

	private static final String createTimeKey = "createTime";

	/*遍历目标属性，将目标属性中String类型的的null值置为""，将目标属性中Number类型的null值置为0*/
	public static <T> void setDefaultProp(T target, Class<T> clazz) {
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz); // 获取目标属性列表
		for (PropertyDescriptor propertyDescriptor : descriptors) {
			String fieldName = propertyDescriptor.getName();
			Object value;
			try {
				value = PropertyUtils.getProperty(target, fieldName);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(
						"can not set property  when get for " + target + " and clazz " + clazz + " field " + fieldName);
			}
			if (String.class.isAssignableFrom(propertyDescriptor.getPropertyType()) && value == null) {
				try {
					PropertyUtils.setProperty(target, fieldName, "");
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new RuntimeException("can not set property when set for " + target + " and clazz " + clazz
							+ " field " + fieldName);
				}
			} else if (Number.class.isAssignableFrom(propertyDescriptor.getPropertyType()) && value == null) {
				try {
					BeanUtils.setProperty(target, fieldName, "0");
				} catch (Exception e) {
					throw new RuntimeException("can not set property when set for " + target + " and clazz " + clazz
							+ " field " + fieldName);
				}
			}
		}
	}

	/*更新“更新时间”字段*/
	public static <T> void onUpdate(T target) {
		try {
			PropertyUtils.setProperty(target, updateTimeKey, System.currentTimeMillis());
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			return;
		}
	}

	/*设置“创建时间”和“更新时间”字段*/
	public static <T> void onInsert(T target) {
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		try {
			PropertyUtils.setProperty(target, updateTimeKey, date);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

		}
		try {
			PropertyUtils.setProperty(target, createTimeKey, date);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

		}
	}

}
