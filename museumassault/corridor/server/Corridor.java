package museumassault.corridor.server;

import java.util.HashMap;
import museumassault.logger.CorridorDetails;
import museumassault.logger.client.LoggerClient;

/**
 * Corridor class.
 *
 * This class represents the corridor between a room and the outside.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Corridor
{
    protected int id;
    protected Integer[] inwards;
    protected Integer[] outwards;

    protected HashMap<Integer, Integer> thievesPositions = new HashMap<>();
    protected int maxDistanceBetweenThieves;
    protected int atTheRoom = 0;
    protected int atTheCorridor = 0;
    protected LoggerClient logger;

    /**
     * Class constructor.
     *
     * @param distance                  the distance from the outside to the room
     * @param maxDistanceBetweenThieves max allowed distance between positions
     */
    public Corridor(int id, int distance, int maxDistanceBetweenThieves, LoggerClient logger)
    {
        if (maxDistanceBetweenThieves <= 0) {
            throw new IllegalArgumentException("The max distance between thieves must be greater than zero.");
        }

        this.id = id;
        this.inwards = new Integer[distance];
        this.outwards = new Integer[distance];
        this.maxDistanceBetweenThieves = maxDistanceBetweenThieves;
        this.logger = logger;

        this.setCorridorDetails();
    }

    /**
     *
     */
    protected final void setCorridorDetails()
    {
        this.logger.setCorridorDetails(new CorridorDetails(this.id, this.getTotalPositions(), this.thievesPositions));
    }

    /**
     * Get the corridor id.
     *
     * @return the id of the corridor
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Get the total number of positions (double of the distance).
     *
     * @return the number of positions
     */
    public int getTotalPositions()
    {
        return this.inwards.length + this.outwards.length;
    }

    /**
     * Get the position of a thief.
     *
     * @param thiefId the thief id
     * @return the thief position within the corridor
     */
    public Integer getThiefPosition(int thiefId)
    {
        Integer pos = this.thievesPositions.get(thiefId);
        if (pos == null || pos == -1 || pos > this.getTotalPositions()) {
            return null;
        }

        return pos;
    }

    /**
     * Clears all the positions of the thieves (cached ones).
     */
    public void clearPositions()
    {
        int length = this.inwards.length;
        for (int x = 0; x < length; x++) {
            this.inwards[x] = this.outwards[x] = null;
        }

        this.thievesPositions.clear();
        this.atTheRoom = 0;
    }

    /**
     * Moves the thief towards the room.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return True if the thief arrived the room entrance, false otherwise
     */
    public synchronized boolean crawlOut(int thiefId, int increment)
    {
        Integer currentPosition = (Integer) this.thievesPositions.get(thiefId);
        Integer realPosition = currentPosition;

        if (currentPosition == null) {
            currentPosition = -1;
            this.logger.setThiefStatus(thiefId, LoggerClient.THIEF_STATUS.CRAWLING_OUTWARDS);
        } else if (currentPosition == -1 || currentPosition >= this.outwards.length) {
            throw new IllegalStateException("Thief already crawled out.");
        }

        int newPosition = -1;
        //System.out.println("[Thief #" + thiefId +"] Crawl out from " + currentPosition + " with increment " + increment);

        int offset;
        boolean foundSlot;
        boolean moved = false;
        boolean ret = false;

        while (true) {

            for (offset = increment; offset > 0; offset--) {

                newPosition = currentPosition + offset;

                if (newPosition >= this.outwards.length) {
                    foundSlot = true;
                } else if (this.outwards[newPosition] == null) {
                    foundSlot = true;
                    this.outwards[newPosition] = thiefId;
                } else {
                    foundSlot = false;
                }

                if (foundSlot) {

                    if (currentPosition != -1) {
                        this.outwards[currentPosition] = null;
                    }
                    if (newPosition < this.outwards.length) {
                        this.outwards[newPosition] = thiefId;
                        //System.out.println("[Thief #" + thiefId +"] Found free slot in position #" + newPosition);
                    } else {
                        this.atTheRoom++;
                        //System.out.println("[Thief #" + thiefId +"] Lefting inwards corridor..");
                    }

                    if (this.checkGaps()) {
                        //System.out.println("[Thief #" + thiefId +"] New position is OK");
                        moved = true;
                        break;
                    } else {
                        //System.out.println("[Thief #" + thiefId +"] New position is not OK");
                        if (currentPosition != -1) {
                            this.outwards[currentPosition] = thiefId;
                        }
                        if (newPosition < this.outwards.length) {
                            this.outwards[newPosition] = null;
                        } else {
                            this.atTheRoom--;
                        }
                    }
                } else {
                    //System.out.println("[Thief #" + thiefId +"] No free slot in position #" + newPosition);
                }
            }

            if (moved) {
                if (newPosition >= this.outwards.length) {
                    ret = true;
                    this.thievesPositions.put(thiefId, -1);
                    this.logger.setThiefStatus(thiefId, LoggerClient.THIEF_STATUS.AT_ROOM_ENTRANCE);
                    //System.out.println("[Thief #" + thiefId +"] Moved successfully outside the outwards corridor");
                } else {
                    this.thievesPositions.put(thiefId, newPosition);
                    //System.out.println("[Thief #" + thiefId +"] Moved successfully to position #" + newPosition);
                }

                if (realPosition == null) {
                    this.atTheCorridor++;
                }

                this.setCorridorDetails();

                this.notifyAll();
                try {
                    this.wait(5);  // Give the opportunity for other thieves to crawl (Thread.yeild() was not working as expected)
                } catch(InterruptedException e) {}

                break;
            }

            try {
                //System.out.println("[Thief #" + thiefId +"] Could not find a position.. waiting..");
                this.notifyAll();
                this.wait();
            } catch(InterruptedException e) {}
        }

        return ret;
    }

    /**
     * Moves the thief towards the outside.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return True if the thief arrived the outside entrance, false otherwise
     */
    public synchronized boolean crawlIn(int thiefId, int increment)
    {
        Integer currentPosition = (Integer) this.thievesPositions.get(thiefId);

        if (currentPosition == null) {
            throw new IllegalStateException("Thief didn't crawled out yet.");
        } else if (currentPosition != -1) {
            if (currentPosition < this.outwards.length) {
                throw new IllegalStateException("Thief didn't crawled out yet.");
            }
            if (currentPosition - this.outwards.length >= this.inwards.length) {
                throw new IllegalStateException("Thief already crawled in.");
            }
            currentPosition -= this.outwards.length;
        } else {
            this.logger.setThiefStatus(thiefId, LoggerClient.THIEF_STATUS.CRAWLING_INWARDS);
        }

        int newPosition = -1;
        //System.out.println("[Thief #" + thiefId +"] Crawl in from " + currentPosition + " with increment " + increment);

        int offset;
        boolean foundSlot;
        boolean moved = false;
        boolean ret = false;

        while (true) {

            for (offset = increment; offset > 0; offset--) {

                newPosition = currentPosition + offset;

                if (newPosition >= this.inwards.length) {
                    foundSlot = true;
                } else if (this.inwards[newPosition] == null) {
                    foundSlot = true;
                    this.inwards[newPosition] = thiefId;
                } else {
                    foundSlot = false;
                }

                if (foundSlot) {

                    if (currentPosition != -1) {
                        this.inwards[currentPosition] = null;
                    } else {
                        this.atTheRoom--;
                    }
                    if (newPosition < this.inwards.length) {
                        this.inwards[newPosition] = thiefId;
                        //System.out.println("[Thief #" + thiefId +"] Found free slot in position #" + newPosition);
                    } else {
                        //System.out.println("[Thief #" + thiefId +"] Lefting inwards corridor..");
                    }

                    if (this.checkGaps()) {
                        //System.out.println("[Thief #" + thiefId +"] New position is OK");
                        moved = true;
                        break;
                    } else {
                        //System.out.println("[Thief #" + thiefId +"] New position is not OK");
                        if (currentPosition != -1) {
                            this.inwards[currentPosition] = thiefId;
                        } else {
                            this.atTheRoom++;
                        }
                        if (newPosition < this.inwards.length) {
                            this.inwards[newPosition] = null;
                        }
                    }
                } else {
                    //System.out.println("[Thief #" + thiefId +"] No free slot in position #" + newPosition);
                }
            }

            if (moved) {
                if (newPosition >= this.inwards.length) {
                    ret = true;
                    this.thievesPositions.put(thiefId, this.getTotalPositions());
                    //System.out.println("[Thief #" + thiefId +"] Moved successfully outside the inwards corridor");
                } else {
                    this.thievesPositions.put(thiefId, newPosition + this.outwards.length);
                    //System.out.println("[Thief #" + thiefId +"] Moved successfully to position #" + newPosition);
                }

                if (ret) {
                    this.atTheCorridor--;
                    if (this.atTheCorridor <= 0) {
                        this.clearPositions();
                    }
                }

                this.setCorridorDetails();

                this.notifyAll();
                try {
                    this.wait(5);  // Give the opportunity for other thieves to crawl (Thread.yeild() was not working as expected)
                } catch(InterruptedException e) {}

                break;
            }

            try {
                //System.out.println("[Thief #" + thiefId +"] Could not find a position.. waiting..");
                this.notifyAll();
                this.wait();
            } catch(InterruptedException e) {}
        }

        return ret;
    }

    /**
     * Checks if the currently displacement of thieves obey the maximum distance constraint.
     *
     * @return true if the constraint passed, false otherwise
     */
    protected synchronized boolean checkGaps()
    {
        int length = this.outwards.length;

        //System.out.print("OUTWARDS: ");
        //for (int x = 0; x < length; x++) {
            //System.out.print("#" + x + " - " + this.outwards[x] + "  ");
        //}
        //System.out.println();

        int nrGaps = 0;
        boolean first = true;
        for (int x = 0; x < length; x++) {
            if (this.outwards[x] != null) {
                if (first) {
                    first = false;
                } else if (nrGaps > this.maxDistanceBetweenThieves) {
                    return false;
                }
                nrGaps = 0;
            } else {
                nrGaps++;
            }
        }

        if (this.atTheRoom > 0) {
            if (first) {
                first = false;
            } else if (nrGaps > this.maxDistanceBetweenThieves) {
                return false;
            }
            nrGaps = 0;
        } else {
            nrGaps++;
        }

        //System.out.print("INWARDS: ");
        //for (int x = 0; x < length; x++) {
            //System.out.print("#" + x + " - " + this.inwards[x] + "  ");
        //}
        //System.out.println();

        for (int x = 0; x < length; x++) {
            if (this.inwards[x] != null) {
                if (first) {
                    first = false;
                } else if (nrGaps > this.maxDistanceBetweenThieves) {
                    return false;
                }
                nrGaps = 0;
            } else {
                nrGaps++;
            }
        }

        return true;
    }
}
