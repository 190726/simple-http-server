package com.sk.http.server;

public class HttpRequest {

	private String method;
	private String target;
	private String httpVersion;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTarget() {
		return target;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	@Override
	public String toString() {
		return "HttpRequest [method=" + method + ", target=" + target + ", httpVersion=" + httpVersion + "]";
	}
}
