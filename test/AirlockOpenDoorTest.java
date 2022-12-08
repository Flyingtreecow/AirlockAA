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
       PressureSensor lockSensor = new PressureSensor(1);
       PressureSensor exteriorSensor = new PressureSensor(1);
       PressureSensor interiorSensor = new PressureSensor(1);
       
       Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.OPEN); 
       Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       airlock.toggleOperationMode();
       airlock.openOuterDoor();
       
   }
   @Test
   //Ensure that if operation mode is AUTO and the inner door is open then an attempt is made to close the inner door 
  
   public void autoInnerOpenOuter()throws PressureException,DoorException,AirLockException{
       PressureSensor lockSensor = new PressureSensor(1);
       PressureSensor exteriorSensor = new PressureSensor(1);
       PressureSensor interiorSensor = new PressureSensor(1);
       
       Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
       Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
      
       airlock.toggleOperationMode();
       airlock.openInnerDoor();
       airlock.openOuterDoor();
       assertTrue(airlock.isInnerDoorClosed());
       assertFalse(airlock.isOuterDoorClosed());
      
   }
   @Test
    //Ensure that if operation mode is AUTO and after the inner door is closed then an attempt is made to equalise pressures with the external environment 
    //Ensure that if operation mode is AUTO and after the inner door is closed and pressure has been equalised with the external environment that an attempt is made to open the outer door
   public void autoInnerCloseEqualisePressure()throws PressureException,DoorException,AirLockException{
       PressureSensor lockSensor = new PressureSensor(5);
       PressureSensor exteriorSensor = new PressureSensor(5);
       PressureSensor interiorSensor = new PressureSensor(1);
       
       Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
       Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
       airlock.toggleOperationMode();
       airlock.openInnerDoor();
       airlock.openOuterDoor();
       assertTrue(airlock.isInnerDoorClosed());
       assertEquals(externalDoor.getExternalPressure(), lockSensor.getPressure(), 0.01);
       assertFalse(airlock.isOuterDoorClosed());
       
   }
   @Test
   //Ensure that if operation mode is MANUAL then an attempt is made to open the outer door
   //Ensure that if no ex exceptions are thrown that the airlock state becomes UNSEALED
   public void manualOpen()throws PressureException,DoorException,AirLockException{
       PressureSensor lockSensor = new PressureSensor(1);
       PressureSensor exteriorSensor = new PressureSensor(1);
       PressureSensor interiorSensor = new PressureSensor(1);
       
       Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
       Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
       airlock.openOuterDoor();
       assertTrue(airlock.isInManualMode());
       assertFalse(airlock.isOuterDoorClosed());
       assertTrue(airlock.isUnsealed());
       
   }
    @Test 
    //Ensure that if any exceptions are thrown and the airlock was SEALED when  openOuterDoor was called, that the airlock remains SEALED.
    //Ensure that all DoorExceptions are caught and then rethrown encapsulated in AirLockExceptions
    public void airlockRemainsSealedOuter()throws PressureException,DoorException,AirLockException{
        
        PressureSensor lockSensor = new PressureSensor(5);
        PressureSensor exteriorSensor = new PressureSensor(1);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        try{airlock.openOuterDoor();
        }
        catch(AirLockException e){
            assertTrue(airlock.isSealed());
        }
        catch(Exception e){
         fail("Door Exception not rethrown as airlockException");
        }
    }
    @Test (expected = AirLockException.class)
    //Ensure that openInnerDoor throws an AirLockException if openInnerDoor is called while the inner door is already open.
    public void InnerDoorAlreadyOpen()throws PressureException,DoorException,AirLockException{
        PressureSensor lockSensor = new PressureSensor(1);
        PressureSensor exteriorSensor = new PressureSensor(1);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.OPEN);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
        airlock.toggleOperationMode();
        airlock.openInnerDoor();
    }
    @Test
    //Ensure that if operation mode is AUTO and the outer door is open then an attempt is made to close the outer door 
    public void autoOuterOpenInner()throws PressureException,DoorException,AirLockException{
       PressureSensor lockSensor = new PressureSensor(1);
       PressureSensor exteriorSensor = new PressureSensor(1);
       PressureSensor interiorSensor = new PressureSensor(1);
       
       Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
       Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
      
       airlock.toggleOperationMode();
       airlock.openOuterDoor();
       airlock.openInnerDoor();
       assertTrue(airlock.isOuterDoorClosed());
       assertFalse(airlock.isInnerDoorClosed());
   }
    @Test
    //Ensure that if operation mode is AUTO and after the outer door is closed then an attempt is made to equalise pressures with the internal cabin 
    //Ensure that if operation mode is AUTO and after the outer door is closed and pressure has been equalised with the internal cabin that an attempt is made to open the inner door
    public void autoOuterCloseEqualisePressure()throws PressureException,DoorException,AirLockException{
       PressureSensor lockSensor = new PressureSensor(5);
       PressureSensor exteriorSensor = new PressureSensor(1);
       PressureSensor interiorSensor = new PressureSensor(5);
       
       Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
       Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
       airlock.toggleOperationMode();
       airlock.openOuterDoor();
       airlock.openInnerDoor();
       assertTrue(airlock.isOuterDoorClosed());
       assertEquals(internalDoor.getExternalPressure(), lockSensor.getPressure(), 0.01);
       assertFalse(airlock.isInnerDoorClosed());
       
   }
   @Test
   //Ensure that if operation mode is MANUAL then an attempt is made to open the inner door
   //Ensure that if no ex exceptions are thrown that the airlock state becomes UNSEALED
    public void manualOpenInner()throws PressureException,DoorException,AirLockException{
       PressureSensor lockSensor = new PressureSensor(1);
       PressureSensor exteriorSensor = new PressureSensor(1);
       PressureSensor interiorSensor = new PressureSensor(1);
       
       Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
       Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
       AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
       
       airlock.openInnerDoor();
       assertTrue(airlock.isInManualMode());
       assertFalse(airlock.isInnerDoorClosed());
       assertTrue(airlock.isUnsealed());
   }
   @Test
   //Ensure that if any exceptions are thrown and the airlock was SEALED when  openInnerDoor was called, that the airlock remains SEALED.
   //Ensure that all DoorExceptions are caught and then rethrown encapsulated in AirLockExceptions
    public void airlockRemainsSealedInner()throws PressureException,DoorException,AirLockException{
        
        PressureSensor lockSensor = new PressureSensor(5);
        PressureSensor exteriorSensor = new PressureSensor(1);
        PressureSensor interiorSensor = new PressureSensor(1);
       
        Door externalDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED); 
        Door internalDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
       
        AirLock airlock = new AirLock(externalDoor, internalDoor, lockSensor);
        
        try{airlock.openInnerDoor();}
        catch(AirLockException e){
            assertTrue(airlock.isSealed());
        }
        catch(Exception e){
         fail("Door Exception not rethrown as airlockException");
        }
    }
    
}
    

