package com.dubboclub.dk.remote.esb.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * Create on 2017/4/7.
 *
 * @author luominggang
 */
public class SimpleRemoteCallContext implements RemoteCallContext {

  protected Map<String, Object> inputPool = new HashMap<>();
  protected Map<String, Object> outputPool = new HashMap<>();
  protected Map<String, Object> tradePool = new HashMap<>();

  @Override
  public Object getValue(String key) {
    return tradePool.get(key);
  }

  @Override
  public String getValueString(String key) {
    Object obj = getValue(key);
    if (obj != null)
      return obj.toString();
    return null;
  }

  @Override
  public int getValueInt(String key) {
    Object value = getValue(key);
    try {
      if (value instanceof Integer) {
        return ((Integer) value).intValue();
      }
      return Integer.parseInt(value.toString());
    } catch (Exception e) {
      throw getException("int", key, String.valueOf(value), null);
    }
  }

  @Override
  public Integer getInteger(String key) {
    Object v = getValue(key);
    if (v == null) {
      return null;
    }
    if (v instanceof Integer) {
      return (Integer) v;
    }
    return Integer.valueOf(v.toString());
  }

  private IllegalArgumentException getException(String type, String key,
                                                String value, String msg) {

    String str = "Args[" + type + "]:" + key + ": " + value;
    if (!StringUtils.isEmpty(msg)) {
      str += msg;
    } else {
      str += ",Type conversion failed";
    }
    return new IllegalArgumentException(str);
  }

  @Override
  public double getValueDouble(String key) {
    Object value = getValue(key);
    try {
      if (value instanceof Double) {
        return ((Double) value).doubleValue();
      }
      return Double.parseDouble(value.toString());
    } catch (Exception e) {
      throw getException("double", key, String.valueOf(value), null);
    }
  }

  @Override
  public Double getDouble(String key) {
    Object v = getValue(key);
    if (v == null) {
      return null;
    }
    if (v instanceof Double) {
      return (Double) v;
    }
    return Double.valueOf(v.toString());
  }

  @Override
  public float getValueFloat(String key) {
    Object value = getValue(key);
    try {
      if (value instanceof Float) {
        return ((Float) value).floatValue();
      }
      return Float.parseFloat(value.toString());
    } catch (Exception e) {
      throw getException("float", key, String.valueOf(value), null);
    }
  }

  @Override
  public Float getFloat(String key) {
    Object v = getValue(key);
    if (v == null) {
      return null;
    }
    if (v instanceof Float) {
      return (Float) v;
    }
    return Float.valueOf(v.toString());
  }

  @Override
  public long getValueLong(String key) {
    Object value = getValue(key);
    try {
      if (value instanceof Long) {
        return ((Long) value).longValue();
      }
      return Long.parseLong(value.toString());
    } catch (Exception e) {
      throw getException("long", key, String.valueOf(value), null);
    }
  }

  @Override
  public Long getLong(String key) {
    Object v = getValue(key);
    if (v == null) {
      return null;
    }
    if (v instanceof Long) {
      return (Long) v;
    }
    return Long.valueOf(v.toString());
  }

  @Override
  public byte[] getValueBytes(String key) {
    if (containsValue(key)) {
      try {
        return (byte[]) getValue(key);
      } catch (Exception e) {
        throw new IllegalArgumentException("Args[byte[]]:" + key + ",is not found" + e.getMessage());
      }
    }
    return null;
  }

  @Override
  public void renameValue(String key, String renameKey) {
    Object value = getValue(key);
    setValue(renameKey, value);
    removeValue(key);
  }

  /**
   * 存入键值对
   */
  @Override
  public void setValue(String key, Object value) {
    tradePool.put(key, value);
  }

  /**
   * 判断对应key的值是否存在
   */
  @Override
  public boolean containsValue(String key) {
    return tradePool.containsKey(key);
  }

  @Override
  public Object removeValue(String key) {
    return tradePool.remove(key);
  }

  @Override
  public void clearValues() {
    tradePool.clear();
  }

  @Override
  public Object getInput(String key) {
    return inputPool.get(key);
  }

  @Override
  public Object getOutput(String key) {
    return outputPool.get(key);
  }

  @Override
  public void setInput(String key, Object value) {
    inputPool.put(key, value);
  }

  @Override
  public void setOutput(String key, Object value) {
    outputPool.put(key, value);
  }

  @Override
  public Object getInputAndTrade(String key) {
    Object value = getInput(key);
    if (value != null) {
      return value;
    }
    return getValue(key);
  }

  @Override
  public Object getOutputAndTrade(String key) {
    Object value = getOutput(key);
    if (value != null) {
      return value;
    }
    return getValue(key);
  }

  @Override
  public Object getFromAllScope(String key) {
    Object value = getValue(key);
    if (value != null) {
      return value;
    }
    value = getInput(key);
    if (value != null) {
      return value;
    }
    return getOutput(key);
  }

  @Override
  public void clearInput() {
    inputPool.clear();
  }

  @Override
  public void clearOutput() {
    outputPool.clear();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SimpleRemoteCallContext{");
    sb.append("inputPool=").append(inputPool);
    sb.append(", outputPool=").append(outputPool);
    sb.append(", tradePool=").append(tradePool);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public RemoteCallContext dump() {
    return new SimpleRemoteCallContext();
  }
}
