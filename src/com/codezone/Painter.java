/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;
/**
 *
 * @author AB
 */
public abstract class Painter {
    protected NPRCanvas mCanvas;
    
    public Painter(NPRCanvas canvas)
    {
        mCanvas=canvas;
        mCanvas.addPainter(this);
    }

    protected int m_iPaintRuns=2000;
    protected int m_iAnimatePeriod=200;

    public void setPaintRuns(int runs)
    {
        m_iPaintRuns=runs;
    }

    public void mouseDragged(Point pt)
    {

    }

    public void mousePressed(Point pt)
    {
        
    }

    public void mouseReleased(Point pt)
    {
        
    }

    public int getPaintRuns()
    {
        return m_iPaintRuns;
    }

    public void setAnimatePeriod(int period)
    {
        m_iAnimatePeriod=period;
    }

    public int getAnimatePeriod()
    {
        return m_iAnimatePeriod;
    }

    public abstract void paint();
}
