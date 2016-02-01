import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FileTransfer {
	
	
    public FileTransfer(Stage primaryStage, String style, String name, String IP){

    	Stage dialog = new Stage();    	
    	dialog.initStyle(StageStyle.UTILITY);
    	dialog.initModality(Modality.WINDOW_MODAL);
        
    	dialog.initOwner(primaryStage);
    	dialog.setResizable(false);
    	Pane panel = new Pane();
    	Scene scene = new Scene( panel , 300,200); 
    	

        scene.getStylesheets().add(GUI.class.getResource(style).toExternalForm());

        Text scenetitle = new Text("File transfer request");
        scenetitle.setId("welcome-text");     
        scenetitle.relocate(10, 10);
        panel.getChildren().add(scenetitle);
                
        Text txt = new Text("From "+name);
        txt.setId("welcome-text");     
        txt.relocate(10, 50);
        panel.getChildren().add(txt);
        
        Button accept = new Button("Accept");
        accept.setPrefWidth(85);
        accept.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){        		
        		new FileRecieve(IP);
        	}
        });


        accept.relocate(50, 150);
        panel.getChildren().add(accept);
        
        
        Button decline = new Button("decline");
        decline.setPrefWidth(85);
        decline.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent e){
        		dialog.close();

        		
        	}
        });


        decline.relocate(175, 150);
        panel.getChildren().add(decline);
        
    	dialog.setScene(scene);
    	
    	
    	dialog.show();
    }
}
