package museumassault;

/**
 *
 * @author André
 */
public class MuseumAssault
{
    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Team[] teams = new Team[1];
        for (int x = 0; x < 1; x++) {
            teams[x] = new Team(x + 1, 10);
        }

        SharedSite exterior = new SharedSite(4, teams);

        Chief chief = new Chief(exterior);
        chief.start();

        Thief[] thiefs = new Thief[10];
        for (int x = 0; x < 10; x++) {
            Thief thief = new Thief(x + 1, 1, exterior);
            thiefs[x] = thief;
            thief.start();
        }

        try {
            chief.join();
        } catch (InterruptedException e) {}

        for (int x = 0; x < 10; x++) {
            thiefs[x].interrupt();
        }

        System.out.println("End");
    }
}
