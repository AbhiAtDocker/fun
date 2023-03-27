package com.example.test.minio.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.minio.model.BucketObject;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;

@Service
public class MinioAdapter {

	@Autowired
    MinioClient minioClient;
	
	
	public InputStream getFile(String objectName, String bucket) {
		InputStream stream = null;
		
		try {
			 stream =
					 minioClient.getObject(
			              GetObjectArgs.builder().bucket(bucket).object(objectName).build());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stream;
	}
	
	
	
	
	public List<BucketObject> getBucketObjects(String bucket){
		
		List<BucketObject> bucketObjects =  new ArrayList<>();
	
		try { 
		Iterable<Result<Item>> results =
	            minioClient.listObjects(ListObjectsArgs.builder().bucket( bucket).build());
		
		for (Result<Item> result : results) {
	          Item item = result.get();
	          bucketObjects.add(populate(item));
	          System.out.println(item.lastModified() + "\t" + item.size() + "\t" + item.objectName());
	        }
		}catch(MinioException ex) {
			System.out.println("Error occurred: " + ex);
		}catch (Exception ex) {
			System.out.println("Error occurred: " + ex);
		}
		
		return bucketObjects;
	}
	
	
	
	

	
	
	
	
	
	

	
	
	
	private BucketObject populate(Item item) {
		BucketObject obj = new BucketObject();
		obj.setObjectName(item.objectName());
		obj.setDir(item.isDir());
		
		return obj;
	}
	
	
	public boolean readFile(InputStream in){
		boolean success = true;
		
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			//List<String> rows = new ArrayList<>();
			while ((line = br.readLine()) != null) {
			
					System.out.println(line);
				
				
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return success;
	}
	
	
	public boolean readFromUrl(String urlStr) {
		boolean success = true;
		 try {      
		URL url = new URL(urlStr);
		
		BufferedReader read = new BufferedReader(
		        new InputStreamReader(url.openStream()));

		        String i;
		        while ((i = read.readLine()) != null)
		            System.out.println(i);
		        read.close();
		 }catch(Exception ex) {
			 System.out.println(ex);
		 }
		
		return success;
	}
}
