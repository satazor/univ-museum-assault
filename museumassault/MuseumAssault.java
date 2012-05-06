package museumassault;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * MuseumAssault main class.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class MuseumAssault
{
    /**
     * Program entry point.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        if (args.length <= 0) {
            System.err.println("Please specify the type to simulate.");
            System.err.println("Available types are: thief, chief, shared-site, room and corridor.");
            System.exit(1);
        }

        // Generate a new args array without the first argument (the type)
        LinkedList<String> argsList = new LinkedList<String>();
        argsList.addAll(Arrays.asList(args));
        if (argsList.size() > 0) {
            argsList.remove(0);
        }

        String[] newArgs = new String[argsList.size()];
        argsList.toArray(newArgs);

        String type = args[0];

        if ("thief".equals(type)) {
            museumassault.thief.Main.main(newArgs);
        } else if ("chief".equals(type)) {
            museumassault.chief.Main.main(newArgs);
        } else if ("logger".equals(type)) {
            museumassault.logger.server.Main.main(newArgs);
        } else if ("shared-site".equals(type)) {
            museumassault.shared_site.server.Main.main(newArgs);
        } else if ("corridor".equals(type)) {
            museumassault.corridor.server.Main.main(newArgs);
        } else if ("room".equals(type)) {
            museumassault.room.server.Main.main(newArgs);
        } else {
            System.err.println("Unknown type specified.");
            System.err.println("Available types are: thief, chief, shared-site, room and corridor.");
            System.exit(1);
        }
    }
}
