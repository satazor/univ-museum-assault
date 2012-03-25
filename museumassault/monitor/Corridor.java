package museumassault.monitor;

import java.util.HashMap;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Corridor implements TargetCorridor
{
    protected Integer[] inwards;
    protected Integer[] outwards;

    protected HashMap thievesPositions = new HashMap();
    protected int maxDistanceBetweenThieves;
    protected int atTheRoom = 0;

    protected Logger logger;

    /**
     *
     * @param room
     */
    public Corridor(int nrPositions, int maxDistanceBetweenThieves, Logger logger)
    {
        assert(nrPositions % 2 != 0);
        assert(maxDistanceBetweenThieves <= 0);

        this.inwards = new Integer[nrPositions];
        this.outwards = new Integer[nrPositions];
        this.maxDistanceBetweenThieves = maxDistanceBetweenThieves;
        this.logger = logger;
    }

    /**
     *
     */
    public int getTotalPositions()
    {
        return this.inwards.length + this.outwards.length;
    }

    /**
     *
     */
    public void clearPositions() {

        int length = this.inwards.length;
        for (int x = 0; x < length; x++) {
            this.inwards[x] = this.outwards[x] = null;
        }

        this.thievesPositions.clear();
    }

    /**
     *
     */
    @Override
    public synchronized boolean crawlOut(int thiefId, int increment)
    {
        Integer currentPosition = (Integer) this.thievesPositions.get(thiefId);

        if (currentPosition == null) {
            currentPosition = -1;
        } else if (currentPosition == -1) {
            throw new RuntimeException("Thief already crawled out.");
        }

        this.logger.setThiefStatus(thiefId, Logger.THIEF_STATUS.CRAWLING_OUTWARDS);

        int newPosition = -1;
        System.out.println("[Thief #" + thiefId +"] Crawl out from " + currentPosition + " with increment " + increment);

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
                        System.out.println("[Thief #" + thiefId +"] Found free slot in position #" + newPosition);
                    } else {
                        this.atTheRoom++;
                        System.out.println("[Thief #" + thiefId +"] Lefting inwards corridor..");
                    }

                    if (this.checkGaps()) {
                        System.out.println("[Thief #" + thiefId +"] New position is OK");
                        moved = true;
                        break;
                    } else {
                        System.out.println("[Thief #" + thiefId +"] New position is not OK");
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
                    System.out.println("[Thief #" + thiefId +"] No free slot in position #" + newPosition);
                }
            }

            if (moved) {
                if (newPosition >= this.outwards.length) {
                    ret = true;
                    this.thievesPositions.put(thiefId, -1);
                    System.out.println("[Thief #" + thiefId +"] Moved successfully outside the outwards corridor");
                } else {
                    this.thievesPositions.put(thiefId, newPosition);
                    System.out.println("[Thief #" + thiefId +"] Moved successfully to position #" + newPosition);
                }

                this.notifyAll();
                try {
                    this.wait(50);  // Give the opportunity for other thieves to crawl (Thread.yeild() was not working as expected)
                } catch(InterruptedException e) {}

                break;
            }

            try {
                System.out.println("[Thief #" + thiefId +"] Could not find a position.. waiting..");
                this.notifyAll();
                this.wait();
            } catch(InterruptedException e) {}
        }

        return ret;
    }

    /**
     *
     */
    @Override
    public synchronized boolean crawlIn(int thiefId, int increment)
    {
        Integer currentPosition = (Integer) this.thievesPositions.get(thiefId);

        if (currentPosition != -1) {
            if (currentPosition < this.outwards.length) {
                throw new RuntimeException("Thief didn't yet crawled out yet.");
            }
            if (currentPosition - this.outwards.length >= this.inwards.length) {
                throw new RuntimeException("Thief already crawled in.");
            }
            currentPosition -= this.outwards.length;
        }

        this.logger.setThiefStatus(thiefId, Logger.THIEF_STATUS.CRAWLING_INWARDS);

        int newPosition = -1;
        System.out.println("[Thief #" + thiefId +"] Crawl in from " + currentPosition + " with increment " + increment);

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
                        System.out.println("[Thief #" + thiefId +"] Found free slot in position #" + newPosition);
                    } else {
                        System.out.println("[Thief #" + thiefId +"] Lefting inwards corridor..");
                    }

                    if (this.checkGaps()) {
                        System.out.println("[Thief #" + thiefId +"] New position is OK");
                        moved = true;
                        break;
                    } else {
                        System.out.println("[Thief #" + thiefId +"] New position is not OK");
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
                    System.out.println("[Thief #" + thiefId +"] No free slot in position #" + newPosition);
                }
            }

            if (moved) {
                if (newPosition >= this.inwards.length) {
                    ret = true;
                    this.thievesPositions.put(thiefId, this.inwards.length + this.outwards.length);
                    System.out.println("[Thief #" + thiefId +"] Moved successfully outside the inwards corridor");
                } else {
                    this.thievesPositions.put(thiefId, newPosition + this.outwards.length);
                    System.out.println("[Thief #" + thiefId +"] Moved successfully to position #" + newPosition);
                }

                this.notifyAll();
                try {
                    this.wait(50);  // Give the opportunity for other thieves to crawl (Thread.yeild() was not working as expected)
                } catch(InterruptedException e) {}

                break;
            }

            try {
                System.out.println("[Thief #" + thiefId +"] Could not find a position.. waiting..");
                this.notifyAll();
                this.wait();
            } catch(InterruptedException e) {}
        }

        return ret;
    }

    /**
     *
     */
    protected synchronized boolean checkGaps() {

        int length = this.outwards.length;

        System.out.print("OUTWARDS: ");
        for (int x = 0; x < length; x++) {
            System.out.print("#" + x + " - " + this.outwards[x] + "  ");
        }
        System.out.println();
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
                System.out.print("INWARDS: ");
        for (int x = 0; x < length; x++) {
            System.out.print("#" + x + " - " + this.inwards[x] + "  ");
        }
        System.out.println();

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
