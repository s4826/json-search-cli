package cs622.hw2.indiegogo;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = IndiegogoJsonDeserializer.class)
public class IndiegogoObject {

	private String tableId;
	private String robotId;
	private String runId;
	private Map<String, String> data;
	
	public IndiegogoObject(String tableId, String robotId, String runId, Map<String, String> data) {
		this.tableId = tableId;
		this.robotId = robotId;
		this.runId = runId;
		this.data = data;
	}

	public String getTableId() {
		return tableId;
	}


	public void setTableId(String tableId) {
		this.tableId = tableId;
	}


	public String getRobotId() {
		return robotId;
	}


	public void setRobotId(String robotId) {
		this.robotId = robotId;
	}


	public String getRunId() {
		return runId;
	}


	public void setRunId(String runId) {
		this.runId = runId;
	}


	public Map<String, String> getData() {
		return data;
	}


	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	public static void main(String[] args) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		IndiegogoObject indie = mapper.readValue(new File("data/robots.json"), IndiegogoObject.class);
		Map<String, String> data = indie.getData();
		data.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		});
	}
}
