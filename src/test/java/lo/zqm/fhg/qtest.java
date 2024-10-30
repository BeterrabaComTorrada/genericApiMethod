package lo.zqm.fhg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lo.zqm.fhg.plm.qLove;


class qtest {

	@Test
	void test() {
		
		var qawd = new qLove(); 
		assertEquals(qawd.Selojs(2), 3);
		
	}

}
