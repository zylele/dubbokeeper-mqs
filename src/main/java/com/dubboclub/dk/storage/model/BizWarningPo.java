package com.dubboclub.dk.storage.model;

import java.math.BigInteger;

/**  
* @ClassName: BizWarningPo  
* @Description:Biz_warning表实体   
* @author zhangpengfei  
* @date 2019年3月11日   
*/
public class BizWarningPo {
	private BigInteger id;
    private String traceId;
    private String traceContent;
    private String traceDt;
    private String error;
    private String txCode;
    
    
    public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceContent() {
        return traceContent;
    }

    public void setTraceContent(String traceContent) {
        this.traceContent = traceContent;
    }

    public String getTraceDt() {
        return traceDt;
    }

    public void setTraceDt(String traceDt) {
        this.traceDt = traceDt;
    }


}
