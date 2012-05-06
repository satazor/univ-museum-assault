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
        LinkedList<String> argsList = new LinkedList<>();
        argsList.addAll(Arrays.asList(args));
        if (argsList.size() > 0) {
            argsList.remove(0);
        }

        String[] newArgs = new String[argsList.size()];
        argsList.toArray(newArgs);

        switch (args[0]) {
            case "thief":
                museumassault.thief.Main.main(newArgs);
                break;
            case "chief":
                museumassault.chief.Main.main(newArgs);
                break;
            case "logger":
                museumassault.logger.server.Main.main(newArgs);
                break;
            case "shared-site":
                museumassault.shared_site.server.Main.main(newArgs);
                break;
            case "corridor":
                museumassault.corridor.server.Main.main(newArgs);
                break;
            case "room":
                museumassault.room.server.Main.main(newArgs);
                break;
            default:
                System.err.println("Unknown type specified.");
                System.err.println("Available types are: thief, chief, shared-site, room and corridor.");
                System.exit(1);
        }
    }
}
