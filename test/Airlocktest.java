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
    //h.	toggleOperationMode
    //	ensure that toggleOperationModethrows an ‘AirLockException’ if it is called while the airlock is not SEALED
    //	ensure that if no exception is thrown that the airlock’s operational mode is switched. (from MANUAL to AUTO or AUTO to MANUAL)
    @Test (expected = AirLockException.class)
    public void toggleOperationModeException()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.OPEN);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor,OperationMode.MANUAL);
       
        airlock.toggleOperationMode();
        airlock.closeInnerDoor();
       
                
    }
    @Test 
    public void toggleOperationMode()throws PressureException,DoorException,AirLockException{
        PressureSensor extPressureSensor = new PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
        PressureSensor extPressureSensor2 = new PressureSensor(1);
        PressureSensor intPressureSensor2 = new PressureSensor(1);
        Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
        PressureSensor lockSensor = new PressureSensor(1);
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor,OperationMode.MANUAL);
       
        assertTrue(airlock.isInManualMode());
        airlock.toggleOperationMode();
        assertTrue(airlock.isInAutoMode());
    }
}
