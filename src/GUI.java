import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI extends Application {
	
	private TextArea chatField;
	private TextField msgField;
	private Client prog;
	ListView<String> list;
	Stage primaryStage;
	String Style = "Blue.css";
	
	private GUI ui;
	
	public static void main(String[] args) {
        launch(args);
    }
		
	
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    	ui = this;
        prog = new Client(ui); 
        
        primaryStage.setTitle("Chat Application");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setId("welcome-text");        
        grid.add(scenetitle, 0, 0, 2, 1);


        chatField = new  TextArea();
        chatField.setDisable(true);

        chatField.setId("chatField");
        grid.add(chatField, 1, 1,5,3);

        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(2);
        
        list = new ListView<String>();        
        ObservableList<String> items =FXCollections.observableArrayList();
        
        list.setItems(items);            
        list.setPrefWidth(75);
        
        vbox.getChildren().add(list);

        Button whisperB = new Button("Whisper");
        whisperB.setPrefWidth(85);
        whisperB.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		String selected = list.getSelectionModel().getSelectedItem();
        		sendWhisper(selected);
        	}
        });
        
        vbox.getChildren().add(whisperB);    
        
        Button fileB = new Button("Send File");
        fileB.setPrefWidth(85);
        fileB.setOnAction(new EventHandler<ActionEvent>(){
        	
        	
        	@Override
        	public void handle(ActionEvent e){
        		FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                	    new FileChooser.ExtensionFilter("All Images", "*.*"),
                	    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                	    new FileChooser.ExtensionFilter("GIF", "*.gif"),
                	    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                	    new FileChooser.ExtensionFilter("PNG", "*.png")
                	);
                File file = fileChooser.showOpenDialog(primaryStage);
               
                
        		String selected = list.getSelectionModel().getSelectedItem();
        		sendFile(selected, file);
        	}
        });
        
        vbox.getChildren().add(fileB);
        
        grid.add(vbox, 6, 1,1,3);
        
        
        msgField = new TextField();        
        grid.add(msgField, 1, 7,5,1);
        

        Button sendB = new Button("Send");
        sendB.setPrefWidth(85);
        sendB.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		sendmsg();
        	}
        });
        
        grid.add(sendB, 6, 7);

        msgField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent key)
            {
                if (key.getCode().equals(KeyCode.ENTER))
                {
                	sendmsg();
                }
            }
        });
        
        
        Scene scene = new Scene(grid, 750, 475);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(GUI.class.getResource(Style).toExternalForm());
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
            	prog.exit();
            	Platform.exit();
                System.exit(0);
            }
        });   
        
        prog.exec();
        
    }
    
    protected void sendFile(String selected, File file) {
    	new  Thread(new FileSend(file)).start();
    	prog.sendmsg("/File "+ selected +" "+ file.getName());
		
	}


    
    public void sendmsg(){
    	String s =  msgField.getText();
		prog.sendmsg(s);
		msgField.setText("");

    }
    
    public void sendWhisper(String name){
    	String s =  msgField.getText();

		prog.sendmsg("/w " +name +" "+s);
		msgField.setText("");

    }

	public void fileTransfer(String name, String IP, String fileName) {
		Platform.runLater(()-> {FileTransfer dialog = new FileTransfer(primaryStage,Style, name, IP, fileName);});
    	
    	
	}
	
    public void addmsg(String s){

    	System.out.println(s);
    	
		Platform.runLater(()-> {chatField.appendText(s+"\n");});

	}


	public void userList(String[] users) {
		System.err.println("called");
        ObservableList<String> items =FXCollections.observableArrayList();
		for(int i=3; i<users.length ; i++){
			items.add(users[i]);
		};
		Platform.runLater(()-> {list.setItems(items);});	
	}


}