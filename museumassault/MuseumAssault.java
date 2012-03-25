package museumassault;

import museumassault.monitor.Corridor;
import museumassault.monitor.Room;
import museumassault.monitor.SharedSite;

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
        int nrChiefs = 1;
        int nrTeams = 1;
        int nrThievesPerTeam = 5;
        int nrRooms = 4;
        int totalThieves = nrTeams * nrThievesPerTeam;

        Team[] teams = new Team[nrTeams];
        for (int x = 0; x < nrTeams; x++) {
            teams[x] = new Team(x + 1, nrThievesPerTeam);
        }

        Room[] rooms = new Room[nrRooms];
        for (int x = 0; x < nrRooms; x++) {
            rooms[x] = new Room(x + 1, 5, new Corridor(6, 1));
        }

        SharedSite site = new SharedSite(rooms, teams, (nrChiefs > 1));

        Thief[] thieves = new Thief[totalThieves];
        for (int x = 0; x < totalThieves; x++) {
            Thief thief = new Thief(x + 1, site);
            thieves[x] = thief;
            thief.start();
        }

        Chief[] chiefs = new Chief[nrChiefs];
        for (int x = 0; x < nrChiefs; x++) {
            Chief chief = new Chief(x + 1, site);
            chiefs[x] = chief;
            chief.start();
        }

        for (int x = 0; x < nrChiefs; x++) {
            try {
                chiefs[x].join();
            } catch (InterruptedException e) {}
        }

        System.out.println("End");
        System.exit(0);
    }
}
