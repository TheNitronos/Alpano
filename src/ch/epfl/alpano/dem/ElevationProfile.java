package ch.epfl.alpano.dem;

/**
 * 
 * @author Samuel Chassot (270955)
 * @author Daniel Filipe Nunes Silva (275197)
 *
 */

import static java.util.Objects.requireNonNull;

import ch.epfl.alpano.GeoPoint;
import ch.epfl.alpano.Preconditions;
import ch.epfl.alpano.Azimuth;
import ch.epfl.alpano.Distance;
import static java.lang.Math.sin;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.asin;

public final class ElevationProfile {
    
    private ContinuousElevationModel elevMod;
    private GeoPoint origin;
    private double azimuth;
    private double length;
    
    private double[][] positions;

    /**
     * represents an altimetric profile corresponding to a great circle
     * @param elevationModel
     * @param origin
     * @param azimuth
     * @param length
     */
    ElevationProfile(ContinuousElevationModel elevationModel, GeoPoint origin,
                                              double azimuth, double length) {
        requireNonNull(elevationModel);
        requireNonNull(origin);
        
        Preconditions.checkArgument(Azimuth.isCanonical(azimuth));
        Preconditions.checkArgument(length > 0);
        
        this.elevMod = elevationModel;
        this.origin = origin;
        this.azimuth = azimuth;
        this.length = length;

        int difference = 4096;
        int precision = (int)Math.ceil(length/difference);
        positions = new double[precision+1][3];
        
        double initLength = 0; 
        for (int i = 0; i < precision; ++i) {
            positions[i] = coordinatesAt(initLength);
            initLength += difference;
        }
        
        positions[precision] = coordinatesAt(length);
    }
    
    /*
    public double elevationAt(double x) {
        Preconditions.checkArgument(x <= length && x >= 0);
        
        GeoPoint point = positionAt(x);
        
        return elevMod.elevationAt(point);
    }
    */
    
    public double elevationAt(double x) {
        Preconditions.checkArgument(x <= length && x >= 0);
        
        GeoPoint point = positionAt(x);
        
        return elevMod.elevationAt(point);
    }
    
    public GeoPoint positionAt(double x) {
        Preconditions.checkArgument(x <= length);
        
        double originLatitude = origin.latitude();
        double originLongitude = origin.longitude();
        double radianLength = Distance.toRadians(x);
        double toMathAzimuth = Azimuth.toMath(azimuth);
        
        double pointLatitude = asin((sin(originLatitude) * cos(radianLength))
                + (cos(originLatitude) * sin(radianLength) * cos(toMathAzimuth)));
        
        double pointLongitude = Azimuth.canonicalize((originLongitude
                -asin((sin(toMathAzimuth)*sin(radianLength))
                        /cos(pointLatitude))+PI))-PI;
        
        GeoPoint point = new GeoPoint(pointLongitude, pointLatitude);
        
        return point;
    }
    
    public double slopeAt(double x) {
        Preconditions.checkArgument(x <= length && x >= 0);
        
        GeoPoint point = positionAt(x);
        
        return elevMod.slopeAt(point);
    }
    
    private double[] coordinatesAt(double x) {
        Preconditions.checkArgument(x <= length);
        
        double originLatitude = origin.latitude();
        double originLongitude = origin.longitude();
        double radianLength = Distance.toRadians(x);
        double toMathAzimuth = Azimuth.toMath(azimuth);
        
        double pointLatitude = asin((sin(originLatitude) * cos(radianLength))
                + (cos(originLatitude) * sin(radianLength) * cos(toMathAzimuth)));
        
        double pointLongitude = Azimuth.canonicalize((originLongitude
                -asin((sin(toMathAzimuth)*sin(radianLength))
                        /cos(pointLatitude))+PI))-PI;
        
        double[] point = new double[] {x, pointLongitude, pointLatitude};
        
        return point;
    }
}
