package Client;

import org.w3c.dom.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Client.LoginController;
public class DBUtils {
public static void changeScene (ActionEvent event, String fxmlFile, String title, String username,String gender,String fullname,String email,String phonenumber) {
	Parent root= null;
	if(username!=null && gender!=null) {
		try {
			FXMLLoader loader =new FXMLLoader(DBUtils.class.getResource(fxmlFile));
		    root = loader.load();
		   
		}catch(IOException e) {
			e.printStackTrace();
		}
	}else {
		try {
			root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
	stage.setTitle(title);
	stage.setScene(new Scene (root,600,400));
	stage.show();
}
private static ArrayList<User> users = new ArrayList<>();
public static void signUpUser(ActionEvent event, String username,String password,String gender,String fullname,String email,String phonenumber) throws ClassNotFoundException {
	Connection connection=null;
	PreparedStatement psInsert= null;
	PreparedStatement psCheckUserExists=null;
	ResultSet resultSet = null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		//connection parameters
		final String url = "jdbc:mysql://localhost/dardachetv4.9";
		final String user = "root";
		final String passwordd = "admin";
		
		
		
		// establish the connection
		 connection = DriverManager.getConnection(url, user, passwordd);
	     psCheckUserExists= connection.prepareStatement("SELECT * FROM users WHERE username= ?");
	     psCheckUserExists.setString(1, username);
	     resultSet=psCheckUserExists.executeQuery();
	     if(resultSet.isBeforeFirst()) {
	    	 System.out.println("User already exists !");
	    	 Alert alert =new Alert(Alert.AlertType.ERROR);
	    	 alert.setContentText("You cannot use this username.");
	    	 alert.show();
	    	 
	     }else {
	    	 psInsert =connection.prepareStatement("INSERT INTO users (username,password,gender,fullname,email,phonenumber) VALUES (?,?,?,?,?,?)");
	    	 psInsert.setString(1, username);
	    	 psInsert.setString(2, password);
	    	 psInsert.setString(3, gender);
	    	 psInsert.setString(4, fullname);
	    	 psInsert.setString(5, email);
	    	 psInsert.setString(6, phonenumber);
	    	 psInsert.executeUpdate();
	    	// changeScene(event,"Loggedin.fxml","Welcome!",username,gender);
	    	// Create a new User object with the provided information
	         User newUser = new User(username, password, gender, fullname, email, phonenumber);

	         // Add the new user to the ArrayList
	         users.add(newUser);
	    	
	     }
	}catch(SQLException e) {
		e.printStackTrace();
	}finally {
		if(resultSet !=null) {
			try {
				resultSet.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if (psCheckUserExists != null) {
			try {
				psCheckUserExists.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if (psInsert != null) {
			try {
				psInsert.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection !=null) {
			try {
				connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
public static void saveUsersToXml(String filename) throws Exception {
    // Create a new DocumentBuilderFactory and DocumentBuilder
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // Create a new XML document with a root element called "users"
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("users");
    doc.appendChild(rootElement);
 // Create a new FileWriter for the text file
    File textFile = new File("users.txt");
    if (!textFile.exists()) {
        textFile.createNewFile();
    }
    FileWriter textWriter = new FileWriter(textFile,true);
    // Iterate over each user in the users ArrayList and create a new "user" element
    for (User user : users) {
        Element userElement = doc.createElement("user");

        // Set the attributes of the "user" element using the User object's properties
        userElement.setAttribute("username", user.getUsername());
        userElement.setAttribute("password", user.getPassword());
        userElement.setAttribute("gender", user.getGender());
        userElement.setAttribute("fullname", user.getFullname());
        userElement.setAttribute("email", user.getEmail());
        userElement.setAttribute("phonenumber", user.getPhonenumber());

        // Add the "user" element to the root element of the XML document
        rootElement.appendChild(userElement);
     // Write the user's information to the text file
        String userInfo = "username:" + user.getUsername() + ", password:" + user.getPassword() + ", gender:" + user.getGender() + ", fullname:" + user.getFullname() + ", email:" + user.getEmail() + ", phonenumber:" + user.getPhonenumber() + "\n";
        textWriter.write(userInfo);   
    
    }
 // Close the FileWriter for the text file
    textWriter.close();
    // Write the XML document to a file
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    FileWriter writer = new FileWriter(filename, true);
    StreamResult result = new StreamResult(writer);
    transformer.transform(source, result);
}



public static void logInUser(ActionEvent event, String username, String password) throws ClassNotFoundException {
	Connection connection =null;
	PreparedStatement preparedStatement=null;
	ResultSet resultSet=null;
	try {
      Class.forName("com.mysql.cj.jdbc.Driver");
		
		//connection parameters
		final String url = "jdbc:mysql://localhost/dardachetv4.9";
		final String user = "root";
		final String passwordd = "admin";
		
		// establish the connection
		 connection = DriverManager.getConnection(url, user, passwordd);
	     preparedStatement=connection.prepareStatement("SELECT password,gender FROM users WHERE username=?");
	     preparedStatement.setString(1, username);
	     resultSet=preparedStatement.executeQuery();
	     if(!resultSet.isBeforeFirst()) {
	    	 System.out.println("User not found in the database !");
	    	 Alert alert =new Alert(Alert.AlertType.ERROR);
	    	 alert.setContentText("User not found in the database, please sign up !");
	    	 alert.show();
	    	 
	     }else {
	    	 while(resultSet.next()) {
	    		 String retrievedPassword=resultSet.getString("password");
	    		 String retrievedChannel= resultSet.getString("gender");
	    	     if(retrievedPassword.equals(password)) {
	    	    	 changeScene(event,"Room.fxml",null,null,null,null,null,null);
	    	    	 
	    	     }else {
	    	    	 System.out.println("Password didn't match !");
	    	    	 Alert alert =new Alert(Alert.AlertType.ERROR);
	    	    	 alert.setContentText("Provided Password doesn't match !");
	    	    	 alert.show();
	    	     }
	    	 }
	    	 
	     }
	}catch(SQLException e) {
		e.printStackTrace();
	}finally {
		if(resultSet !=null) {
			try {
				resultSet.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(preparedStatement !=null) {
			try {
				preparedStatement.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection !=null) {
			try {
				connection.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
}
