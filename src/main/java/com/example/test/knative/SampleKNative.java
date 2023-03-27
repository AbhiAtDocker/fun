package com.example.test.knative;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.test.knative.SampleKNative.Input;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SampleKNative
    implements Function<Message<Input>, com.example.test.knative.SampleKNative.Output> {

  /**
   * @Override public User apply(Message<Input> t) {
   *           System.out.println(t.getHeaders().get("authorization")); User user
   *           = processInput(t.getPayload(), t.getHeaders()); return user; }
   **/

  @Override
  public Output apply(Message<Input> t) {
    System.out.println(t.getHeaders().get("authorization"));
    Input input = t.getPayload();
    Output output = new Output();
    if ("GET".equalsIgnoreCase(input.getMethod())) {
      output = processInputHS(output, t.getPayload(), t.getHeaders());// Ingress URL of HotService
                                                                      // is not exposed.
    } else if ("MasterData".equalsIgnoreCase(input.getServiceName())) {
      output = processInputMDFV(output, t.getPayload(), t.getHeaders());
    } else {
      output = processInputMDFV(output, t.getPayload(), t.getHeaders());
    }
    return output;
  }

  /****
   * @param t
   * @param h
   * @return
   */

  private User processInput(Input t, MessageHeaders h) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth((String) h.get("authorization"));

    Request req = new Request("knative test", "rest template");
    // QSRequest req = new QSRequest("censhare:resource-key",
    // "clarins:m3.import.column-mapping");

    HttpEntity<Request> request = new HttpEntity<Request>(req, headers);
    // HttpEntity<QSRequest> request = new HttpEntity<QSRequest>(req, headers);

    ResponseEntity<User> response = restTemplate.postForEntity(t.getUrl(), request, User.class);
    // ResponseEntity<Result> response = restTemplate.postForEntity( t.getUrl(),
    // request , Result.class );
    User result = response.getBody();
    return result;
  }

  /***
   * @param t
   * @param h
   * @return
   */
  private Output processInputQS(Output output, Input t, MessageHeaders h) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String token = (String) h.get("authorization");
    if (token != null) {
      token = token.replace("Bearer", "").trim();
    }
    headers.setBearerAuth(token);

    // Request req = new Request("knative test", "rest template");
    QSRequest req = new QSRequest(
        new Condition("censhare:resource-key", "clarins:m3.import.column-mapping"));

    // HttpEntity<Request> request = new HttpEntity<Request>(req, headers);
    HttpEntity<QSRequest> request = new HttpEntity<QSRequest>(req, headers);

    ResponseEntity<Result> response = restTemplate.postForEntity(t.getUrl(), request,
        Result.class);
    Result result = response.getBody();
    output.setResult(result);

    return output;
  }

  /***
   * <p>
   * </p>
   * 
   * @param <V>
   * @param output
   * @param t
   * @param h
   * @return
   */
  private <V> Output processInputMDFV(Output output, Input t, MessageHeaders h) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String token = (String) h.get("authorization");
    if (token != null) {
      token = token.replace("Bearer", "").trim();
    }
    headers.setBearerAuth(token);

    // Request req = new Request("knative test", "rest template");
    Attributes attr = new Attributes("clarins:product.old_line", "FACE CARE", "test", "190001", 2, 0, 1);
    MDFVRequest<Attributes> req = new MDFVRequest<>(attr, "feature_value");
    ObjectMapper objM = new ObjectMapper();
    try {
      String json = objM.writeValueAsString(req);
      System.out.println("ResultingJSONstring = " + json);
      // System.out.println(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(req);
    // HttpEntity<Request> request = new HttpEntity<Request>(req, headers);
    HttpEntity<MDFVRequest<Attributes>> request = new HttpEntity<MDFVRequest<Attributes>>(req,
        headers);

    ResponseEntity<Output> response = restTemplate.postForEntity(t.getUrl(), request,
        Output.class);
    Output result = response.getBody();

    return result;
  }

  /****
   * @param t
   * @param h
   * @return
   */
  private Output processInputHS(Output output, Input t, MessageHeaders h) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String token = (String) h.get("authorization");
    if (token != null) {
      token = token.replace("Bearer", "").trim();
    }
    headers.setBearerAuth(token);

    // Request req = new Request("knative test", "rest template");
    String url = t.getUrl();

    // HttpEntity<Request> request = new HttpEntity<Request>(req, headers);
    HttpEntity<QSRequest> request = new HttpEntity<QSRequest>(headers);

    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request,
        String.class);
    System.out.println(response.toString());
    String result = response.getBody();
    output.setResponse(result);
    return output;
  }

  public static class QSRequest {
    Condition condition;

    public QSRequest(Condition condition) {
      this.condition = condition;
    }

    public Condition getCondition() {
      return condition;
    }

    public void setCondition(Condition condition) {
      this.condition = condition;
    }

  }

  public static class Condition {
    private String name;
    private String value;

    public Condition(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

  }

  public static class Request {
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

  public static class User {
    private String name;
    private String job;
    private Long id;
    private Date createdAt;
    private List<String> assetIds;

    public String getName() {
      return name;
    }

    public List<String> getAssetIds() {
      return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
      this.assetIds = assetIds;
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

  public static class Result {

    private List<String> assetIds;
    private String response;

    public String getResponse() {
      return response;
    }

    public void setResponse(String response) {
      this.response = response;
    }

    public List<String> getAssetIds() {
      return assetIds;
    }

    public void setAssetIds(List<String> assetIds) {
      this.assetIds = assetIds;
    }

  }

  public static class Input {
    private String url;
    private String method;
    private String serviceName;

    public String getServiceName() {
      return serviceName;
    }

    public void setServiceName(String serviceName) {
      this.serviceName = serviceName;
    }

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

  public static class Output {

    Result result;
    String response;
    String title;
    String detail;
    int status;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDetail() {
      return detail;
    }

    public void setDetail(String detail) {
      this.detail = detail;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public Result getResult() {
      return result;
    }

    public void setResult(Result result) {
      this.result = result;
    }

    public String getResponse() {
      return response;
    }

    public void setResponse(String response) {
      this.response = response;
    }

  }

  public interface XRequest {

  }

  /***
   * <p>
   * Request for master data feature value service.
   * </p>
   *
   * @author asc
   */
  public static class MDFVRequest<V> implements XRequest {
    private String table;
    private V attributes;

    public MDFVRequest(V attributes, String table) {
      this.table = table;
      this.attributes = attributes;
    }

    public String getTable() {
      return table;
    }

    public void setTable(String table) {
      this.table = table;
    }

    public V getAttributes() {
      return attributes;
    }

    public void setAttributes(V attributes) {
      this.attributes = attributes;
    }

    @Override
    public String toString() {
      return "MDFVRequest [table=" + table + ", attributes=" + attributes + "]";
    }

  }

  /***
   * <p>
   * Holds attributes for MDFVRequest.
   * </p>
   *
   * @author asc
   */
  public static class Attributes {
    private String feature;
    private String name;
    private String description;
    private String value_key;
    private Number isHierarchical;
    private Number enabled;
    private Number sorting;

    /***
     * <p>
     * constructor.
     * </p>
     *
     * @param feature
     *                        feature key
     * @param name
     *                        featureValue name
     * @param desc
     *                        featureValue desc
     * @param value_key
     *                        featureValue value key
     * @param sort
     *                        sort value
     * @param is_hierarchical
     *                        0-false, 1-true
     * @param enabled
     *                        0-false, 1-true
     */
    public Attributes(String feature, String name, String desc, String value_key, int sort,
        int is_hierarchical, int enabled) {
      super();
      this.feature = feature;
      this.name = name;
      this.description = desc;
      this.value_key = value_key;
      this.isHierarchical = is_hierarchical;
      this.enabled = enabled;
      this.sorting = sort;
    }

    public String getFeature() {
      return feature;
    }

    public void setFeature(String feature) {
      this.feature = feature;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getValue_key() {
      return value_key;
    }

    public void setValue_key(String value_key) {
      this.value_key = value_key;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    @JsonProperty("is_hierarchical")
    public Number getIsHierarchical() {
      return isHierarchical;
    }

    public void setIsHierarchical(Number isHierarchical) {
      this.isHierarchical = isHierarchical;
    }

    public Number getEnabled() {
      return enabled;
    }

    public void setEnabled(Number enabled) {
      this.enabled = enabled;
    }

    public Number getSorting() {
      return sorting;
    }

    public void setSorting(Number sorting) {
      this.sorting = sorting;
    }

    public Number isEnabled() {
      return enabled;
    }

    public void setEnabled(int enabled) {
      this.enabled = enabled;
    }

    @Override
    public String toString() {
      return "Attributes [feature=" + feature + ", name=" + name + ", desc=" + description
          + ", value_key=" + value_key + ", is_hierarchical=" + isHierarchical + ", enabled="
          + enabled + ", sorting=" + sorting + "]";
    }

  }

}
