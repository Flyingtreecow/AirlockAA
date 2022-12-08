/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import airlock.entities.AirLock;
import airlock.entities.Door;
import airlock.entities.DoorState;
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
public class SystemLvlTest {
    
    public SystemLvlTest() {
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
    //Pass through airlock in auto mode from inside to outside when external environment pressure is less than internal cabin pressure
    public void Systemtest1()throws  PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(1);
        PressureSensor interiorSensor = new PressureSensor(5);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.toggleOperationMode();
        try{
            airlock.openInnerDoor();
            airlock.openOuterDoor();
            airlock.closeOuterDoor();
        }
        catch(Exception e){
            fail("Exception Thrown");
        }
    }
    @Test
    //Pass through airlock in auto mode from outside to inside when external environment pressure is greater than internal cabin pressure
    public void Systemtest2()throws  PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        airlock.toggleOperationMode();
        try{
            airlock.openOuterDoor();
            airlock.openInnerDoor();
            airlock.closeInnerDoor();
            assertTrue(airlock.isSealed());
        }
        catch(Exception e){
            fail("Exception Thrown");
        }
    }
    @Test
    //Pass through airlock from inside to outside in manual mode when external environment pressure is greater than internal cabin pressure
    public void Systemtest3()throws  PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(5);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        try{
            airlock.equaliseWithCabinPressure();
            airlock.openInnerDoor();
            airlock.closeInnerDoor();
            airlock.equaliseWithEnvironmentPressure();
            airlock.openOuterDoor();
            airlock.closeOuterDoor();
            assertTrue(airlock.isSealed());
        }
        catch(Exception e){
            fail("Exception Thrown");
        }
    }
    @Test
    //Pass through airlock from outside to inside in manual mode when external environment pressure is less than internal cabin pressure
     public void Systemtest4()throws  PressureException,DoorException,AirLockException{
         PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(1);
        PressureSensor interiorSensor = new PressureSensor(5);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        try{
            airlock.equaliseWithEnvironmentPressure();
            airlock.openOuterDoor();
            airlock.closeOuterDoor();
            airlock.equaliseWithCabinPressure();
            airlock.openInnerDoor();
            airlock.closeInnerDoor();
            assertTrue(airlock.isSealed());
        }
        catch(Exception e){
            fail("Exception Thrown");
        }
    }
}
