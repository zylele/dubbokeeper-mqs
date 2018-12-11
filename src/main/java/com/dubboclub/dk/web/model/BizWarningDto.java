package com.dubboclub.dk.web.model;

import java.math.BigInteger;

public class BizWarningDto {
	private BigInteger id;
    private String traceId;
    private String traceContent;
    private String traceDt;
    
    
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
