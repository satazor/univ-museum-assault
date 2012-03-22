package museumassault;

/**
 *
 * @author André
 */
public class Thief extends Thread {

    public static final int ARRIVED_ACTION = 1;
    
    public Thief(Exterior exterior) {

    }


   @Override
   public void run ()
   {

      while (true)
      {
          exterior.getMessage();

          // ler mensagem
          // se mensagem for de thief chegou..
          // se ainda houver salas..
            // se temos equipas livres
            // se nao temos, dormir
      }
   }
}
