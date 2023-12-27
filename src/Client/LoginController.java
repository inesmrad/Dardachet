package Client;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{
	 @FXML
	    private Button button_login;

	    @FXML
	    private Button button_signup;

	    @FXML
	    private PasswordField tf_password;

	    @FXML
	    private TextField tf_username;
	  
	

		

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		button_login.setOnAction(new EventHandler <ActionEvent> () {
			public void handle (ActionEvent event) {
				try {
					DBUtils.logInUser(event,tf_username.getText(),tf_password.getText());
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		button_signup.setOnAction(new EventHandler <ActionEvent> () {
			public void handle (ActionEvent event) {
				DBUtils.changeScene(event,"signup.fxml","Sign up!",null,null,null,null,null);
			}
		});
	}

}
