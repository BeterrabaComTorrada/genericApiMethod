package lo.zqm.fhg.plm;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GenericApi extends urlConstantApi{

	@Autowired
	private final RestTemplate restTemplate = new RestTemplate();

	
	public enum RequestType{
		GET, POST, PATCH, PUT, DELETE;
	}
	

	public <T> T genericRequest(String url,
			RequestType type,// Response of API
			Class<T> responseType,
			boolean list,//éLista?
			Map<String, Object> queryParams,//pAthparams
			Object... params) {

		URI uri = buildUriWithParams(url, queryParams);
		HttpEntity<Object> requestEntity = (params != null && params.length > 0) ? new HttpEntity<>(params[0]) : null;
		ResponseEntity<T> response;

		switch (type) {
		
		case GET:
			response = restTemplate.exchange(uri, HttpMethod.GET, null, responseType);
			
			break;
			
		case POST:
			response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, responseType);
			
			break;
			
		case PUT:
			response = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, responseType);
			
			break;
			
		case PATCH:
			response = restTemplate.exchange(uri, HttpMethod.PATCH, requestEntity, responseType);
			
			break;
			
		case DELETE:
			restTemplate.delete(uri);
			
			return null;
			
		default:
			throw new IllegalArgumentException("Unsupported request type: " + type);
		}
		
		return response.getBody();
	}
	
//    private <T> T executeGet(String url, Class<T> responseType, Map<String, Object> queryParams) {
//    	URI uri = buildUriWithParams(url, queryParams);
//    	final String url1 = String.format(url, queryParams);
//        return restTemplate.getForObject(url1, responseType);
	
//    Map<String, Object> queryParams = new HashMap<>();
//    queryParams.put("page", 1);
//    queryParams.put("size", 10);
    
    
    private <T> T executeRequest(String url, HttpMethod method, Class<T> responseType, Object... params) {
        HttpEntity<Object> request = params.length > 0 ? new HttpEntity<>(params[0]) : null;
        ResponseEntity<T> response = restTemplate.exchange(url, method, request, responseType);
        return response.getBody();
    }

	
	private URI buildUriWithParams(String url, Map<String, Object> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        return builder.build().toUri();
    }
	
	

	   public static void main(String[] args) {
	        GenericApi genericApi = new GenericApi();

	        // Get all List
	        String listUrl2 = "URL_ESPECIFICA";
	        Map<String, Object> listParams = Map.of("page?", 1, "size?", 10);
	        System.out.println("GET All: " + genericApi.executeRequest(listUrl2, HttpMethod.GET, String.class, listParams));

	        // Getbid
	        String getByIdUrl = "URL_ESPECIFICA_PARA_GET_BY_ID";
	        Map<String, Object> getByIdParams = Map.of("expand", "details");
	        System.out.println("GET by ID: " + genericApi.executeRequest(getByIdUrl, HttpMethod.GET, String.class, getByIdParams));

	        // PUT Update enviando body
	        String putUrl = "URL_ESPECIFICA_PARA_PUT";
	        LegumesDto legumesToUpdate = new LegumesDto();
	        legumesToUpdate.setName("Updated Name");
	        System.out.println("PUT Update: " + genericApi.executeRequest(putUrl, HttpMethod.PUT, LegumesResponseDto.class, null, legumesToUpdate));

	        // DELETE
	        String deleteUrl = "URL_ESPECIFICA_PARA_DELETE";
	        Map<String, Object> deleteParams = Map.of("confirm", "true");
	        genericApi.executeRequest(deleteUrl, HttpMethod.DELETE, Void.class, deleteParams);
	        System.out.println("DELETE completed");
	    }
}