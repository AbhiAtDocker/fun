package com.example.test.minio.model;



/***
 * 
 * @author asc
 *
 */

public class UploadResponse {


	    String id;
	    String objectName;
	    String bucket;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getObjectName() {
			return objectName;
		}
		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}
		public String getBucket() {
			return bucket;
		}
		public void setBucket(String bucket) {
			this.bucket = bucket;
		}
	    
	    


}
