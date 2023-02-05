package cs622.hw2.cli.command;

public class HistoryQueryFactory implements CommandFactory {
	private SearchHistory historyTarget;
	
	public HistoryQueryFactory(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}
	
	
	public Command create() {
		HistoryQuery historyQuery = new HistoryQuery();
		historyQuery.setHistoryTarget(historyTarget);
		return historyQuery;
	}
}
