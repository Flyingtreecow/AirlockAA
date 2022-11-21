package airlock.entities;

import airlock.exceptions.PressureException;

public class PressureSensor implements IPressureSensor {
	
	double pressure;
	
	public PressureSensor(double initialPressure) throws PressureException {
            if (initialPressure < 0){
                throw new PressureException("Pressure is negative");    
            }
            
            pressure = initialPressure;
	}
	
	public double getPressure() {
	    return pressure;
	}
	
	public void setPressure(double newPressure) throws PressureException {
	    if (newPressure < 0){
                throw new PressureException("Can't set pressure to be negative"); 
            }
            pressure = newPressure;
	}

	public String toString() {
		return String.format(
			"PressureSensor: pressure: %3.1f bar", getPressure());
	}

}
