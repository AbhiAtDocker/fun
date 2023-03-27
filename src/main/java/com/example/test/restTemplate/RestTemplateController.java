package com.example.test.restTemplate;

import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestTemplateController {
	
	
	@PostMapping("/execute")
	public User executeRestTemplate(@RequestBody Input req) {
		
		return processInput(req);
		
	}

	
     private User processInput(Input t) {
		RestTemplate restTemplate =  new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		Request req = new Request("knative test", "rest template");

		HttpEntity<Request> request = new HttpEntity<Request>(req, headers);

		ResponseEntity<User> response = restTemplate.postForEntity( t.getUrl(), request , User.class );
		User user = response.getBody();
		return user;
	}

	
	
	public static class Request{
		private String name;
		private String job;
		
		public Request(String name, String job) {
			this.name = name;
			this.job = job;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getJob() {
			return job;
		}

		public void setJob(String job) {
			this.job = job;
		}
		
		
	}


	   public static class User{
		   private String  name;
		   private String job;
		   private Long id;
		   private Date createdAt;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		   
		   
		   
		   
		 
	   }


		public static class Input{
			private String url;
			private String method;
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			public String getMethod() {
				return method;
			}
			public void setMethod(String method) {
				this.method = method;
			}
			
		}


}
