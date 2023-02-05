package cs622.hw2.indiegogo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class IndiegogoJsonDeserializer extends StdDeserializer<IndiegogoObject> {

	public IndiegogoJsonDeserializer() {
		this(null);
	}
	
	
	protected IndiegogoJsonDeserializer(Class<?> vc) {
		super(vc);
	}


	@Override
	public IndiegogoObject deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JacksonException {
		JsonNode node = p.getCodec().readTree(p);
		String table_id = node.get("table_id").asText();
		String robot_id = node.get("robot_id").asText();
		String run_id = node.get("run_id").asText();

		JsonNode dataNode = node.findValue("data");
		JsonNode tagNode = dataNode.findValue("tags");
		
		Map<String, String> dataMap = new HashMap<>();

    	List<String> dataFields = new ArrayList<>();
    	dataNode.fieldNames().forEachRemaining(dataFields::add);
    	
    	dataFields.stream().filter(field -> !field.equals("tags")).forEach(field -> {
    		dataMap.put(field, dataNode.get(field).asText());
    	});
    	
    	String base = "tag";
    	int tagNum = 1;
    	for (JsonNode n : tagNode) {
    		dataMap.put(base+tagNum, n.asText());
    		tagNum++;
    	}

    	return new IndiegogoObject(table_id, robot_id, run_id, dataMap);
	}
}
