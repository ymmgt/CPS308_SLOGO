package command.math;

import command.Command;

public class Pi implements Command {

	@Override
	public double evaluate(Command... args) {
		return Math.PI;
	}

}