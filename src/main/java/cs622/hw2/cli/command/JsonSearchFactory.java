package cs622.hw2.cli.command;

public class JsonSearchFactory implements CommandFactory {
	
	private SearchHistory historyTarget;
	
	public JsonSearchFactory(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}
	
	public Command create() {
		JsonSearch jsonSearch = new JsonSearch();
		jsonSearch.setHistoryTarget(historyTarget);
		return jsonSearch;
	}
}
