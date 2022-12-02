/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import airlock.entities.AirLock;
import airlock.entities.Door;
import airlock.entities.DoorState;
import airlock.entities.OperationMode;
import airlock.entities.PressureSensor;
import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.exceptions.PressureException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author angry
 */
public class Airlocktest {
    
    public Airlocktest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    @Test
    //Ensure that a valid fully initialised AirLock is returned. (Both airlock state and operation mode are initialised correctly)
    //Ensure that initial operational mode is set to MANUAL.
    public void validAirlockCreated() throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(1);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.OPEN);
       
      
        try {
            AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
            assertTrue(airlock.isUnsealed());
            assertTrue(airlock.isInManualMode());
        }
        catch (Exception e) {
            fail("Airlock is not fully initialised");
        }
    }
    @Test
    //Ensure that initial airlock state is set to SEALED if both doors are CLOSED, and otherwise UNSEALED
    public void airlockIsSealedIfBothDoorsClosed() throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(1);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        assertTrue(airlock.isSealed());
        airlock.openOuterDoor();
        assertTrue(airlock.isUnsealed());
    }
    
    @Test
    //Ensure that closeOuterDoor attempts to close the outer door
    //Ensure that if the inner door is also closed, closeOuterDoor sets the airlock state to SEALED
     public void CloseOuterDoor()throws PressureException,DoorException,AirLockException{
         PressureSensor lockSensor = new PressureSensor(1);
         PressureSensor exteriorSensor = new PressureSensor(1);
         PressureSensor interiorSensor = new PressureSensor(1);
       
         Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.OPEN); 
         Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
         AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
         airlock.closeOuterDoor();
         assertTrue(airlock.isOuterDoorClosed());
         assertTrue(airlock.isSealed());
     }
     @Test
    //Ensure that all DoorExceptions are caught and then rethrown encapsulated in AirLockExceptions
     public void CloseDoorExceptions()throws PressureException,DoorException,AirLockException{
         PressureSensor lockSensor = new PressureSensor(1);
         PressureSensor exteriorSensor = new PressureSensor(1);
         PressureSensor interiorSensor = new PressureSensor(1);
       
         Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
         Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
         AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        try{airlock.closeInnerDoor();
        }
        catch(AirLockException e){
            assertTrue(airlock.isInnerDoorClosed());
        }
        catch(Exception e){
         fail(" Inner door Exception not rethrown as airlockException");
        }
        try{airlock.closeOuterDoor();
        }
        catch(AirLockException e){
            assertTrue(airlock.isOuterDoorClosed());
        }
        catch(Exception e){
         fail("Outer door Exception not rethrown as airlockException");
        }
        
        
     }
     
     
     
    @Test
    //Ensure that closeInnerDoor attempts to close the inner door
    //Ensure that if the outer door is also closed, closeInnerDoor sets the airlock state to SEALED
     public void CloseInnerDoor()throws PressureException,DoorException,AirLockException{
         PressureSensor lockSensor = new PressureSensor(1);
         PressureSensor exteriorSensor = new PressureSensor(5);
         PressureSensor interiorSensor = new PressureSensor(1);
       
         Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
         Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.OPEN);
       
       
         AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
         
         airlock.closeInnerDoor();
         assertTrue(airlock.isInnerDoorClosed());
         assertTrue(airlock.isSealed());
        
     }
    
     
     
    @Test (expected = AirLockException.class)
    //ensure that equaliseWithEnvironmentPressure throws an ‘AirlockNotSealedException’ if it is called while the airlock state is not SEALED
    public void EqualisewithEnviromentException()throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.OPEN);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        airlock.equaliseWithEnvironmentPressure();
    }
    
    @Test
    //ensure that if no exception is thrown that the lock pressure is set to the same as the exterior environment pressure (outer doors external pressure)
    public void EqualisewithEnviromentPressureSensor()throws PressureException,DoorException,AirLockException{
         PressureSensor lockSensor = new PressureSensor(1);
         PressureSensor exteriorSensor = new PressureSensor(5);
         PressureSensor interiorSensor = new PressureSensor(1);
       
         Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
         Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.toggleOperationMode();
        airlock.equaliseWithEnvironmentPressure();
        
        assertTrue(lockSensor.getPressure() == exteriorSensor.getPressure());
        
    }
    
    @Test (expected = AirLockException.class)
    //ensure that equaliseWithCabinPressure throws an ‘AirlockNotSealedException’ if it is called while the airlock state is not SEALED
    public void EqualisewithCabinException() throws PressureException,DoorException,AirLockException{
         PressureSensor lockSensor = new PressureSensor(1);
         PressureSensor exteriorSensor = new PressureSensor(5);
         PressureSensor interiorSensor = new PressureSensor(1);
       
         Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
         Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.OPEN);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        airlock.toggleOperationMode();
        airlock.equaliseWithCabinPressure();
        
    }
    @Test
    //ensure that if no exception is thrown that the lock pressure is set to the same as the interior cabinpressure (inner doors external pressure)
     public void EqualisewithCabinPressureSensor()throws PressureException,DoorException,AirLockException{
         PressureSensor lockSensor = new PressureSensor(1);
         PressureSensor exteriorSensor = new PressureSensor(5);
         PressureSensor interiorSensor = new PressureSensor(1);
       
         Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
         Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
         AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
         
         airlock.toggleOperationMode();
         airlock.equaliseWithCabinPressure();
        
         assertTrue(lockSensor.getPressure() == interiorSensor.getPressure());
        
    }
    
      
    @Test (expected = AirLockException.class)
     //	ensure that toggleOperationModethrows an ‘AirLockException’ if it is called while the airlock is not SEALED
    public void toggleOperationModeException()throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.OPEN);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
        airlock.toggleOperationMode();
        airlock.closeInnerDoor();
       
                
    }
    @Test
     //	ensure that if no exception is thrown that the airlock’s operational mode is switched. (from MANUAL to AUTO or AUTO to MANUAL)
    public void toggleOperationMode()throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
        assertTrue(airlock.isInManualMode());
        airlock.toggleOperationMode();
        assertTrue(airlock.isInAutoMode());
    }
    
    @Test
    //	ensure that isInManualMode returns TRUE if the airlocks operational mode is MANUAL and false if it in AUTO operation mode
    public void IsinManualMode()throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        assertTrue(airlock.isInManualMode());
        airlock.toggleOperationMode();
        assertFalse(airlock.isInManualMode());
        
    }
    @Test
    //ensure that isInAutoMode returns TRUE if the airlocks operational mode is AUTO and false if it in MANUAL operation mode
   
    public void IsinAutoMode()throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
         
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        assertFalse(airlock.isInAutoMode());
        airlock.toggleOperationMode();
        assertTrue(airlock.isInAutoMode());
    }
    @Test
    //ensure that isOuterDoorClosed returns true if the outer door is closed and false if it is open
    //ensure that isInnerDoorClosed returns true if the outer door is closed and false if it is open
    public void isDoorClosed()throws  PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
         
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.toggleOperationMode();
        
        assertTrue(airlock.isOuterDoorClosed());
        assertTrue(airlock.isInnerDoorClosed());
        
        airlock.openOuterDoor();
        assertFalse(airlock.isOuterDoorClosed());
        
        airlock.openInnerDoor();
        assertFalse(airlock.isInnerDoorClosed());
    }
    
}
