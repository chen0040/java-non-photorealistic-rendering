/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codezone;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 *
 * @author AB
 */
public class IPUtils {
    
    public static double GradientNorm(Point pt, BufferedImage img)
    {
        Point dir = GetOrientation(img, pt);

        double xx = dir.x;
        double yy = dir.y;

        double l = Math.sqrt(xx * xx + yy * yy);

        return l;
    }

    public static boolean isOutsideImage(Point pt, BufferedImage img)
    {
        int canvas_width = img.getWidth();
        int canvas_height = img.getHeight();

        if ((pt.x < 0) || (pt.x >= canvas_width) || (pt.y < 0) || (pt.y >= canvas_height))
        {
            return true;
        }

        return false;
    }

    public static BufferedImage scaleImage(BufferedImage bsrc, int width, int height, String dest) throws IOException
    {
           BufferedImage bdest =
              new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
           Graphics2D g = bdest.createGraphics();
           AffineTransform at =
              AffineTransform.getScaleInstance((double)width/bsrc.getWidth(),
                  (double)height/bsrc.getHeight());
           g.drawRenderedImage(bsrc,at);
           return bdest;
    }
    
    public static void saveImage(BufferedImage img, String dest)
    {
        try{
            ImageIO.write(img,"JPG",new File(dest));
        }catch(IOException ie)
        {
            ie.printStackTrace();
        }
    }


    public static Point GetOrientation(BufferedImage source, Point pt)
    {
        int height = source.getHeight();
        int width = source.getWidth();

        int[][] pixel = new int[3][];
        for(int i=0; i<3; ++i)
        {
            pixel[i]=new int[3];
        }

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                pixel[i][j] = 0;
            }
        }

        for (int i = 0; i < 3; ++i)
        {
            int yy = pt.getIntY() + i - 1;

            if (yy < 0)
            {
                continue;
            }
            else if (yy >= height)
            {
                break;
            }

            for (int j = 0; j < 3; ++j)
            {
                int xx = pt.getIntX() + j - 1;
                if (xx < 0)
                {
                    continue;
                }
                else if (xx >= width)
                {
                    break;
                }

                pixel[i][j] = GetLuminance(source, new Point(xx, yy));
            }
        }

        int vx = -pixel[0][0] + pixel[0][2]
                - 2 * pixel[1][0] + 2 * pixel[1][2]
                - pixel[2][0] + pixel[2][2];
        int vy = -pixel[0][0] + pixel[2][0]
                - 2 * pixel[0][1] + 2 * pixel[2][1]
                - pixel[0][2] + pixel[2][2];
        return new Point(vx, vy);
    }

    public static byte GetLuminance(BufferedImage source, Point pt)
    {
        int rgb=source.getRGB(pt.getIntX(), pt.getIntY());

        return GetLuminance(new java.awt.Color(rgb));
    }

    public static byte GetLuminance(Color color)
    {
        return (byte)(0.3 * color.getRed() + 0.59 * color.getGreen() + 0.11 * color.getBlue());
    }
}
