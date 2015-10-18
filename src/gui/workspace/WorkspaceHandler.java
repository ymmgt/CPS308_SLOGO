package gui.workspace;

import gui.init.ButtonFactory;
import gui.init.ColorPickerFactory;
import gui.init.ListViewFactory;
import gui.init.canvas.TurtleCanvas;
import gui.init.colorpicker.ColorChangeInterface;
import gui.init.textfield.CommandField;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Actions;
import model.SimpleActions;
import model.Turtle;

public class WorkspaceHandler implements ICreateWorkspace {
	private int WORKSPACE_NUMBER=0;
	private TabPane tabPane;
	private HBox topNav;
	private ButtonFactory buttonFactory;
	private ColorPickerFactory colorPickerFactory;
	private ListViewFactory listViewFactory;
	private String language;
	private ICreateWorkspace createWorkspaceInterface;

	public WorkspaceHandler(String lang){
		language = lang;
		tabPane = new TabPane();
		createWorkspaceInterface = this; 
		buttonFactory = new ButtonFactory(createWorkspaceInterface);
		createWorkspace();
	}
	/**
	 * @param tabPane
	 */
	@Override
	public void createWorkspace() {
		// Any object that changes between workspaces must be created new.
		// Factories must be redefined for new inputs. 
		
		Turtle turtle = new Turtle(null, WORKSPACE_NUMBER, WORKSPACE_NUMBER);
		Actions simpleActions = new SimpleActions(turtle);
		
		TurtleCanvas turtleCanvas = new TurtleCanvas();
		ColorChangeInterface colorChangeInterface = turtleCanvas;
		
		buttonFactory = new ButtonFactory(createWorkspaceInterface);
		colorPickerFactory = new ColorPickerFactory(colorChangeInterface);
		listViewFactory = new ListViewFactory();


		
		
		Tab tab = new Tab();
		tab.setText("Workspace " + String.valueOf(WORKSPACE_NUMBER+1));
		
		BorderPane borderPane = new BorderPane();
	
		Pane turtlePane = new Pane();
		turtleCanvas.widthProperty().bind(turtlePane.widthProperty());
		turtleCanvas.heightProperty().bind(turtlePane.heightProperty());
		
		turtlePane.getChildren().add(turtleCanvas);
		borderPane.setCenter(turtlePane);
		
		HBox navBar = createNavBar();
		TextField commandField = new CommandField(simpleActions, language);
		borderPane.setTop(navBar);
		borderPane.setBottom(commandField);
		
		Node historyView = listViewFactory.createObject("history_view");
		
		borderPane.setRight(historyView);

		tab.setContent(borderPane);
		tabPane.getTabs().add(tab);
		
		WORKSPACE_NUMBER++;
	}
	
	// Keep this method private to prevent ColorPickerFactory from being called if createWorkspace() is not run.
	private HBox createNavBar(){
		topNav = new HBox();
		Node[] navBarNodes = {colorPickerFactory.createObject("background_color"),
							  colorPickerFactory.createObject("pen_color"),
							  buttonFactory.createObject("turtle_image"),
							  buttonFactory.createObject("help"),
							  buttonFactory.createObject("reset_turtle"),
							  buttonFactory.createObject("open"),
							  buttonFactory.createObject("save"),
							  buttonFactory.createObject("grid"),
							  buttonFactory.createObject("add_workspace")
							  };
		topNav.getChildren().addAll(navBarNodes);
		return topNav;
	}
	
	public TabPane getTabPane(){
		return tabPane;
	}

}