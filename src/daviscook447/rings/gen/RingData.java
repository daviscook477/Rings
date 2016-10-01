package daviscook447.rings.gen;

import java.util.HashMap;

public class RingData {

	private HashMap<String, String> data;
	
	public RingData(HashMap<String, String> data) {
		this.data = data;
	}
	
	public String valueOf(String param) {
		return data.get(param);
	}
	
}
