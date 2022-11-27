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
public class AirlockOpenDoorTest {
    
    public AirlockOpenDoorTest() {
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
   public void constructor(){
       
   }
   @Test (expected = AirLockException.class)
   //Ensure that openOuterDoor throws an AirLockException if openOuterDoor is called while the outer door is already open.
   public void outerDoorAlreadyOpen()throws PressureException,DoorException,AirLockException{
       PressureSensor extPressureSensor = new PressureSensor(1);
       PressureSensor intPressureSensor = new PressureSensor(1);
       Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
       
       PressureSensor extPressureSensor2 = new PressureSensor(1);
       PressureSensor intPressureSensor2 = new PressureSensor(1);
       Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
       PressureSensor lockSensor = new PressureSensor(1);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor,OperationMode.AUTO);
       airlock.openOuterDoor();
       
   }
   @Test
   //Ensure that if operation mode is AUTO and the inner door is open then an attempt is made to close the inner door 
  
   public void autoInnerOpenOuter()throws PressureException,DoorException,AirLockException{
       PressureSensor extPressureSensor = new PressureSensor(1);
       PressureSensor intPressureSensor = new PressureSensor(1);
       Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
       PressureSensor extPressureSensor2 = new PressureSensor(5);
       PressureSensor intPressureSensor2 = new PressureSensor(1);
       Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.CLOSED);
       
       PressureSensor lockSensor = new PressureSensor(1);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor,OperationMode.AUTO);  
       airlock.openInnerDoor();
       airlock.openOuterDoor();
       assertTrue(airlock.isInnerDoorClosed());
       assertTrue(internalDoor.getExternalPressure() == internalDoor.getInternalPressure());
       assertTrue(airlock.isOuterDoorClosed());
      
   }
 //  @Test
    //Ensure that if operation mode is AUTO and after the inner door is closed then an attempt is made to equalise pressures with the external environment 
   public void autoInnerCloseEqualisePressure()throws PressureException,DoorException,AirLockException{
       PressureSensor extPressureSensor = new PressureSensor(5);
       PressureSensor intPressureSensor = new PressureSensor(5);
       Door externalDoor = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
       
       PressureSensor extPressureSensor2 = new PressureSensor(1);
       PressureSensor intPressureSensor2 = new PressureSensor(1);
       Door internalDoor = new Door(extPressureSensor2, intPressureSensor2, DoorState.OPEN);
       
       PressureSensor lockSensor = new PressureSensor(1);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor,OperationMode.AUTO);
       airlock.openOuterDoor();
       assertTrue(airlock.isInnerDoorClosed());
       
       assertTrue(intPressureSensor2.getPressure() == 5);
   }
   
   
   
   
}
