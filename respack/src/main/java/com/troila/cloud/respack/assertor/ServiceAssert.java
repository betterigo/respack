package com.troila.cloud.respack.assertor;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.troila.cloud.respack.exception.BaseErrorException;

/**
 * <p>Title: ServiceAssert</p>  
 * <p>Description:业务断言工具类，用于处理业务异常。 </p>
 * @author haodonglei
 * @since 2020年6月17日
 */
public class ServiceAssert {
	
	public static final int DEFAULT_ERROR_CODE = 999;
	
	public static final String DEFAULT_ERROR_MSG = "undefined error msg";
	
	public static void state(boolean condition,ServiceErrorEnum serviceErrorEnum) {
		if(!condition) {
			throwException(serviceErrorEnum);
		}
	}
	/**
	 * <p>Title: isNull</p>
	 * <p>Description:如果obj为null，则抛出异常 </p>   
	 * @param obj
	 * @param serviceErrorEnum
	 */
	public static void isNull(Object obj,ServiceErrorEnum serviceErrorEnum) {
		if(obj == null) {
			throwException(serviceErrorEnum);
		}
	}
	
	/**
	 * <p>Title: isNull</p>
	 * <p>Description:如果obj为null，则抛出异常 </p>   
	 * @param obj
	 * @param serviceErrorEnum
	 */
	public static void isNull(Object obj,ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if(obj == null) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	/**
	 * <p>Title: isNull</p>
	 * <p>Description: 如果obj为null，则抛出异常</p>   
	 * @param obj
	 * @param serviceEnumSupplier
	 */
	public static void isNull(Object obj,Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if(obj == null) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	/**
	 * <p>Title: notNull</p>
	 * <p>Description: 如果obj不为null，则抛出异常</p>   
	 * @param obj
	 * @param serviceErrorEnum
	 */
	public static void notNull(Object obj,ServiceErrorEnum serviceErrorEnum) {
		if(obj != null) {
			throwException(serviceErrorEnum);
		}
	}
	
	
	/**
	 * <p>Title: notNull</p>
	 * <p>Description: 如果obj不为null，则抛出异常</p>   
	 * @param obj
	 * @param serviceErrorEnum
	 */
	public static void notNull(Object obj,ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if(obj != null) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	
	/**
	 * <p>Title: notNull</p>
	 * <p>Description: 如果obj不为null，则抛出异常</p>   
	 * @param obj
	 * @param serviceEnumSupplier
	 */
	public static void notNull(Object obj,Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if(obj != null) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	/**
	 * <p>Title: isTrue</p>
	 * <p>Description:断言是true，如果结果为false则抛出异常 </p>   
	 * @param expression
	 * @param serviceErrorEnum
	 */
	public static void isTrue(boolean expression,ServiceErrorEnum serviceErrorEnum) {
		if(!expression) {
			throwException(serviceErrorEnum);
		}
	}
	
	/**
	 * <p>Title: isTrue</p>
	 * <p>Description:断言是true，如果结果为false则抛出异常 </p>   
	 * @param expression
	 * @param serviceErrorEnum
	 */
	public static void isTrue(boolean expression,ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if(!expression) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	/**
	 * <p>Title: isTrue</p>
	 * <p>Description:断言是true，如果结果为false则抛出异常 </p>   
	 * @param expression
	 * @param serviceEnumSupplier
	 */
	public static void isTrue(boolean expression,Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if(!expression) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	/**
	 * <p>Title: hasLength</p>
	 * <p>Description: 如果字符串text为null或者长度为0则抛出异常</p>   
	 * @param text
	 * @param serviceErrorEnum
	 */
	public static void hasLength(@Nullable String text, ServiceErrorEnum serviceErrorEnum) {
		if (!StringUtils.hasLength(text)) {
			throwException(serviceErrorEnum);
		}
	}
	
	/**
	 * <p>Title: hasLength</p>
	 * <p>Description: 如果字符串text为null或者长度为0则抛出异常</p>   
	 * @param text
	 * @param serviceErrorEnum
	 */
	public static void hasLength(@Nullable String text, ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if (!StringUtils.hasLength(text)) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	/**
	 * <p>Title: hasLength</p>
	 * <p>Description: 如果字符串text为null或者长度为0则抛出异常</p>   
	 * @param text
	 * @param serviceEnumSupplier
	 */
	public static void hasLength(@Nullable String text, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (!StringUtils.hasLength(text)) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	public static void hasText(@Nullable String text, ServiceErrorEnum serviceErrorEnum) {
		if (!StringUtils.hasText(text)) {
			throwException(serviceErrorEnum);
		}
	}
	
	public static void hasText(@Nullable String text, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (!StringUtils.hasText(text)) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	public static void doesNotContain(@Nullable String textToSearch, String substring, ServiceErrorEnum serviceErrorEnum) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.contains(substring)) {
			throwException(serviceErrorEnum);
		}
	}
	
	public static void doesNotContain(@Nullable String textToSearch, String substring, ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.contains(substring)) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.contains(substring)) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	public static void notEmpty(@Nullable Object[] array, ServiceErrorEnum serviceErrorEnum) {
		if (ObjectUtils.isEmpty(array)) {
			throwException(serviceErrorEnum);
		}
	}
	
	public static void notEmpty(@Nullable Object[] array, ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if (ObjectUtils.isEmpty(array)) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	public static void notEmpty(@Nullable Object[] array, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (ObjectUtils.isEmpty(array)) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	public static void noNullElements(@Nullable Object[] array, ServiceErrorEnum serviceErrorEnum) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throwException(serviceErrorEnum);
				}
			}
		}
	}
	
	public static void noNullElements(@Nullable Object[] array, ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throwException(serviceErrorEnum,args);
				}
			}
		}
	}
	
	public static void noNullElements(@Nullable Object[] array, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throwException(nullSafeGet(serviceEnumSupplier));
				}
			}
		}
	}
	
	/**
	 * <p>Title: notEmpty</p>
	 * <p>Description:判断Collection不为空，如果为空则抛出异常 </p>   
	 * @param collection
	 * @param serviceErrorEnum
	 */
	public static void notEmpty(@Nullable Collection<?> collection, ServiceErrorEnum serviceErrorEnum) {
		if (CollectionUtils.isEmpty(collection)) {
			throwException(serviceErrorEnum);
		}
	}
	
	/**
	 * <p>Title: notEmpty</p>
	 * <p>Description:判断Collection不为空，如果为空则抛出异常 </p>   
	 * @param collection
	 * @param serviceErrorEnum
	 */
	public static void notEmpty(@Nullable Collection<?> collection, ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if (CollectionUtils.isEmpty(collection)) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	/**
	 * <p>Title: notEmpty</p>
	 * <p>Description: 判断Collection不为空，如果为空则抛出异常</p>   
	 * @param collection
	 * @param serviceEnumSupplier
	 */
	public static void notEmpty(@Nullable Collection<?> collection, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (CollectionUtils.isEmpty(collection)) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	public static void noNullElements(@Nullable Collection<?> collection, ServiceErrorEnum serviceErrorEnum) {
		if (collection != null) {
			for (Object element : collection) {
				if (element == null) {
					throwException(serviceErrorEnum);
				}
			}
		}
	}
	
	public static void noNullElements(@Nullable Collection<?> collection, ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if (collection != null) {
			for (Object element : collection) {
				if (element == null) {
					throwException(serviceErrorEnum,args);
				}
			}
		}
	}
	
	public static void noNullElements(@Nullable Collection<?> collection, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (collection != null) {
			for (Object element : collection) {
				if (element == null) {
					throwException(nullSafeGet(serviceEnumSupplier));
				}
			}
		}
	}
	
	public static void notEmpty(@Nullable Map<?, ?> map, ServiceErrorEnum serviceErrorEnum) {
		if (CollectionUtils.isEmpty(map)) {
			throwException(serviceErrorEnum);
		}
	}
	
	public static void notEmpty(@Nullable Map<?, ?> map, ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if (CollectionUtils.isEmpty(map)) {
			throwException(serviceErrorEnum,args);
		}
	}
	
	
	public static void notEmpty(@Nullable Map<?, ?> map, Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		if (CollectionUtils.isEmpty(map)) {
			throwException(nullSafeGet(serviceEnumSupplier));
		}
	}
	
	public static void isInstanceOf(Class<?> type, @Nullable Object obj, ServiceErrorEnum serviceErrorEnum) {
		notNull(type, ()->{
			return new ServiceErrorEnum() {
				@Override
				public String getMessage() {
					return "Type to check against must not be null";
				}
				@Override
				public int getErrorCode() {
					return DEFAULT_ERROR_CODE;
				}
			};
		});
		if (!type.isInstance(obj)) {
			instanceCheckFailed(type, obj, serviceErrorEnum);
		}
	}
	
	
	
	private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable ServiceErrorEnum serviceErrorEnum) {
		String className = (obj != null ? obj.getClass().getName() : "null");
		String result = "";
		boolean defaultMessage = true;
		String msg = "";
		if(serviceErrorEnum!=null) {
			msg = serviceErrorEnum.getMessage();
		}
		if (StringUtils.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, className);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + ("Object of class [" + className + "] must be an instance of " + type);
		}
		
		throwException(serviceErrorEnum != null?serviceErrorEnum.getErrorCode():DEFAULT_ERROR_CODE, result);
	}
	public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, ServiceErrorEnum serviceErrorEnum) {
		notNull(subType, ()->{
			return new ServiceErrorEnum() {
				@Override
				public String getMessage() {
					return "Type to check against must not be null";
				}
				@Override
				public int getErrorCode() {
					return DEFAULT_ERROR_CODE;
				}
			};
		});
		if (subType == null || !superType.isAssignableFrom(subType)) {
			assignableCheckFailed(superType, subType, serviceErrorEnum);
		}
	}
	private static void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable ServiceErrorEnum serviceErrorEnum) {
		String result = "";
		boolean defaultMessage = true;
		String msg = "";
		if(serviceErrorEnum!=null) {
			msg = serviceErrorEnum.getMessage();
		}
		if (StringUtils.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, subType);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + (subType + " is not assignable to " + superType);
		}
		throwException(serviceErrorEnum != null?serviceErrorEnum.getErrorCode():DEFAULT_ERROR_CODE, result);
	}
	
	private static boolean endsWithSeparator(String msg) {
		return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
	}

	private static String messageWithTypeName(String msg, @Nullable Object typeName) {
		return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
	}
	
	private static void throwException(ServiceErrorEnum serviceErrorEnum) {
		if(serviceErrorEnum!=null) {				
			throwException(serviceErrorEnum.getErrorCode(),serviceErrorEnum.getMessage());
		}else {
			throwDefaultException();
		}
	}
	
	private static void throwException(ServiceErrorEnum serviceErrorEnum,Object ...args) {
		if(serviceErrorEnum!=null) {
			throwException(serviceErrorEnum.getErrorCode(),AssertMessageFormatter.arrayFormat(serviceErrorEnum.getMessage(), args).getMessage());
		}else {
			throwDefaultException();
		}
	}
	
	private static void throwException(int errorCode,String msg) {
//		throw new RRException(msg, errorCode);
		throw new BaseErrorException(errorCode, msg);
	}
	private static void throwDefaultException() {
		throwException(DEFAULT_ERROR_CODE,DEFAULT_ERROR_MSG);
	}
	
	@Nullable
	private static ServiceErrorEnum nullSafeGet(@Nullable Supplier<ServiceErrorEnum> serviceEnumSupplier) {
		return (serviceEnumSupplier != null ? serviceEnumSupplier.get() : null);
	}

	/**
	 * int判断区间
	 * @param aValue
	 * @param aMinimum
	 * @param aMaximum
	 * @param serviceErrorEnum
	 */
	public static void assertArgumentRange(int aValue, int aMinimum, int aMaximum, ServiceErrorEnum serviceErrorEnum) {
		if (aValue < aMinimum || aValue > aMaximum) {
			throwException(serviceErrorEnum);
		}
	}

	/**
	 * 非空
	 * @param aString
	 * @param serviceErrorEnum
	 */
	public static void assertArgumentNotEmpty(String aString, ServiceErrorEnum serviceErrorEnum) {
		if (aString == null || aString.trim().isEmpty()) {
			throwException(serviceErrorEnum);
		}
	}
}
