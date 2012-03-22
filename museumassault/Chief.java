package museumassault;

/**
 *
 * @author André
 */
public class Chief extends Thread {

    static final int CHIEF_ID = -1;
    
    Exterior exterior;
    int nrStolenCanvas = 0;
    int nrRooms;
    int stolenRooms = 0;
    
    public Chief(Exterior exterior, int nrRooms) {
        this.exterior = exterior;
        this.nrRooms = nrRooms;
    }


   @Override
   public void run ()
   {
      while (true)
      {
          Message message = this.exterior.readMessage(Chief.CHIEF_ID);
          if (message != null) {
              
              if (message.getAction() == Thief.ARRIVED_ACTION) {
                  //if (message.gotCanvas()) {
                      this.nrStolenCanvas++;
                      this.stolenRooms++;
                  //}
              }
          }
     
          // ler mensagem
          // se mensagem for de thief chegou..
          // se ainda houver salas..
            // se temos equipas livres
            // se nao temos, dormir
      }
   }
   
   /**
    * 
    */
   
}
