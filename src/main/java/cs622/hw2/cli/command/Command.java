package cs622.hw2.cli.command;

import cs622.hw2.cli.command.history.SearchHistory;

public interface Command {
	void run();
	void setHistoryTarget(SearchHistory historyTarget);
}
