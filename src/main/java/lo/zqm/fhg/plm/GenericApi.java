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

		 switch (type) {
		 
         case GET:
             return executeGet(url, responseType, queryParams);
         case POST:
             return executePost(url, responseType, params);
         case PATCH:
             return executePatch(url, responseType, params);
         case PUT:
             return executePut(url, responseType, params);
         case DELETE:
             executeDelete(url, queryParams);
             return null;
             
		default:
			throw new IllegalArgumentException("deu ruim" + type);
		}

	}
    private <T> T executeGet(String url, Class<T> responseType, Map<String, Object> queryParams) {
//    	URI uri = buildUriWithParams(url, queryParams);
    	final String url1 = String.format(url, queryParams);
        return restTemplate.getForObject(url1, responseType);
    }	
    
//    Map<String, Object> queryParams = new HashMap<>();
//    queryParams.put("page", 1);
//    queryParams.put("size", 10);
    
    
    private <T> T executePost(String url, Class<T> responseType, Object... params) {
		HttpEntity<Object> request = new HttpEntity<>(params[0]);
		ResponseEntity<T> response = restTemplate.postForEntity(url, request, responseType);
		return response.getBody();
	}

    private <T> T executePut(String url, Class<T> responseType, Object... params) {
		HttpEntity<Object> request = new HttpEntity<>(params[0]);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, request, responseType);
		return response.getBody();
	}

	private <T> T executePatch(String url, Class<T> responseType, Object... params) {
		HttpEntity<Object> request = new HttpEntity<>(params[0]);
		ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PATCH, request, responseType);
		return response.getBody();
	}

	private void executeDelete(String url, Map<String, Object> queryParams) {
        URI uri = buildUriWithParams(url, queryParams);
        restTemplate.delete(uri);
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

	        // GET All (List)
	        String listUrl2= "URL_ESPECIFICA";
	        Map<String, Object> listParams = Map.of("page?", 1, "size?", 10);
	        System.out.println("GET All: " + genericApi.genericRequest(listUrl2, RequestType.GET, String.class, true, listParams));
	        
	        
	        // GET By ID
	        String getByIdUrl = "URL_ESPECIFICA_PARA_GET_BY_ID";
	        Map<String, Object> getByIdParams = Map.of("expand", "details");
	        															//url 		tipo de request   responseClass éLista?  PAthPArams
	        System.out.println("GET by ID: " + genericApi.genericRequest(getByIdUrl, RequestType.GET, String.class, false, getByIdParams));

	        
	        // PUT Update enviando body)
	        String putUrl = "URL_ESPECIFICA_PARA_PUT";
	        Map<String, Object> putByIdParams = Map.of("expand", "details");
	        LegumesDto legumesToUpdate = new LegumesDto();
	        legumesToUpdate.setName("Updated Name");
	        System.out.println("PUT Update: " + genericApi.genericRequest(putUrl, RequestType.PUT, LegumesResponseDto.class, false, null, legumesToUpdate, null));

	        // DELETE
	        String deleteUrl = "URL_ESPECIFICA_PARA_DELETE";
	        Map<String, Object> deleteParams = Map.of("confirm", "true");
	        genericApi.genericRequest(deleteUrl, RequestType.DELETE, null, false, deleteParams);
	        System.out.println("DELETE completed");
	    }
}