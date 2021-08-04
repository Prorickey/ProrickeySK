package me.prorickey.addon.elements.data;

public class GetRequestData {
	
	private int code;
	private String body;
	
	public GetRequestData(int code, String body) {
		this.code = code;
		this.body = body;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getBody() {
		return body;
	}
	
}
