package com.example.test.streamData;

import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class WriteStringToFile {
	
	
//	@RequestMapping(path = "/stream", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Mono<UploadResponse> uploadStream(
//            @RequestPart(value = "files", required = true) FilePart files, @RequestParam(value = "ttl", required = false) Integer ttl) {
//        return adapter.putObject(files);
//
//    }
//	

}
