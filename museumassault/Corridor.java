package museumassault;

/**
 *
 * @author André
 */
public class Corridor
{
    protected Integer[] inwards;
    protected Integer[] outwards;
    protected int current;

    /**
     *
     * @param room
     */
    public Corridor(int nrPositions)
    {
        this.inwards = new Integer[nrPositions];
        this.outwards = new Integer[nrPositions];
        this.current = 0;
    }

    /**
     *
     */
    public synchronized boolean crallIn(int thiefId, int increment)
    {
        // Get current position
        int length = this.inwards.length;
        Integer currentPosition = null;
        for (int x = 0; x < length; x++) {
            if (this.inwards[x] == thiefId) {
                currentPosition = inwards[x];
                break;
            }
        }

        if (currentPosition == null) {
            currentPosition = -1;
        }

        int newPosition = currentPosition + increment;

        if (this.current != currentPosition) {
            while (this.inwards[newPosition] != null) {
                this.nextIteration();
                try {
                    this.inwards[newPosition].wait();
                } catch (InterruptedException ex) {}
            }
        }

        this.inwards[currentPosition] = null;
        if (newPosition >= this.inwards.length) {
            return true;
        }

        this.inwards[newPosition] = thiefId;
        return false;
    }

    /**
     *
     */
    protected void nextIteration()
    {
        this.current += 1;
        if (this.current >= this.inwards.length * 2) {
            this.current = 0;
        }

        if (this.current < this.inwards.length) {
            this.inwards[this.current].notify();
        } else {
            this.outwards[this.current - this.inwards.length].notify();
        }
    }
}
