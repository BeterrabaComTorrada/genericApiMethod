//package lo.zqm.fhg.plm;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequestMapping("/c")
//@Slf4j
//public class qLove {
//	
//	private static final Logger logger = LoggerFactory.getLogger(qLove.class);
//	
//	
//	public int Selojs (int numberA) {
//		return numberA + 1;
//	}
//	
//	
//	
//	@PostMapping("/gyyyyyyyyyyytPin")
//	public String gyyyyyyyyyyytPin(@RequestBody Response wdioad) {
//		
//		String pin = wdioad.getPin();
//		String requestedPin = requestPin();
//		logger.info("getpin: {}", pin + "MAIZE" + requestedPin);
//		
//		return "MANUSEANDO COM CUIDADO E CALMA: ?o3ms9t3kbt" + pin + " " + requestedPin;
//		
//	}
//	
//	private String requestPin() {
//		RestTemplate restTemplate = new RestTemplate();
//		String url = "http://localhost:8082/c/generatePins";
//		Response pinrq = new Response();
//		pinrq.setPin("lol");
//		String pin = restTemplate.postForObject(url, null,String.class);
//		logger.info("requested Pin: {}", pin);
//		return pin;
//	}
//	
//	class Response {
//		private String pin;
//		public String getPin() {
//			return pin;
//		}
//		public void setPin(String pin) {
//			this.pin = pin;
//		}
//		
//	}
//}
