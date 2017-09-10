/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;


/**
 *
 * @author AB
 */
public class BrushManager
{
    private int mBrushSize=5;
    protected static BrushManager mSingleton;
    protected int m_iCurrentBrush;

    public Brush[] mBrushes;

    public static final int BRUSH_BSLANTED=0;
    public static final int BRUSH_OVAL=1;
    public static final int BRUSH_GUIDEDLINES=2;
    public static final int BRUSH_BSLANTEDRAIN=3;
    public static final int BRUSH_POINT=4;
    public static final int BRUSH_RECTPIN=5;
    public static final int BRUSH_ROUNDEDPIN=6;
    public static final int BRUSH_WATERDROP=7;

    public static final int BRUSH_COUNT=8;
    

    private BrushManager()
    {
        mBrushes=new Brush[BRUSH_COUNT];
        mBrushes[BRUSH_BSLANTED]=new BrushBSlanted();
        mBrushes[BRUSH_OVAL]=new BrushOval();
        mBrushes[BRUSH_GUIDEDLINES]=new BrushGuidedLines();
        mBrushes[BRUSH_BSLANTEDRAIN]=new BrushBSlantedRain();
        mBrushes[BRUSH_POINT]=new BrushPoint();
        mBrushes[BRUSH_RECTPIN]=new BrushRectPin();
        mBrushes[BRUSH_ROUNDEDPIN]=new BrushRoundedPin();
        mBrushes[BRUSH_WATERDROP]=new BrushWaterDrop();

        m_iCurrentBrush=BRUSH_OVAL;
    }

    public Brush getCurrentBrush()
    {
        return mBrushes[m_iCurrentBrush];
    }

    public void setCurrentBrush(int brushIndex)
    {
        m_iCurrentBrush=brushIndex;
    }

    public static BrushManager getSingleton()
    {
        if(mSingleton == null)
        {
            mSingleton=new BrushManager();
        }

        return mSingleton;
    }

    public int getBrushSize()
    {
        return mBrushSize;
    }
}
