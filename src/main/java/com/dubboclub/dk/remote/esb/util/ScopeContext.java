package com.dubboclub.dk.remote.esb.util;

public interface ScopeContext {
    /**
     * 浠庤緭鍏ユ睜涓幏鍙栧彉閲�
     * @param key
     * @return
     */
    Object getInput(String key);


    /**
     * 浠庤緭鍑烘睜涓幏鍙栧彉閲�
     * @param key
     * @return
     */
    Object getOutput(String key);

    /**
     * 鍚戣緭鍏ユ睜涓坊鍔犲彉閲�
     * @param key
     */
    void setInput(String key, Object value);

    /**
     * 鍚戣緭鍑烘睜涓坊鍔犲彉閲�
     * @param key
     * @param value
     */
    void setOutput(String key, Object value);

    /**
     * 浠庤緭鍏ュ拰浜ゆ槗姹犱腑鑾峰彇鍙橀噺
     * @param key
     * @return
     */
    Object getInputAndTrade(String key);

    /**
     * 浠庤緭鍑哄拰浜ゆ槗姹犱腑鑾峰彇鍙橀噺
     * @param key
     * @return
     */
    Object getOutputAndTrade(String key);

    /**
     * 浠庢墍鏈夌殑姹犱腑鑾峰彇鍙橀噺
     * @param key
     * @return
     */
    Object getFromAllScope(String key);

    /**
     * 娓呯┖杈撳叆姹�
     */
    void clearInput();

    /**
     * 娓呯┖杈撳嚭姹�
     */
    void clearOutput();
}

