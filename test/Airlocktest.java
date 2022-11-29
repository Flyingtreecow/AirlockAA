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
    //Ensure that closeOuterDoor attempts to close the outer door
    //Ensure that if the inner door is also closed, closeOuterDoor sets the airlock state to SEALED

     public void CloseOuterDoor()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.closeOuterDoor();
        assertTrue(airlock.isOuterDoorClosed());
        assertTrue(airlock.isSealed());
     }
     
     
    @Test
    //Ensure that closeInnerDoor attempts to close the inner door
    //Ensure that if the outer door is also closed, closeInnerDoor sets the airlock state to SEALED
     public void CloseInnerDoor()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.OPEN);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.closeInnerDoor();
        assertTrue(airlock.isInnerDoorClosed());
        assertTrue(airlock.isSealed());
        
     }
    
     
     
    @Test (expected = AirLockException.class)
    //ensure that equaliseWithEnvironmentPressure throws an ‘AirlockNotSealedException’ if it is called while the airlock state is not SEALED
    public void EqualisewithEnviromentException()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.OPEN);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        airlock.equaliseWithEnvironmentPressure();
    }
    
    @Test
    //ensure that if no exception is thrown that the lock pressure is set to the same as the exterior environment pressure (outer doors external pressure)
    public void EqualisewithEnviromentPressureSensor()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.toggleOperationMode();
        airlock.equaliseWithEnvironmentPressure();
        
        assertTrue(lockSensor.getPressure() == extPressureSensor.getPressure());
        
    }
    
    @Test (expected = AirLockException.class)
    //ensure that equaliseWithCabinPressure throws an ‘AirlockNotSealedException’ if it is called while the airlock state is not SEALED
    public void EqualisewithCabinException() throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.OPEN);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.toggleOperationMode();
        airlock.equaliseWithCabinPressure();
        
    }
    @Test
    //ensure that if no exception is thrown that the lock pressure is set to the same as the interior cabinpressure (inner doors external pressure)
     public void EqualisewithCabinPressureSensor()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.toggleOperationMode();
        airlock.equaliseWithCabinPressure();
        
        assertTrue(lockSensor.getPressure() == extPressureSensor2.getPressure());
        
    }
    
      
    @Test (expected = AirLockException.class)
     //	ensure that toggleOperationModethrows an ‘AirLockException’ if it is called while the airlock is not SEALED
    public void toggleOperationModeException()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.OPEN);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
        airlock.toggleOperationMode();
        airlock.closeInnerDoor();
       
                
    }
    @Test
     //	ensure that if no exception is thrown that the airlock’s operational mode is switched. (from MANUAL to AUTO or AUTO to MANUAL)
    public void toggleOperationMode()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
        assertTrue(airlock.isInManualMode());
        airlock.toggleOperationMode();
        assertTrue(airlock.isInAutoMode());
    }
    
    @Test
    //	ensure that isInManualMode returns TRUE if the airlocks operational mode is MANUAL and false if it in AUTO operation mode
    public void IsinManualMode()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        assertTrue(airlock.isInManualMode());
        airlock.toggleOperationMode();
        assertFalse(airlock.isInManualMode());
        
    }
    @Test
    //ensure that isInAutoMode returns TRUE if the airlocks operational mode is AUTO and false if it in MANUAL operation mode
   
    public void IsinAutoMode()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        assertFalse(airlock.isInAutoMode());
        airlock.toggleOperationMode();
        assertTrue(airlock.isInAutoMode());
    }
    
}
