package grapher.ui;

import java.util.List;
import java.util.Optional;

import grapher.fc.FunctionFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;


public class Main extends Application {
	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		
		SplitPane splitP = new SplitPane();
		BorderPane borderP = new BorderPane();
		
		GrapherCanvas grapher = new GrapherCanvas(getParameters());
		
		Button add = new Button("+");
		Button remove = new Button("-");
		
		ObservableList<String> pl = FXCollections.observableArrayList();
		
		List<String> l = getParameters().getRaw();
		for(int i=0; i<l.size(); i++) {
			pl.add(l.get(i));
		}

		ToolBar toolBar = new ToolBar(add, remove);
		
		ListView<String> list = new ListView<String>(pl);
		list.getSelectionModel().getSelectedItems().addListener(ListListener(grapher,list));;
		
		borderP.setCenter(list);
		borderP.setBottom(toolBar);
		
		add.setOnAction(createEventHandlerP(grapher, pl));
		remove.setOnAction(createEventHandlerM(grapher, pl, list));
				
		splitP.getItems().add(borderP);
		splitP.getItems().add(grapher);
		
		root.setCenter(splitP);
		
		MenuBar menuB = new MenuBar();
		
		Menu expression = new Menu("Expression");
		
		MenuItem ajouter = new MenuItem("Ajouter");
		MenuItem edit = new MenuItem("Edit");
		MenuItem supprimer = new MenuItem("Supprimer");
		
		ajouter.setOnAction(createEventHandlerP(grapher, pl));
		edit.setOnAction(createEventHandlerE(grapher, pl, list));
		supprimer.setOnAction(createEventHandlerM(grapher, pl, list));

		expression.getItems().addAll(ajouter, edit, supprimer);

		menuB.getMenus().addAll(expression);
	
		ajouter.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		edit.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
		supprimer.setAccelerator(KeyCombination.keyCombination("Ctrl+Backspace"));		
		
		root.setTop(menuB);

		stage.setTitle("grapher");
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	private EventHandler<ActionEvent> createEventHandlerE(GrapherCanvas grapher, ObservableList<String> pl,
			ListView<String> list) {
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				list.getSelectionModel().getSelectedItem().replace(target, replacement);
			}
		};
		return handler;
	}

	private ListChangeListener<String> ListListener(GrapherCanvas grapher,ListView<String> l) {
		ListChangeListener<String> listener = new ListChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> arg0) {
				grapher.setSelectedFunction(l.getSelectionModel().getSelectedItems());
			}
		};
		return listener;
	}

	private EventHandler<ActionEvent> createEventHandlerP(GrapherCanvas grapher, ObservableList<String> pl) {
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				TextInputDialog TID = new TextInputDialog("Nouvelle fonction");
				TID.setContentText("Fonction");
				TID.setTitle("Ajouter une fonction");
				Optional<String> r = TID.showAndWait();
				r.ifPresent(fonction ->{
					grapher.addFunction(r.get());
					pl.add(r.get());
				});
			}
		};
		return handler;
	}

	private EventHandler<ActionEvent> createEventHandlerM(GrapherCanvas grapher, ObservableList<String> pl, ListView<String> l) {
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String s = l.getSelectionModel().getSelectedItem();
				pl.remove(s);
				grapher.removeFunction(s);
			}
		};
		return handler;
	}
	
	public void addFunction() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}