/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package museumassault;

/**
 *
 * @author André
 */
class Team {

    protected boolean prepared = false;
    protected boolean busy = false;
    protected int id;
    protected int nrThiefs;

    /**
     *
     * @param id
     */
    public Team(int id, int nrThiefs)
    {
        this.id = id;
        this.nrThiefs = nrThiefs;
    }

    /**
     *
     */
    public int getId()
    {
        return this.id;
    }

    /**
     *
     */
    public int getNrThiefs()
    {
        return this.nrThiefs;
    }

    /**
     *
     * @return
     */
    public boolean isPrepared(boolean prepared)
    {
        return this.prepared = prepared;
    }

    /**
     *
     * @return
     */
    public boolean isPrepared()
    {
        return this.prepared;
    }

    /**
     *
     * @return
     */
    public boolean isBusy(boolean busy)
    {
        return this.busy = busy;
    }

    /**
     *
     * @return
     */
    public boolean isBusy()
    {
        return this.busy;
    }
}
