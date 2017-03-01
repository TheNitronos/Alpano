package ch.epfl.alpano.dem;

import ch.epfl.alpano.Interval2D;
import ch.epfl.alpano.Preconditions;

import static java.util.Objects.requireNonNull;

/**
 * 
 * @author Samuel Chassot (270955)
 * @author Daniel Filipe Nunes Silva (275197)
 *
 */

final class CompositeDiscreteElevationModel implements DiscreteElevationModel {
    
    private final DiscreteElevationModel dem1;
    private final DiscreteElevationModel dem2;
    
    
    CompositeDiscreteElevationModel(DiscreteElevationModel dem1, DiscreteElevationModel dem2){
        
        requireNonNull(dem1);
        requireNonNull(dem2);

        this.dem1 = dem1;
        this.dem2 = dem2;
    }
    
    @Override
    public void close() throws Exception {
        dem1.close();
        dem2.close();

    }

    @Override
    public Interval2D extent() {
          
        Interval2D inter1 = dem1.extent();
        Interval2D inter2 = dem2.extent();
        
       return inter1.union(inter2);
    }

    @Override
    public double elevationSample(int x, int y) {
        Interval2D inter1 = dem1.extent();
        Interval2D inter2 = dem2.extent();
        Preconditions.checkArgument(inter1.contains(x, y) || inter2.contains(x, y));
        if (inter1.contains(x, y)){
            return dem1.elevationSample(x, y);
        } else {
            return dem2.elevationSample(x, y);
        }
        
    }

}