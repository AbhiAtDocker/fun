package com.example.test.minio.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.minio.adapter.MinioAdapter;
import com.example.test.minio.model.BucketObject;

@RestController
public class MinioStorageController {
	
	@Autowired
	MinioAdapter minioAdapter;
	
	@GetMapping(path = "/files/{bucket}")
    public List<BucketObject> listFiles(@PathVariable String bucket) {
        return minioAdapter.getBucketObjects(bucket);
    }
	
	
	@GetMapping(path = "/readFile/{bucket}")
    public List<String> readFile(@PathVariable String bucket) {
        
		List<String> filesRead = new ArrayList<>(); 
		List<BucketObject> items =     minioAdapter.getBucketObjects(bucket);
		for(BucketObject item:items) {
			InputStream stream = minioAdapter.getFile(item.getObjectName(),bucket);
			minioAdapter.readFile(stream);
			filesRead.add(item.getObjectName());
		}
		return filesRead;
    }
	
	@GetMapping(path="/readFromUrl")
	public String readFileFromUrl() {
		String url = " https://minio-default-ets.censhare.com/hotfolder1/M3_To_PIM_20210902_115219-sya-test.csv?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220322T052108Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604800&X-Amz-Credential=W3HJV23GCE8PS9LK7118%2F20220322%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=ed095bbf8376410b56c4d073a6bb30b2fef7f487a9776fb3ca5e74f782e38358";
		String message = "file Read from url " + url;
		
		minioAdapter.readFromUrl(url);
		return message;
	}

}
