package gui.init;

import java.util.Properties;

import gui.init.button.AddWorkspaceButton;
import gui.init.button.EnterCommandButton;
import gui.init.button.GridButton;
import gui.init.button.HelpButton;
import gui.init.button.OpenButton;
import gui.init.button.ResetTurtleButton;
import gui.init.button.SaveButton;
import gui.init.button.TurtleImageButton;
import gui.init.canvas.IReset;
import gui.turtle.IChangeImage;
import gui.workspace.ICreateWorkspace;
import gui.workspace.WorkspaceHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import parser.Parser;

public class ButtonFactory extends Factory{
	private ICreateWorkspace createWorkspace;
	private IChangeImage changeImage;
	private TextArea commandField;
	private IReset reset;
	private Parser parser;
	private String language;
	private Properties properties;
	public ButtonFactory(ICreateWorkspace createInterface, IChangeImage imageInterface, IReset resetInterface, TextArea field, Parser p, String lang, Properties prop){
		createWorkspace = createInterface;
		changeImage = imageInterface;
		reset = resetInterface;
		commandField = field;
		parser = p;
		language = lang;
		properties = prop;
	}
	@Override
	public Node createObject(String id) {
		switch(id){
		case "help_page"		   		: return new HelpButton(properties);
		case "reset_turtle"		: return new ResetTurtleButton(reset,properties);
		case "open"		   		: return new OpenButton(properties);
		case "save"		   		: return new SaveButton(properties);
		case "grid"		   		: return new GridButton(properties);
		case "change_turtle_image"		: return new TurtleImageButton(changeImage,properties);
		case "add_workspace"	: return new AddWorkspaceButton(createWorkspace,properties);
		case "enter_command"	: return new EnterCommandButton(commandField, parser, language,properties);

		default: 
		}
		return null;
}
}