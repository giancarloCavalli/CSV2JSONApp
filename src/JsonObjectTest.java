import static org.junit.Assert.*;

import org.junit.Test;

public class JsonObjectTest {

	@Test
	public void attributeNamesToString() {
		JsonObject jo1 = new JsonObject();
		jo1.setPair("Nome", "Gica");
		jo1.setPair("Idade", 23);
		jo1.setPair("Status", "Fit");
		assertEquals("Status, Idade, Nome", jo1.attributeNamesToString());
	}
	
	@Test
	public void valuesToString() {
		JsonObject jo1 = new JsonObject();
		jo1.setPair("Nome", "Gica");
		jo1.setPair("Idade", 23);
		jo1.setPair("Status", "Fit");
		assertEquals("Fit, 23, Gica", jo1.valuesToString());
	}

}
