/*
 *	Author:      Samuel Chassot (270955)
 *	Date:        4 avr. 2017
 */


package ch.epfl.alpano.gui;

import java.util.function.DoubleUnaryOperator;

import ch.epfl.alpano.Panorama;

@FunctionalInterface
public interface ChannelPainter {

    /**
     * return the value of the channel at the position x,y  
     * @param x coordinate x
     * @param y coordinate y
     * @return the value at (x,y)
     */
    public abstract float valueAt(int x, int y);
    
    /**
     * return the greatest difference between the distance of the pixel(x,y) and the distance of his neighbours
     * @param pano the panorama in which you want to work
     * @return the max difference distance
     */
    public static ChannelPainter maxDistanceToNeighbors(Panorama pano){
        
        return (x,y)->
            Math.max(Math.max(pano.distanceAt(x, y-1), pano.distanceAt(x, y+1)),
                    Math.max(pano.distanceAt(x+1, y), pano.distanceAt(x-1, y))) - pano.distanceAt(x, y);
        
    }
    
    /**
     * add the constant to the value of the channel
     * @param constant the constant
     * @return a new Channel
     */
    public default ChannelPainter add(float constant){
        return (x,y)-> valueAt(x,y)+ constant;  
    }
    
    /**
     * multiply by a constant to the value of the channel
     * @param constant the constant
     * @return a new Channel
     */
    public default ChannelPainter mul(float constant){
        return (x,y)-> valueAt(x,y) * constant;  
    }
    
    /**
     * substract a constant to the value of the channel
     * @param constant the constant
     * @return a new Channel
     */
    public default ChannelPainter sub(float constant){
        return (x,y)-> valueAt(x,y) - constant;  
    }
   
    /**
     * divide by a constant to the value of the channel
     * @param constant the constant
     * @return a new Channel
     */
    public default ChannelPainter div(float constant){
        return (x,y)-> valueAt(x,y)  / constant;  
    }
    
    /**
     * multiply by a constant to the value of the channel
     * @param constant the constant
     * @return a new Channel
     */
    public default ChannelPainter map(DoubleUnaryOperator op){
        return (x,y)-> (float) op.applyAsDouble(valueAt(x,y));
    }
    
    /**
     * gives the max between 0 and the minimum of the valueAt(x,y) and 1
     * @return a new ChannelPainter
     */
    public default ChannelPainter clamp(){
        return (x,y)-> Math.max(0, Math.min(valueAt(x,y), 1));
    }
    
    /**
     * gives the 1-valueAt(x,y)
     * @return a new ChannelPainter
     */
    public default ChannelPainter revert(){
        return (x,y)-> 1 - valueAt(x,y);
    }
    
    /**
     * give the valueAt(x,y) mod 1
     * @return a new ChannelPainter
     */
    public default ChannelPainter cycling(){
        return (x,y)->valueAt(x,y) % 1;
    }
}
