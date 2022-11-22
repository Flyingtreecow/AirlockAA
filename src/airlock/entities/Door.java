package airlock.entities;

import airlock.exceptions.DoorException;

public class Door implements IDoor{
	
	private static double TOLERANCE = 0.001;
	
	IPressureSensor inSensor;
	IPressureSensor exSensor;
	
	private DoorState state;
	private Double diffInPressure = inSensor.getPressure()- exSensor.getPressure();
        
	public Door(IPressureSensor exSensor, IPressureSensor inSensor, DoorState initialState) throws DoorException {
            try{
               exSensor.getPressure();
               inSensor.getPressure();
            }
            catch(Exception e){
               throw new DoorException("Sensors are not valid");
            }
            inSensor = this.inSensor;
            exSensor = this.exSensor;
            state = initialState;
            
            if(state == DoorState.OPEN &&  (diffInPressure > TOLERANCE || diffInPressure < TOLERANCE)){
               throw  new DoorException("Difference in pressures is too large");
            }
	}
	
	@Override
	public void open() throws DoorException {
            if(state == DoorState.OPEN){
               throw  new DoorException("Door already open");
            }
            if(state == DoorState.CLOSED && (diffInPressure > TOLERANCE || diffInPressure < TOLERANCE)){
               throw  new DoorException("Pressure unequal");
            }
            state = DoorState.OPEN;
	}
	
	@Override
	public void close() throws DoorException {
	    if(state == DoorState.CLOSED){
               throw  new DoorException("Door already closed");
            }
            state = DoorState.CLOSED;
	}

	@Override
	public double getExternalPressure() {
	    return exSensor.getPressure();
	}

	@Override
	public double getInternalPressure() {
	    return inSensor.getPressure();
	}

	@Override
	public boolean isOpen() {
            return state == DoorState.OPEN;
	}

	@Override
	public boolean isClosed() {
	    return state == DoorState.CLOSED;
	}

	public String toString() {
	    return String.format(
	        "Door: state: %s, external pressure: %3.1f bar, internal pressure: %3.1f bar", 
		state, exSensor.getPressure(), inSensor.getPressure());
	}	

}
