package com.dubboclub.dk.remote.common;

/**
 * Create on 2017/3/8.
 *
 * @author luo
 */
public class BizException extends RuntimeException {
  private static final long serialVersionUID = 1756368854408608348L;
  private String errCode;
  private String errMsg;
  private StatusCode statusCode;

  public BizException() {
  }

  public BizException(CodeDesc codeDesc) {
    super(codeDesc.desc());
    this.errCode = codeDesc.code();
    this.errMsg = codeDesc.desc();
  }
  
  public BizException(CodeDesc codeDesc,StatusCode statusCode) {
	    super(codeDesc.desc());
	    this.errCode = codeDesc.code();
	    this.errMsg = codeDesc.desc();
	    this.statusCode = statusCode;
  }
  
  public BizException(String errCode, String errMsg) {
	    super(errMsg);
	    this.errCode = errCode;
	    this.errMsg = errMsg;
  }
 
  public BizException(String errCode, String errMsg ,StatusCode statusCode) {
	    super(errMsg);
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	    this.statusCode = statusCode;
 }
  
  public BizException(String errCode, String errMsg, Throwable cause) {
	    super(errMsg + " : " + cause.getMessage(), cause);
	    this.errCode = errCode;
	    this.errMsg = errMsg;
  }
  
  public BizException(String errCode, String errMsg, StatusCode statusCode, Throwable cause) {
	    super(errMsg + " : " + cause.getMessage(), cause);
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	    this.statusCode = statusCode;
  }
  
  public BizException(CodeDesc codeDesc, Throwable cause) {
    this(codeDesc.code(), codeDesc.desc(), cause);
  }
  
  public BizException(CodeDesc codeDesc,StatusCode statusCode, Throwable cause) {
	    this(codeDesc.code(), codeDesc.desc(), statusCode , cause);
  }

  public BizException(CodeDesc codeDesc, String extMsg) {
	    this(codeDesc.code(), codeDesc.desc() + " " + extMsg);
  }
  
  public BizException(CodeDesc codeDesc,StatusCode statusCode, String extMsg) {
	    this(codeDesc.code(), codeDesc.desc() + " " + extMsg ,statusCode);
  }
  
  public BizException(CodeDesc codeDesc, String extMsg, Throwable cause) {
	    this(codeDesc.code(), codeDesc.desc() + " " + extMsg, cause);
  }
  
  public BizException(CodeDesc codeDesc, StatusCode statusCode,String extMsg, Throwable cause) {
	    this(codeDesc.code(), codeDesc.desc() + " " + extMsg,statusCode, cause);
  } 

  /**
   * 该类主要是为了在类序列化的过程中存储转换后的栈消息
   *
   * @param errCode 错误码
   * @param errMsg  错误描述
   * @param message 错误消息
   */
  public BizException(String errCode, String errMsg, String message) {
    super(message);
    this.errCode = errCode;
    this.errMsg = errMsg;
  }
  
  public BizException(String errCode, String errMsg, StatusCode statusCode, String message) {
	    super(message);
	    this.errCode = errCode;
	    this.errMsg = errMsg;
	    this.statusCode = statusCode;
  }

  public BizException(String errCode, String errMsg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	    super(errMsg, cause, enableSuppression, writableStackTrace);
	    this.errCode = errCode;
	    this.errMsg = errMsg;
  }
  
  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

	public StatusCode getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

  
}
