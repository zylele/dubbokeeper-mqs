package com.dubboclub.dk.remote.esb.util;

public interface MapContext {
	  /**
	   * 鍒ゆ柇瀵瑰簲Key鐨勫�兼槸鍚﹀瓨鍦�
	   * @param key
	   * @return
	   */
	  boolean containsValue(String key);


	  /**
	   * 鑾峰彇Object绫诲瀷鐨勫弬鏁板��
	   *
	   * @param key
	   *            鍙橀噺鍚�
	   * @return
	   */
	  Object getValue(String key);

	  /**
	   * 鑾峰彇String绫诲瀷鐨勫弬鏁板��
	   *
	   * @param key
	   *            鍙橀噺鍚�
	   * @return
	   */
	  String getValueString(String key);

	  /**
	   * 鑾峰彇int绫诲瀷鐨勫弬鏁板��
	   *
	   * @param key
	   *            鍙橀噺鍚�
	   * @return
	   */
	  int getValueInt(String key);

	  /**
	   * 鑾峰彇Integer绫诲瀷鐨勫��
	   * @param key
	   *  鍙橀噺鍚�
	   * @return
	   *  瀵瑰簲鐨勫彉閲忓�硷紝濡傛灉涓嶅瓨鍦ㄥ垯杩斿洖null
	   */
	  Integer getInteger(String key);

	  /**
	   * 鑾峰彇double绫诲瀷鐨勫弬鏁板��
	   *
	   * @param key
	   *            鍙橀噺鍚�
	   * @return
	   */
	  double getValueDouble(String key);

	  /**
	   * 鑾峰彇Double绫诲瀷鐨勫��
	   * @param key
	   *  鍙橀噺鍚�
	   * @return
	   *  瀵瑰簲鐨勫彉閲忓�硷紝濡傛灉涓嶅瓨鍦ㄥ垯杩斿洖null
	   */
	  Double getDouble(String key);

	  /**
	   * 鑾峰彇float绫诲瀷鐨勫弬鏁板��
	   *
	   * @param key
	   * @return
	   */
	  float getValueFloat(String key);

	  /**
	   * 鑾峰彇Float绫诲瀷鐨勫��
	   * @param key
	   *  鍙橀噺鍚�
	   * @return
	   *  瀵瑰簲鐨勫彉閲忓�硷紝濡傛灉涓嶅瓨鍦ㄥ垯杩斿洖null
	   */
	  Float getFloat(String key);


	  /**
	   * 鑾峰彇long绫诲瀷鐨勫弬鏁板��
	   *
	   * @param key
	   * @return
	   */
	  long getValueLong(String key);

	  /**
	   * 鑾峰彇Long绫诲瀷鐨勫��
	   * @param key
	   *  鍙橀噺鍚�
	   * @return
	   *  瀵瑰簲鐨勫彉閲忓�硷紝濡傛灉涓嶅瓨鍦ㄥ垯杩斿洖null
	   */
	  Long getLong(String key);

	  /**
	   * 鑾峰彇byte[]绫诲瀷鐨勫弬鏁板��
	   *
	   * @param key
	   * @return
	   */
	  byte[] getValueBytes(String key);

	  /**
	   * 閲嶆柊灏嗗彉閲忓瓨鏀惧埌鍙︿竴涓彉閲忎腑锛屼繚鐣欒捣鍏堢殑鍙橀噺涓��
	   * @param key
	   * @param renameKey
	   * @return
	   */
	  void renameValue(String key,String renameKey);

	  /**
	   * 璁剧疆鍙橀噺姹犲彉閲�
	   *
	   * @param key
	   *            灏嗚瀛樻斁鐨勫璞＄殑鍙橀噺鍚�
	   * @param value
	   *            灏嗚瀛樻斁鐨勫璞�
	   */
	  void setValue(String key, Object value);

	  /**
	   * 鍒犻櫎鍙橀噺姹犱腑鐨勫彉閲�
	   * @param key
	   * @return
	   * 鍙橀噺鍘熸湰鍦ㄥ彉閲忔睜涓殑鍊�
	   */
	  Object removeValue(String key);

	  /**
	   * 娓呯┖鍙橀噺姹犱腑鐨勫彉閲�
	   * @return
	   */
	  void clearValues();
	}

