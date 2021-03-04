package com.dubboclub.dk.remote.common;


public enum StatusCode {
	
	IBS_SUCCESS  ("0000","交易成功"),
	SUCCESS      ("00000","交易成功"),
	ON_PROCESS   ("77777","交易处理中"),
	ACCEPTED     ("88888" ,"交易处理中"),
	FAILURE      ("99999", "交易失败"),
	ZF_PROCESSING("ZF8000" ,"交易处理中"),
	ZF_SUCCESS   ("ZF0000" ,"交易成功"),	
	CB_SUCCESS   ("000000" ,"交易成功");	
	
	  private String code;
	  private String desc;

	  StatusCode(String code, String desc) {
	    this.code = code;
	    this.desc = desc;
	  }

	  public String code() {
	    return code;
	  }

	  public String desc() {
	    return desc;
	  }
	  
	   
	  public final boolean equals(String code){
		  return code.equals(this.code);
	  }
}
