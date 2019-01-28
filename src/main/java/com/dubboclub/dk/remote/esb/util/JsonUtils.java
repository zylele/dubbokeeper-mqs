package com.dubboclub.dk.remote.esb.util;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json报文处理
 *
 * @author chenweipu
 */
public class JsonUtils {

  private static ObjectMapper objectMapper = new ObjectMapper();
  static {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static <T> T convertJsonStrToObject(String jsonString, final JsonTypeReference<T> typeReference) throws IOException {
    return objectMapper.readValue(jsonString, new TypeReference<T>() {
      @Override
      public Type getType() {
        return typeReference.getType();
      }
    });
  }

  public static <T> T convertJsonStrToObject(String jsonString, Class<T> type) {
    try {
      return objectMapper.readValue(jsonString, type);
    } catch (IOException e) {
      throw new IllegalStateException("convert jsonStr to object error: " + e.getMessage(), e);
    }
  }

  public static String convertObjectToJsonStr(Object pojo) {
    try {
      return objectMapper.writeValueAsString(pojo);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("convert object to jsonStr error: " + e.getMessage(), e);
    }
  }
}
