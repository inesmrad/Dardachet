package Client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class SignupController implements Initializable {
	 @FXML
	    private Button button_login;

	    @FXML
	    private Button button_signup;

	    @FXML
	    private TextField email;

	    @FXML
	    private TextField fullname;
         @FXML
	    private RadioButton rb_female;

	    @FXML
	    private RadioButton rb_male;

	    @FXML
	    private TextField tf_password;

	    @FXML
	    private TextField tf_username;

	   

	    @FXML
	    private TextField phonenumber;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ToggleGroup toggleGroup =new ToggleGroup();
		rb_female.setToggleGroup(toggleGroup);
		rb_male.setToggleGroup(toggleGroup);
		
		rb_female.setSelected(true);
		 
		
		button_signup.setOnAction(new EventHandler <ActionEvent> () {
			public void handle (ActionEvent event) {
				String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
				if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !fullname.getText().trim().isEmpty() && !email.getText().trim().isEmpty() && !phonenumber.getText().trim().isEmpty() ) {
					try {
						DBUtils.signUpUser(event,tf_username.getText(),tf_password.getText(),toggleName,fullname.getText(),email.getText(),phonenumber.getText());
						DBUtils.saveUsersToXml("users.xml");
						
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					System.out.println("Please fill in all information!");
			    	 Alert alert =new Alert(Alert.AlertType.ERROR);
			    	 alert.setContentText("Please fill in all information to sign up !");
			    	 alert.show();
				}
			}
		});
		
		button_login.setOnAction(new EventHandler <ActionEvent> () {
			public void handle (ActionEvent event) {
				DBUtils.changeScene(event,"login.fxml","log in!",null,null,null,null,null);
			}
		});
	}

}
