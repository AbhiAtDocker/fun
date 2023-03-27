package com.example.test.minio.model;

public class BucketObject {
	
	private String objectName;
	private boolean isDir;
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public boolean isDir() {
		return isDir;
	}
	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}
	
	

}
