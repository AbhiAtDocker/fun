package com.example.test.minio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.test.minio.adapter.MinioAdapter;
import com.example.test.minio.model.UploadResponse;

import reactor.core.publisher.Mono;
//@RestController
public class WriteStringToFile {
	
    @Autowired
    MinioAdapter adapter;
	
	
//	@RequestMapping(path = "/stream", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Mono<UploadResponse> uploadStream(
//            @RequestPart(value = "files", required = true) FilePart files, @RequestParam(value = "ttl", required = false) Integer ttl) {
//		String date = "test data";
//		WebClient client = WebClient.create();
//		
////		InputStream in = new BufferedInputStream(new FileInputStream(data));
////		webClient
////		   .put()
////		   .uri("")
////		   .header(HttpHeaders.CONTENT_TYPE, mimetype)
////		   .body(BodyInserters.fromResource(new InputStreamResource(in)))
////		   .retrieve()
////    }
//	
//}
	
	//create a microservice that takes a string, converts it into input stream, calls another url via web client to pass the input stream .
	
	
	//creat another microservice to be used by webclient from 1st microservice and take the input stream and save the file.
	
	
	
	
	
}