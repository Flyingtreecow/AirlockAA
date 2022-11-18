package airlock.entities;

import airlock.exceptions.PressureException;

public class PressureSensor implements IPressureSensor {
	
	double pressure;
	
	public PressureSensor(double initialPressure) throws PressureException {
            if (pressure < 0){
                throw PressureException("Pressure is negative");    
            }
	}
	
	public double getPressure() {
	    return pressure;
	}
	
	public void setPressure(double newPressure) throws PressureException {
	    if (newPressure < 0){
                throw PressureException("Can't set pressure to be negative"); 
            }
            pressure = newPressure;
	}

	public String toString() {
		return String.format(
			"PressureSensor: pressure: %3.1f bar", getPressure());
	}

}
