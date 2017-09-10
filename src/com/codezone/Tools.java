/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;
import java.util.Random;
/**
 *
 * @author AB
 */
public class Tools {
    protected static Tools mSingleton=null;
    protected Random mRand;
    
    private Tools()
    {
        mRand=new Random();
    }

    public int randomInt(int upper)
    {
        return mRand.nextInt(upper);
    }

    public static Tools getSingleton()
    {
        if(mSingleton == null)
        {
            mSingleton=new Tools();
        }
        
        return mSingleton;
    }

    public double random()
    {
        return mRand.nextDouble();
    }
}
