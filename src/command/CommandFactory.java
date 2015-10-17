package command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.Actions;
import parser.ParseFormatException;
import util.PropertyLoader;
/**
 * Must use the same instance to produce commands
 * Otherwise variable scope might not work 
 * 
 * @author Mike Ma (ym67)
 *
 */
public class CommandFactory {
	
	private final static String TURTLE_COMMAND = "TurtleCommand";
	private final static String TURTLE_QUERY = "TurtleQuery";
	private final static String MATH = "Math";
	private final static String BOOLEAN = "Boolean";
	private final static String CONTROL = "Control";
	private final static String DISPLAY = "Display";
	private final static String MULTIPLE = "Multiple";
	private final static String USER_DEFINED = "UserDefined";
	private final static String CLUSTER = "Cluster";
	
	
	private Actions myActions;
	private Map<String,Integer> myNumArgsRules;
	private Map<String,String> myCommandCatalog;
	private Map<String,Command> myUserDefined;
	private ControlCommands myControlCommands;
	
	public CommandFactory(Actions actions) throws IOException{
		myActions = actions;
		myUserDefined = new HashMap<>();
		myCommandCatalog = new HashMap<>();
		myNumArgsRules = new HashMap<>();
		myControlCommands = new ControlCommands();
		Properties prop = (new PropertyLoader()).load("Commands");
		prop.forEach((k,v)->{
			String[] s = v.toString().split(",");
			int numArgs = Integer.parseInt(s[0]);
			myNumArgsRules.put(k.toString(), numArgs<0?Integer.MAX_VALUE:numArgs);
			myCommandCatalog.put(k.toString(), s[1]);
		});
	}
	
	public Command getCommand(String name,List<Command> args) throws ParseFormatException{
		switch (myCommandCatalog.get(name)) {
		case TURTLE_COMMAND:
			return TurtleCommands.getCommand(myActions, name, args);
		case TURTLE_QUERY:
			return TurtleCommands.getQuery(myActions, name, args);
		case MATH:
			return MathCommands.get(name, args);
		case BOOLEAN:
			return BooleanCommands.get(name, args);
		case CONTROL:
			return myControlCommands.get(name, args);
		case DISPLAY:
			//TODO
			return null;
		case MULTIPLE:
			//TODO
			return null;
		case USER_DEFINED:
			//TODO
			return null;
		case CLUSTER:
			return new CommandList(args);
		default:
			throw new ParseFormatException(name+" does not exist!");
		}
	}
	
	public Command getConstant(double value){
		return (args)->{return value;};
	}
	
	public Command getVarable(String name){
		return myControlCommands.getVariable(name);
	}
	
	public Command getUserCommand(String name){
		//TODO
		return null;
	}
	
	public int getNumArgs(String name) throws ParseFormatException{
		if(!myNumArgsRules.containsKey(name))
			throw new ParseFormatException("\""+name +"\""+ " does not exist!");
		return myNumArgsRules.get(name);
	}
}
