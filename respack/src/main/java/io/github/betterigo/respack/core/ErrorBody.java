package io.github.betterigo.respack.core;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorBody {
	private int status;
	@JsonFormat(timezone="GMT+8")
	private Date timeStamp;
	private String message;
	private String path;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "ErrorBody [status=" + status + ", timestamp=" + timeStamp + ", message=" + message + ", path=" + path
				+ "]";
	}
}
