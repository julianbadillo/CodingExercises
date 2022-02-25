import static java.lang.Math.exp;
import static java.lang.Math.abs;

class EulerOde {
    
    public static double exEuler(int nb) {
        // your code
    	double T = 1.0, x = 0.0, y = 1.0;
    	double h = T / nb, z = z(x), error = 0;
    	
    	for (int i = 0; i < nb; i++) {
    		y = y + h*f(x, y);
    		x += h;
    		z = z(x);
    		error += abs(y-z)/z;
		}
    	
    	return error / (nb + 1);
    }
    
    static double f(double t, double y){
    	return 2.0 - exp(-4*t) - 2*y;
    }
    static double z(double t){
    	// exact solution
    	return 1 + 0.5*exp(-4*t) - 0.5*exp(-2*t);
    }
}