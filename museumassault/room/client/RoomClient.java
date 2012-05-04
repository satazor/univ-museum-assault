package museumassault.room.client;

import java.util.Random;

import museumassault.common.ClientCom;
import museumassault.common.Message;
import museumassault.room.IRoomMessageConstants;

/**
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 */
public class RoomClient implements IRoomMessageConstants
{
	 protected ClientCom con;
	 protected Random random = new Random();

	 /**
	  * Constructor.
	  */
	 public RoomClient(String connectionString)
	 {
	     this.con = new ClientCom(connectionString);
	 }
	 
	 /**
	  * Attempts to roll a canvas from the room.
	  *
	  * @param thiefId the thief id
	  *
	  * @return true if a canvas was successfully stolen, false otherwise
	  */
	 public boolean rollACanvas(int thiefId)
	 {
		 
		 while (!this.con.open()) {                           // Try until the server responds
	            try {
	                Thread.sleep(this.random.nextInt(500) + 500);
	            } catch (InterruptedException e) {}
	        }

	        this.con.writeMessage(new Message(ROLL_A_CANVAS_TYPE, thiefId));

	        Message response = this.con.readMessage();
	        this.con.close();

	        if (response.getType() == CANVAS_ROLLED_TYPE) {
	            return (boolean) response.getExtra();
	        } else {
	            System.err.println("Unexpected message type sent by the server: " + response.getType());
	            System.exit(1);

	            return false;
	        }
	 }
}
