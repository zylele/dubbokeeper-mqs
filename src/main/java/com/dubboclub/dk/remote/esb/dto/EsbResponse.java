package com.dubboclub.dk.remote.esb.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Create on 2017/5/9
 *
 * @author chenweipu
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class EsbResponse{
  @JsonProperty("SYS_HEAD")
  private EsbResSysHead SYS_HEAD;
  @JsonProperty("APP_HEAD")
  private EsbResAppHead APP_HEAD;

  public EsbResSysHead getSYS_HEAD() {
    return SYS_HEAD;
  }

  public void setSYS_HEAD(EsbResSysHead SYS_HEAD) {
    this.SYS_HEAD = SYS_HEAD;
  }

  public EsbResAppHead getAPP_HEAD() {
    return APP_HEAD;
  }

  public void setAPP_HEAD(EsbResAppHead APP_HEAD) {
    this.APP_HEAD = APP_HEAD;
  }

}
