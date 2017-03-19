package ch.epfl.alpano.summit;

/**
 * 
 * @author Samuel Chassot (270955)
 * @author Daniel Filipe Nunes Silva (275197)
 *
 */

import ch.epfl.alpano.GeoPoint;

public final class Summit {
    
    private String name;
    private GeoPoint position;
    private int elevation;
    
    /**
     * Creates an instance of a Summit with a name, a position and an elevation
     * @param name
     * @param position
     * @param elevation
     */
    public Summit(String name, GeoPoint position, int elevation) {
        this.name = name;
        this.position = position;
        this.elevation = elevation;
    }

    /**
     * @return the name
     */
    public String name() {
        return name;
    }

    /**
     * @return the position
     */
    public GeoPoint position() {
        return position;
    }

    /**
     * @return the elevation
     */
    public int elevation() {
        return elevation;
    }
    
    /**
     * display the summit as "EIGER (8.0053,46.5775) 3970"
     * name (position) elevation
     */
    @Override
    public String toString(){
        return String.format(name + " " + position.toString() + " " + elevation);
    }
}
