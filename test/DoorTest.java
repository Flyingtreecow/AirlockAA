/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import airlock.entities.Door;
import airlock.entities.DoorState;
import airlock.entities.PressureSensor;
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
public class DoorTest {
    
    public DoorTest() {
    }
   
   
    @BeforeClass
    public static void setUpClass() throws PressureException,DoorException {
      //PressureSensor extPressureSensor = new  PressureSensor(1);
      //PressureSensor intPressureSensor = new PressureSensor(1);
      //Door door = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
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

  
            
    @Test (expected = DoorException.class)
    public void doorValidSensorTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor;
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
    }
    @Test (expected = DoorException.class)
    public void opendoorPressureUnequalTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
    }
    
    @Test 
    public void validDoorTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
        door.getExternalPressure();
        door.isOpen(); 
    }
    
    @Test (expected = DoorException.class)
    public void doorAlreadyOpenTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
        
        door.open();
    }
     @Test (expected = DoorException.class)
    public void doorOpenWhenPressuresUnequalTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(5);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
        
        door.open();
    }
    
    @Test 
    public void doorOpenTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
        
        door.open();
        assertEquals(true, door.isOpen());
    }
    
    @Test (expected = DoorException.class)
    public void doorCloseWhenAlreadyClosed()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
    
        door.close();
    }
    @Test 
    public void doorCloseTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
        
        door.close();
        assertEquals(true, door.isClosed());
    }
    
    
    @Test
    public void doorGetExtPressureTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
        
        assertEquals(5, door.getExternalPressure(), 0.01);
        
    }
    public void doorGetIntPressureTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(5);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
        
        assertEquals(1, door.getInternalPressure(), 0.01);
        
    }
    
    @Test
    public void doorIsOpenTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.OPEN);
        
        assertTrue(door.isOpen());
        door.close();
        assertFalse(door.isOpen());
        
       
    }
     @Test
    public void doorIsClosedTest()throws PressureException,DoorException{
        PressureSensor extPressureSensor = new  PressureSensor(1);
        PressureSensor intPressureSensor = new PressureSensor(1);
        Door door = new Door(extPressureSensor, intPressureSensor, DoorState.CLOSED);
        
        assertTrue(door.isClosed());
        door.open();
        assertFalse(door.isClosed());
    }
}
