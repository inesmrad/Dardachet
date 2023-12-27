package Client;
import Client.LoginController;
import javafx.scene.*;
import javafx.stage.Stage;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;
import Client.DBUtils;
public class Room extends Thread implements Initializable {
       private Stage stage;
       private Scene scene;
       private Parent root;
       @FXML
       private MenuItem loadMenuItem;
       @FXML
       private MenuItem colorMenuItem;
       @FXML
       private MenuItem resetMenuItem;
       @FXML
       private ColorPicker colorPicker;
      
       @FXML
	    private Pane chat;

	    @FXML
	    private Label clientName;

	    @FXML
	    private TextField msgField;

	    @FXML
	    private TextArea msgRoom;

	    @FXML
	    private TextField username;
	    @FXML
	    private MenuItem usersinfo;
	    @FXML
	    private Button history;
        private FileChooser fileChooser;
        private File filePath;
    

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    


    public void connectSocket() {
        try {
            socket = new Socket("localhost", 8889);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(username.getText() + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                msgRoom.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   

   

    public void handleSendEvent(MouseEvent event) {
        send();
        
    }


    public void send() {
        String msg = msgField.getText();
        writer.println(username.getText() + ": " + msg);
        msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgRoom.appendText("Me: " + msg + "\n");
        msgField.setText("");
        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
        
    }


    public boolean saveControl = false;

   

    public void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }
   
    
    
    @FXML
    void loadFile(ActionEvent event) throws FileNotFoundException {
        File file = new File("C:\\Users\\USER\\Desktop\\chat4.txt");
        try {
            Scanner myReader = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                sb.append(line).append("\n");
            }
            msgRoom.setText(sb.toString());
            myReader.close();
        } catch (IOException e) {
            System.err.println("IOException");
        }
    }
    @FXML
    void KeepTrackSignedUpUsers(ActionEvent event) {
        File file = new File("users.txt");
        try {
            Scanner myReader = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                sb.append(line).append("\n");
            }
            msgRoom.setText(sb.toString());
            myReader.close();
        } catch (IOException e) {
            System.err.println("IOException");
        }
    }
    @FXML
    void changeColor(ActionEvent event) throws InvalidColorException{
        Color color = colorPicker.getValue();
        msgRoom.setStyle("-fx-control-inner-background: " + toRGBCode(color) + ";");
    }

    private static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    @FXML
    void resetMsgRoom(ActionEvent event) {
        msgRoom.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        connectSocket();
    }
}