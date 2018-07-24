
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.TextArea;


public class Gateway implements Constants.Constants {
    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private TextArea textArea;

    // Establish the connection to the server.
    public Gateway(TextArea textArea) {
        this.textArea = textArea;
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }

    // Start the chat by sending in the user's handle.
    public void sendHandle(String handle) {
        outputToServer.println(SEND_HANDLE);
        outputToServer.println(handle);
        outputToServer.flush();
    }

    // Send a new comment to the server.
    public void sendInstructions(String[] instructions) {
        outputToServer.println(SEND_INSTRUCTIONS);
        outputToServer.println(instructions.length);
        for(String str : instructions)
            outputToServer.println(str);
        outputToServer.flush();
    }

    // Ask the server to send us a count of how many comments are
    // currently in the transcript.
    
    public int getRobotCount() {
        outputToServer.println(GET_ROBOT_COUNT);
        outputToServer.flush();
        int numberOfRobot = 0;
        try {
            numberOfRobot = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getCommentCount: " + ex.toString() + "\n"));
        }
        return numberOfRobot;
    }

    // Fetch comment n of the transcript from the server.
    public RobotPosition getNewPosition(int n) {
        outputToServer.println(GET_NEW_POSSITION);
        outputToServer.println(n);
        outputToServer.flush();
        RobotPosition p = new RobotPosition();
        try {
            p.setX(Integer.parseInt(inputFromServer.readLine()));
            p.setY( Integer.parseInt(inputFromServer.readLine()));
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getComment: " + ex.toString() + "\n"));
        }
        return p;
    }
    
    
    
    
}
