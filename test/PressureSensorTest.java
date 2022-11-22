/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import airlock.entities.PressureSensor;
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
public class PressureSensorTest {
    
    public PressureSensorTest() {
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
    double validPressure = 2.0;
    double invalidPressure = -2.0;
    double pressureChange = 5.0;
    
    @Test(expected = PressureException.class)
    public void contructorTestNegativeVal() throws Exception {
        PressureSensor negativeSensor = new PressureSensor(invalidPressure);
    }
    
    public void constructerInitiationTest()  {
        try {
            PressureSensor sensor = new PressureSensor(validPressure);
            sensor.getPressure();
            sensor.setPressure(pressureChange);
        }
        catch (Exception e){
            fail("Sensor not Initialised");
        }
        
    }
    
    @Test 
    public void getPressureTest() throws Exception{
        PressureSensor sensor = new PressureSensor(validPressure);
        assertEquals(validPressure, sensor.getPressure(), 0.01);
        
        sensor.setPressure(pressureChange);
        assertEquals(pressureChange, sensor.getPressure(), 0.01);
    }
    
    @Test(expected = PressureException.class)
    public void setNegativePressureTest() throws Exception{
        PressureSensor sensor = new PressureSensor(validPressure);
        sensor.setPressure(invalidPressure);
    }
    
    @Test
    public void updateInitPressureTest() throws Exception{
        PressureSensor sensor = new PressureSensor(validPressure);
        sensor.setPressure(pressureChange);
        assertEquals(pressureChange, sensor.getPressure(), 0.01);
    }    
}
