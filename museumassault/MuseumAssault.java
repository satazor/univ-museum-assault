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

        Room[] rooms = new Room[4];
        for (int x = 0; x < 4; x++) {
            rooms[x] = new Room(x + 1);
        }

        SharedSite exterior = new SharedSite(rooms, teams);


        Thief[] thiefs = new Thief[10];
        for (int x = 0; x < 10; x++) {
            Thief thief = new Thief(x + 1, exterior);
            thiefs[x] = thief;
            thief.start();
        }

        Chief chief = new Chief(exterior);
        chief.start();

        try {
            chief.join();
        } catch (InterruptedException e) {}

        System.out.println("End");
        System.exit(0);
    }
}
