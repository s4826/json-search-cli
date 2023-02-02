package cs622.hw2.cli.command;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TestCommandProcessor {
	
	CommandProcessor proc;
	
	@BeforeEach
	void setUpEach() {
		proc = new CommandProcessor();
	}
	
}
