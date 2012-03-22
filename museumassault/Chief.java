package museumassault;


/**
 *
 * @author André
 */
public class Chief extends Thread
{
    static final int CHIEF_ID = -1;

    ExteriorSite exterior;
    int nrStolenCanvas = 0;
    int nrRooms;
    int stolenRooms = 0;

    public Chief(ExteriorSite exterior, int nrRooms)
    {
        this.exterior = exterior;
        this.nrRooms = nrRooms;
    }


   @Override
   public void run ()
   {
      while (true)
      {
           System.out.println("[Chief] takeARest()");

          Integer thiefId = this.exterior.takeARest();
          if (thiefId == null) {
              return;
          }

          // collect canvas
          System.out.println("[Chief] collectCanvas() on " + thiefId);
          return;

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
