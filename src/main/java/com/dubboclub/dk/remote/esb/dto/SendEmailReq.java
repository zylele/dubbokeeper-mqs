package com.dubboclub.dk.remote.esb.dto;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.dubboclub.dk.remote.common.BizException;
import com.dubboclub.dk.remote.common.ErrCode;



/**
 * 邮件内容声明
 * 
 * @author liumintao
 *
 */
public class SendEmailReq {

	private String sceneCode;

	/**
	 * 业务类型<br/>
	 * exp:OutOpenAcc外出开户
	 */
	private String busType;

	/**
	 * 业务关键字及值<br/>
	 * Exp：{ent_no:00001818} Json格式，存储key,value键值对。
	 */
	private String busKey;

	private String subject;

	private List<String> mailTo;

	private Map<String, String> attachments;

	private String msg;

	public String getSceneCode() {
		return sceneCode;
	}

	public void setSceneCode(String sceneCode) {
		this.sceneCode = sceneCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<String> getMailTo() {
		return mailTo;
	}

	public void setMailTo(List<String> mailTo) {
		this.mailTo = mailTo;
	}

	public Map<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getBusKey() {
		return busKey;
	}

	public void setBusKey(String busKey) {
		this.busKey = busKey;
	}

	/**
	 * 组装邮件内容
	 * 
	 * @return
	 */
	public String getContent() {

		// "{\"subject\":\"邮件主题\",\"to\":[\"fantasyer@126.com\",\"zhouning@zgcbank.com\"],
		// \"attachments\":[{\"type\":\"PATH\",\"src_file_name\":\"测试_1234\",\"file_name\":
		// \"/share/email/logback.xml\"},{\"type\":\"PATH\",\"src_file_name\":\"property.txt\",
		// \"file_name\":\"/share/email/config.properties\"}],\"inline\":[{\"type\":\"PATH\",\"file_name\":
		// \"/share/email/image/test.png\"}],\"msg\":\"AAA<img
		// src=\\\"cid:test.png\\\" ></img> \"}"
		if (subject == null || subject == "") {
			throw new BizException(ErrCode.BS001, "邮件主旨不能为空");
		}
		String to = "";
		if (mailTo != null) {
			for (String m : mailTo) {
				if (m != null) {
					to += m + "\",\"";
				}
			}
		}
		if (to != null && to != "" && to.endsWith(",\"")) {
			to = to.substring(0, to.length() - 3);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{\"subject\":\"");
		sb.append(getSubject());
		sb.append("\",\"to\":[\"");
		sb.append(to);
		sb.append("\"],");
		if (attachments != null && attachments.size() > 0) {
			sb.append("\"attachments\":[");

			Map<String, String> resultMap = sortMapByKey(attachments); // 按Key进行排序
			for (Entry<String, String> set : resultMap.entrySet()) {
				String key = set.getKey();
				String val = set.getValue();
				if (key == null || key == "") {
					System.out.println("文件地址为空。");
				} else {
					sb.append("{\"type\":\"PATH\",\"file_name\":\"");
					sb.append(key);
					if (val == null || val == "") {
						System.out.println("文件重命名称为空。");
					} else {
						sb.append("\",\"src_file_name\":\"");
						sb.append(val);
					}
					sb.append("\"},");
				}
			}
			sb.append("],");
		}
		sb.append("\"msg\":\"");
		sb.append(getMsg());
		sb.append("\"}");

		String ret = sb.toString();
		return ret.replace("},]", "}]");
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}

	class MapKeyComparator implements Comparator<String> {

		@Override
		public int compare(String str1, String str2) {

			return str1.compareTo(str2);
		}
	}
}
