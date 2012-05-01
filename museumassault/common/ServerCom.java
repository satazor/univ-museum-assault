package museumassault.common;

import java.io.*;
import java.net.*;


public class ServerCom {


    protected ServerSocket serverSocket = null;
    protected Socket clientSocket = null;
    protected int serverPort;


    protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;

    public ServerCom(int serverPort)
    {
        this.serverPort = serverPort;
    }


    public ServerCom(int serverPort, ServerSocket serverSocket) {
    	this.serverPort = serverPort;
        this.serverSocket = serverSocket;
    }



    public void start() {
        try {
            serverSocket = new ServerSocket(serverPort, 1);
        }
        catch (BindException e)
        {
            System.err.println("Socket bind failed on port: "+ this.clientSocket.getPort() + "!");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e)
        {
            System.err.println("Unknown error while connecting to: "+ this.clientSocket.getPort() + "!");
            e.printStackTrace(System.err);
            System.exit(1);
        }

    }


    public void end() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Unable to close socket of connection to: !" + this.clientSocket.getPort() + "!");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }


    public ServerCom accept() {
        ServerCom scon;

        scon = new ServerCom(serverPort, serverSocket);
        try {
            scon.clientSocket = serverSocket.accept();
        } catch (SocketException e) {
            System.err.println("Socket closed during the process!");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Unable to open a channel for the request for "+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            scon.in = new ObjectInputStream(scon.clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Unable to open the input stream of the socket on"+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            scon.out = new ObjectOutputStream(scon.clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Unable to open the output stream of the socket on "+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        return scon;
    }


    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("Unable to close the input stream of the socket on "+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            out.close();
        } catch (IOException e) {
            System.err.println("Unable to close the output stream of the socket on "+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println( "Unable to close socket of connection to "+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }


    public Message readMessage() {
        Message fromClient = null;                            // objecto

        try {
            fromClient = (Message) this.in.readObject();
        } catch (InvalidClassException e) {
            System.err.println("Unable to unserialize object sent by "+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error while reading from the input stream of connection to "+this.clientSocket.getPort()+"!");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("Object sent by " + this.clientSocket.getPort() + " is not of a known type!");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception e) {
                System.err.println("Cannot cast the object sent by " + this.clientSocket.getPort() + " to a Message.");
                e.printStackTrace(System.err);
                System.exit(1);
        }

        return fromClient;
    }


    public void writeMessage(Message toClient) {
        try {
            this.out.writeObject(toClient);
        } catch (InvalidClassException e) {
            System.err.println("Unable to serialize object to be sent " + this.clientSocket.getPort() + "!");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (NotSerializableException e) {
            System.err.println("Object to be sent to " + this.clientSocket.getPort() + " cannot be serialized!");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error while writing to the output stream of connection to " + this.clientSocket.getPort() + "!");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}