package airlock.entities;

import airlock.exceptions.DoorException;

public class Door implements IDoor{
	
	private static double TOLERANCE = 0.001;
	
	IPressureSensor inSensor;
	IPressureSensor exSensor;
	private DoorState state;
	
        
	public Door(IPressureSensor exSensor, IPressureSensor inSensor, DoorState initialState) throws DoorException {
            if (exSensor == null || inSensor == null){
                throw new DoorException("Sensors are not valid");
            }  
            
            this.inSensor = inSensor;
            this.exSensor = exSensor;
            state = initialState;
            
            if(state == DoorState.OPEN && DiffInPressure()) {
               throw  new DoorException("Door is open with too big of a difference in pressure");
            }
	}
	
	@Override
	public void open() throws DoorException {
            if(state == DoorState.OPEN){
               throw  new DoorException("Door already open");
            }
      
            if(state == DoorState.CLOSED && DiffInPressure()){
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
        private boolean  DiffInPressure (){
            double diffInPressure = Math.abs(inSensor.getPressure()- exSensor.getPressure());
            return diffInPressure > TOLERANCE;
        }

	public String toString() {
	    return String.format(
	        "Door: state: %s, external pressure: %3.1f bar, internal pressure: %3.1f bar", 
		state, exSensor.getPressure(), inSensor.getPressure());
	}	
       
}
