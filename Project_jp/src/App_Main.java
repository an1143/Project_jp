
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App_Main extends Application{
	//EUC-KR
    public static Window wn;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("app_main.fxml"));
		Parent parent = fxmlLoader.load();
		Scene scene = new Scene(parent);
		
		wn = primaryStage;

		
		primaryStage.setTitle("Úª×µÎ·×â");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(true);
		
		

		
		
	}

}
