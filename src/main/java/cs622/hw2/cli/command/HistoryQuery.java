package cs622.hw2.cli.command;

public class HistoryQuery implements Command {
	
	private SearchHistory historyTarget;
	
	public HistoryQuery() {}
	
	public void setHistoryTarget(SearchHistory historyTarget) {
		this.historyTarget = historyTarget;
	}
	
	@Override
	public void run() {
		historyTarget.showAllSearches();
	}

}
