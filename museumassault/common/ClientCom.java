package museumassault.common;

import java.io.*;
import java.net.*;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;

/**
 * ClientCom class.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class ClientCom
{
    protected Socket socket = null;
    protected String serverAddress = null;
    protected int serverPort;
    protected String connectionString;

    protected int timeout = 10;
    protected ObjectInputStream in = null;
    protected ObjectOutputStream out = null;

    /**
     * Constructor.
     *
     * @param connectionString
     */
    public ClientCom(String host, int port)
    {
        this.serverAddress = host;
        this.serverPort = port;
    }

    /**
     * Set the connection timeout.
     *
     * @param timeout The timeout value in miliseconds
     */
    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    /**
     * Gets the server host.
     *
     * @return The server host
     */
    public String getHost()
    {
        return this.serverAddress;
    }

    /**
     * Gets the server port.
     *
     * @return The server port
     */
    public int getPort()
    {
        return this.serverPort;
    }

    /**
     * Opens the connection to the server.
     *
     * @return true if the connection was successful, false otherwise

     * @throws ComException if an error ocurred while connecting
     * @throws ShutdownException if the server is or was shutted down
     */
    public boolean open() throws ComException, ShutdownException
    {
        boolean success = true;
        SocketAddress socketAddress = new InetSocketAddress(this.serverAddress, this.serverPort);

        try {
            this.socket = new Socket();
            this.socket.connect(socketAddress, this.timeout);
        } catch (UnknownHostException e) {
            //System.err.println("Unknown host provided: " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unknown host provided: " + this.serverAddress + ":" + this.serverPort + ".");
        } catch (NoRouteToHostException e) {
            //System.err.println("Unreachable host provided: " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unreachable host provided: " + this.serverAddress + ":" + this.serverPort + ".");
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
            //System.err.println("Unknown error while connection to " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ShutdownException();
        }

        if (!success) {
            return false;
        }

        try {
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            //System.err.println("Unable to open output stream upon connecting to " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unable to open output stream upon connecting to " + this.serverAddress + ":" + this.serverPort + ".");
        }

        try {
            this.in = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            //System.err.println("Unable to open output stream upon connecting to " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unable to open input stream upon connecting to " + this.serverAddress + ":" + this.serverPort + ".");
        }

        return success;
    }

    /**
     * Closes the connection to the server.
     *
     * @throws ComException if an error ocurred while connecting
     */
    public void close() throws ComException
    {
        try {
            this.in.close();
        } catch (IOException e) {
            //System.err.println("Unable to close input stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unable to close input stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
        }

        try {
            this.out.close();
        } catch (IOException e) {
            //System.err.println("Unable to close output stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unable to close output stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
        }

        try {
            this.socket.close();
        } catch (IOException e) {
            //System.err.println("Unable to close socket of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unable to close socket of connection to " + this.serverAddress + ":" + this.serverPort + ".");
        }
    }

    /**
     * Reads a message from the server.
     *
     * @return the message read
     *
     * @throws ComException if an error ocurred while reading
     * @throws ShutdownException if the server is or was shutted down
     */
    public Message readMessage() throws ComException, ShutdownException
    {
        Message fromServer = null;

        try {
            fromServer = (Message) this.in.readObject();
        } catch (InvalidClassException e) {
            //System.err.println("Unable to unserialize object sent by " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unable to unserialize object sent by " + this.serverAddress + ":" + this.serverPort + ".");
        } catch (IOException e) {
            //System.err.println("Error while reading from the input stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            //System.err.println("Did the server terminated?");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ShutdownException();
        } catch (ClassNotFoundException e) {
            //System.err.println("Object sent by " + this.serverAddress + ":" + this.serverPort + " is not of a known type.");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Object sent by " + this.serverAddress + ":" + this.serverPort + " is not of a known type.");
        } catch (Exception e) {
            //System.err.println("Cannot cast the object sent by " + this.serverAddress + ":" + this.serverPort + " to a Message.");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Cannot cast the object sent by " + this.serverAddress + ":" + this.serverPort + " to a Message.");
        }

        return fromServer;
    }

    /**
     * Writes a message to the server.
     *
     * @param toServer The message
     *
     * @throws ComException if an error ocurred while writing
     * @throws ShutdownException if the server is or was shutted down
     */
    public void writeMessage(Message toServer) throws ShutdownException, ComException
    {
        try {
            this.out.writeObject(toServer);
        } catch (InvalidClassException e) {
            //System.err.println("Unable to serialize object to be sent " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Unable to serialize object to be sent " + this.serverAddress + ":" + this.serverPort + ".");
        } catch (NotSerializableException e) {
            //System.err.println("Object to be sent to " + this.serverAddress + ":" + this.serverPort + " cannot be serialized.");
            //e.printStackTrace(System.err);
            //System.exit(1);
            throw new ComException("Object to be sent to " + this.serverAddress + ":" + this.serverPort + " cannot be serialized.");
        } catch (IOException e) {
            //System.err.println("Error while writing to the output stream of connection to " + this.serverAddress + ":" + this.serverPort + ".");
            //e.printStackTrace(System.err);
            //System.exit(1);
            //System.err.println("Did the server terminated?");
            throw new ShutdownException();
        }
    }
}
