package museumassault.common;

import java.io.*;
import java.net.*;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class ClientCom
{
    protected Socket socket = null;
    protected String serverAddress = null;
    protected int serverPort;

    protected int timeout = 1;
    protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;

    /**
     *
     * @param connectionString
     * @param timeout
     */
    public ClientCom(String connectionString)
    {
        String address = connectionString;
        String[] split = address.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid connection string.");
        }

        this.serverAddress = split[0];
        this.serverPort = Integer.parseInt(split[1]);
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
     * @return
     */
    public boolean open()
    {
        boolean success = true;
        SocketAddress socketAddress = new InetSocketAddress(this.serverAddress, this.serverPort);

        try {
            this.socket = new Socket();
            this.socket.connect(socketAddress, this.timeout);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host provided: " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (NoRouteToHostException e) {
            System.err.println("Unreachable host provided: " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (ConnectException e) {
            if (e.getMessage().equals("Connection refused")) {
                success = false;
            } else {
                System.out.println("Unable to connect: " + e.getMessage() + ".");
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Timeout exceeded when trying to connect to " + this.serverAddress + ":" + this.serverPort + ".");
            success = false;
        } catch (IOException e) {
            System.err.println("Unknown error while connection to " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        if (!success) {
            return false;
        }

        try {
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Unable to open output stream upon connecting to " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Unable to open output stream upon connecting to " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        return success;
    }

    /**
     *
     */
    public void close()
    {
        try {
            this.in.close();
        } catch (IOException e) {
            System.err.println("Unable to close input stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            this.out.close();
        } catch (IOException e) {
            System.err.println("Unable to close output stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        try {
            this.socket.close();
        } catch (IOException e) {
            System.err.println("Unable to close socket of connection to " + this.serverAddress + ":" + this.serverPort + ".");
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
        Message fromServer = null;

        try {
            fromServer = (Message) this.in.readObject();
        } catch (InvalidClassException e) {
            System.err.println("Unable to unserialize object sent by " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error while reading from the input stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.err.println("Object sent by " + this.serverAddress + ":" + this.serverPort + " is not of a known type.");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Cannot cast the object sent by " + this.serverAddress + ":" + this.serverPort + " to a Message.");
            e.printStackTrace(System.err);
            System.exit(1);
        }

        return fromServer;
    }

    /**
     * @param toServer
     */
    public void writeMessage(Message toServer)
    {
        try {
            this.out.writeObject(toServer);
        } catch (InvalidClassException e) {
            System.err.println("Unable to serialize object to be sent " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (NotSerializableException e) {
            System.err.println("Object to be sent to " + this.serverAddress + ":" + this.serverPort + " cannot be serialized.");
            e.printStackTrace(System.err);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error while writing to the output stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
