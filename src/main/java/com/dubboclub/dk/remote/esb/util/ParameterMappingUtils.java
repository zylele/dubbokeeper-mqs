package com.dubboclub.dk.remote.esb.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.dubboclub.dk.remote.esb.base.EsbBaseReq;
import com.dubboclub.dk.remote.esb.base.EsbBaseRes;
import com.dubboclub.dk.remote.esb.dto.EsbReqAppHead;
import com.dubboclub.dk.remote.esb.dto.EsbReqSysHead;
import com.dubboclub.dk.remote.esb.dto.EsbResAppHead;
import com.dubboclub.dk.remote.esb.dto.EsbResSysHead;
import com.dubboclub.dk.remote.esb.dto.EsbResponse;
import com.google.common.base.Preconditions;

/**
 * mapper文件处理
 * 
 * @author chenweipu
 */
public class ParameterMappingUtils {
	private static Map<String, Method> sysMethods = new ConcurrentHashMap<>();
	private static Map<String, Method> appMethods = new ConcurrentHashMap<>();
	private static Map<String, Map<String, Method>> objMethods = new ConcurrentHashMap<>();

	/**
	 * 实体转Context报文
	 * 
	 * @param pojo
	 *            传入的实体
	 */
	public static SimpleRemoteCallContext objToContext(Object pojo) {
		SimpleRemoteCallContext context = new SimpleRemoteCallContext();
		getContextOrObj(pojo.getClass(), pojo, "get", context);
		return context;
	}

	/**
	 * Context报文转实体
	 * 
	 * @param context
	 *            变量池Context
	 * @param resType
	 *            实体的class
	 * @return 获取的实体
	 */
	public static <T> T contextToObj(SimpleRemoteCallContext context,
			Class<T> resType) {
		T obj;
		try {
			obj = resType.newInstance();
			getContextOrObj(resType, obj, "set", context);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("context to obj error: "
					+ e.getMessage(), e);
		}
		return obj;
	}

	private static void getContextOrObj(Class<?> pojoCla, Object pojo,
			String setOrGet, SimpleRemoteCallContext context) {
		Field[] fields = pojoCla.getDeclaredFields();
		try {
			for (Field field : fields) {
				String name = field.getName();
				if ("serialVersionUID".equals(name)) {
					continue;
				}
				String upperName = name.substring(0, 1).toUpperCase()
						+ name.substring(1);
				Object value = pojo.getClass().getMethod(setOrGet + upperName)
						.invoke(pojo);

				context.setValue(name, value);
			}
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException("obj To EsbJson error: "
					+ e.getMessage(), e);
		}

		if (pojoCla.getGenericSuperclass() != null) {
			getContextOrObj(pojoCla.getSuperclass(), pojo, setOrGet, context);
		}
	}

	/**
	 * 实体转esb的jsonStr报文
	 * 
	 * @param pojo
	 *            传入的实体
	 * @return jsonstr报文
	 */
	public static String objToEsbJson(Object pojo) {
		Preconditions.checkNotNull(pojo, "pojo is null");
		Map<String, Object> esbsend = new HashMap<>();
		EsbReqSysHead esbReqSysHead = new EsbReqSysHead();
		EsbReqAppHead esbReqAppHead = new EsbReqAppHead();
		Object body = null;
		try {
			body = pojo.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("obj To EsbJson error: "
					+ e.getMessage(), e);
		}
		esbsend.put("SYS_HEAD", esbReqSysHead);
		esbsend.put("APP_HEAD", esbReqAppHead);
		esbsend.put("BODY", body);

		getEsbSend(pojo.getClass(), pojo, esbReqSysHead, esbReqAppHead, body);

		return JsonUtils.convertObjectToJsonStr(esbsend);
	}

	private static void getEsbSend(Class<?> pojoCla, Object pojo,
			EsbReqSysHead esbReqSysHead, EsbReqAppHead esbReqAppHead,
			Object body) {
		Field[] fields = pojoCla.getDeclaredFields();
		try {
			if (!objMethods.containsKey(pojoCla.getName())) {
				setObjMethods(pojoCla);
			}
			Map<String, Method> pojoClaMethods = objMethods.get(pojoCla
					.getName());
			for (Field field : fields) {
				String name = field.getName();
				if ("serialVersionUID".equals(name)) {
					continue;
				}
				String upperName = name.substring(0, 1).toUpperCase()
						+ name.substring(1);
				Object value = pojoClaMethods.get("get" + upperName).invoke(
						pojo);
				if (value == null) {
					continue;
				}
				if (sysMethods.size() <= 0 || sysMethods.size() <= 0) {
					put(esbReqSysHead, esbReqAppHead);
				}
				if (sysMethods.containsKey("set" + upperName)) {
					sysMethods.get("set" + upperName).invoke(esbReqSysHead,
							value);
					continue;
				}
				if (appMethods.containsKey("set" + upperName)) {
					continue;
				}
				pojoClaMethods.get("set" + upperName).invoke(body, value);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException("obj To EsbJson error: "
					+ e.getMessage(), e);
		}

		if (pojoCla.getGenericSuperclass() != null) {
			getEsbSend(pojoCla.getSuperclass(), pojo, esbReqSysHead,
					esbReqAppHead, body);
		}
	}

	/**
	 * esb 报文的json Str 转换为实体对象
	 * 
	 * @param esbJson
	 *            esb的jsonstr
	 * @param resType
	 *            实体的class
	 * @param <T>
	 *            输出实体类型
	 * @return 获取的实体
	 */
	public static <T> T esbJsonToObj(String esbJson, Class<T> resType) {
		JSONObject jsonObject = JSONObject.parseObject(esbJson);
		String bodyJson = jsonObject.getString("BODY");
		jsonObject.remove("BODY");
		EsbResponse esbResponse = JsonUtils.convertJsonStrToObject(
				jsonObject.toJSONString(), EsbResponse.class);
		EsbResAppHead esbResAppHead = esbResponse.getAPP_HEAD();
		EsbResSysHead esbResSysHead = esbResponse.getSYS_HEAD();
		T res;
		if (bodyJson == null) {
			try {
				res = resType.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalStateException("obj To EsbJson error: "
						+ e.getMessage(), e);
			}
		} else {
			res = JsonUtils.convertJsonStrToObject(bodyJson, resType);
		}
		if (esbResAppHead == null) {
			esbResAppHead = new EsbResAppHead();
		}
		if (esbResSysHead == null) {
			esbResSysHead = new EsbResSysHead();
		}
		BeanUtils.copyProperties(esbResAppHead, res);
		BeanUtils.copyProperties(esbResSysHead, res);
		try {
			res.getClass().getMethod("setRet", List.class)
					.invoke(res, esbResSysHead.getRet());
			res.getClass().getMethod("setExtendArray", List.class)
					.invoke(res, esbResSysHead.getExtendArray());
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException("obj To EsbJson error: "
					+ e.getMessage(), e);
		}
		return res;
	}

	/**
	 * 实体转esb的jsonStr报文
	 * 
	 * @param pojo
	 *            传入的实体
	 * @return jsonstr报文
	 */
	public static String objToEsbJsonNew(Object pojo) {
		Preconditions.checkNotNull(pojo, "pojo is null");
		Map<String, Object> esbsend = new HashMap<>();
		EsbBaseReq esbBaseReq = (EsbBaseReq) pojo;
		esbsend.put("SYS_HEAD", esbBaseReq.getSysHead());
		esbsend.put("APP_HEAD", esbBaseReq.getAppHead());
		((EsbBaseReq) pojo).setAppHead(null);
		((EsbBaseReq) pojo).setSysHead(null);
		esbsend.put("BODY", pojo);
		String json =  JsonUtils.convertObjectToJsonStr(esbsend);
		return json;
	}
    public static String objToEsbJsonSjc(Object pojo) {
        Preconditions.checkNotNull(pojo, "pojo is null");
        Map<String, Object> esbsend = new HashMap<>();
        EsbBaseReq esbBaseReq = (EsbBaseReq) pojo;
        esbsend.put("SYS_HEAD", esbBaseReq.getSysHead());
        esbsend.put("APP_HEAD", esbBaseReq.getAppHead());
        ((EsbBaseReq) pojo).setAppHead(null);
        ((EsbBaseReq) pojo).setSysHead(null);
        esbsend.put("BODY", pojo);
        
        // 添加分页默认值，防止漏传分页值，导致后续系统返回数据暴增。默认分页数：20 by:liumintao 2018-03-12
        if (esbBaseReq.getAppHead() != null && 
                (StringUtils.isEmpty(esbBaseReq.getAppHead().getPageStart())
                        ||StringUtils.isEmpty(esbBaseReq.getAppHead().getPageEnd()))) {
            esbBaseReq.getAppHead().setPageStart("1");
            esbBaseReq.getAppHead().setPageEnd("20");
        }
        
        String json = JsonUtils.convertObjectToJsonStr(esbsend);
        return json;
    }

	public static <T> T esbJsonToObjNew(String esbJson, Class<T> resType) {
		JSONObject jsonObject = JSONObject.parseObject(esbJson);
		String bodyJson = jsonObject.getString("BODY");
		jsonObject.remove("BODY");
		EsbBaseRes esbResponse = JsonUtils.convertJsonStrToObject(
				jsonObject.toJSONString(), EsbBaseRes.class);
		T res;
		if (bodyJson == null) {
			try {
				res = resType.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalStateException("obj To EsbJson error: "
						+ e.getMessage(), e);
			}
		} else {
			res = JsonUtils.convertJsonStrToObject(bodyJson, resType);
		}
		if (EsbBaseRes.class.isAssignableFrom(resType)) {
			EsbBaseRes baseRes = (EsbBaseRes) res;
			baseRes.setAppHead(esbResponse.getAppHead());
			baseRes.setSysHead(esbResponse.getSysHead());
		}
		return res;
	}

	/**
	 * 将函数名转为主控函数名，遇到大写字母变为 _+对应小写
	 * 
	 * @param name
	 * @return
	 */
	private static String getParmNameToWs(String name) {
		StringBuffer sb = new StringBuffer();

		char[] chars = name.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (Character.isUpperCase(ch)) {
				sb.append("_" + Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	private static void put(EsbReqSysHead esbReqSysHead,
			EsbReqAppHead esbReqAppHead) {
		Method[] methods = esbReqSysHead.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				sysMethods.put(method.getName(), method);
			}
		}
		methods = esbReqAppHead.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				appMethods.put(method.getName(), method);
			}
		}
	}

	public static void setObjMethods(Class<?> pojoCla) {
		Method[] methods = pojoCla.getMethods();
		Map<String, Method> map = new ConcurrentHashMap<>();
		for (Method method : methods) {
			map.put(method.getName(), method);
		}
		objMethods.put(pojoCla.getName(), map);
	}
}