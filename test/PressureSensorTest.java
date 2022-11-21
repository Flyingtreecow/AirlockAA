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
    
    @Test(expected = PressureException.class)
    public void contructorTestNegativeVal() throws Exception {
        PressureSensor negativeSensor = new PressureSensor(-2);
    }
    
    public void constructerInitiationTest() throws Exception {
        PressureSensor Sensor = new PressureSensor(2);
        Sensor.getPressure();
        Sensor.setPressure(10);
    }
    
    @Test 
    public void getPressureTest() throws Exception{
        double initialPressure = 2.0;
        PressureSensor Sensor = new PressureSensor(initialPressure);
        assertEquals(Sensor.getPressure(), initialPressure,0.01);
        
        double updatedPressure = 5.0;
        Sensor.setPressure(updatedPressure);
        assertEquals(Sensor.getPressure(), updatedPressure, 0.01);
        
    }
    
    @Test(expected = PressureException.class)
    public void setNegativePressureTest() throws Exception{
        PressureSensor Sensor = new PressureSensor(2);
        Sensor.setPressure(-20);
    }
    
    @Test
    public void updateInitPressureTest() throws Exception{
        double updatePressure = 10.0;
        PressureSensor Sensor = new PressureSensor(2);
        Sensor.setPressure(updatePressure);
        assertEquals(Sensor.getPressure(), updatePressure,0.01);
        
        
    }
        
}
