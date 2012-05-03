package museumassault.common;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 */
public class ServerCom
{
    protected ServerSocket serverSocket = null;
    protected Socket clientSocket = null;
    protected int serverPort;

    protected int timeout = 10;
    protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;

    /**
     *
     * @param serverPort
     */
    public ServerCom(int serverPort)
    {
        this.serverPort = serverPort;
    }

    /**
     *
     * @param serverPort
     * @param serverSocket
     */
    public ServerCom(int serverPort, ServerSocket serverSocket)
    {
    	this.serverPort = serverPort;
        this.serverSocket = serverSocket;
    }

    /**
     *
     * @param timeout
     */
    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }


    /**
     *
     */
    public void start()
    {
        try {
            this.serverSocket = new ServerSocket(this.serverPort, this.timeout);
        } catch (BindException e) {
            System.err.println("Socket bind failed on port " + this.serverPort + " (port already in use?).");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Unknown error when trying to listening on port " + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    /**
     *
     */
    public void end()
    {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Unable to close server socket.");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    /**
     *
     * @return
     */
    public ServerCom accept()
    {
        ServerCom scon;

        scon = new ServerCom(this.serverPort, this.serverSocket);
        try {
            scon.clientSocket = this.serverSocket.accept();
        } catch (SocketException e) {
            System.err.println("Client socket in port " + scon.clientSocket.getPort() + " closed during the process.");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Unable to open a channel for the client socket in port " + scon.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            scon.in = new ObjectInputStream(scon.clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Unable to open the input stream of the client socket in port " + scon.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            scon.out = new ObjectOutputStream(scon.clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Unable to open the output stream of the client socket in port " + scon.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        return scon;
    }

    /**
     *
     */
    public void close()
    {
        try {
            this.in.close();
        } catch (IOException e) {
            System.err.println("Unable to close the input stream of the client socket in port " + this.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            this.out.close();
        } catch (IOException e) {
            System.err.println("Unable to close the output stream of the client socket in port " + this.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            this.clientSocket.close();
        } catch (IOException e) {
            System.err.println( "Unable to close client connection in port " + this.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    /**
     *
     * @return
     */
    public Message readMessage()
    {
        Message fromClient = null;

        try {
            fromClient = (Message) this.in.readObject();
        } catch (InvalidClassException e) {
            System.err.println("Unable to unserialize object sent by the client in port " + this.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error while reading from the input stream of client in port " + this.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("Object sent by the client of port " + this.clientSocket.getPort() + " is not of a known type.");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Cannot cast the object sent by the client in port " + this.clientSocket.getPort() + " to a Message.");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        return fromClient;
    }

    /**
     *
     * @param toClient
     */
    public void writeMessage(Message toClient)
    {
        try {
            this.out.writeObject(toClient);
        } catch (InvalidClassException e) {
            System.err.println("Unable to unserialize object to be sent to the client in port " + this.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (NotSerializableException e) {
            System.err.println("Object to be sent to the client in port " + this.clientSocket.getPort() + " cannot be serialized.");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error while writing to the output stream of client in port " + this.clientSocket.getPort() + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
