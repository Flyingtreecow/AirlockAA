import java.util.Scanner;

import airlock.entities.AirLock;
import airlock.entities.Door;
import airlock.entities.DoorState;
import airlock.entities.IAirLock;
import airlock.entities.IDoor;
import airlock.entities.IPressureSensor;
import airlock.entities.PressureSensor;
import airlock.exceptions.AirLockException;

public class Main {

	public static void main(String[] args) {
		try {
			double exteriorPressure = 1.0;
			double lockPressure = 1.0;
			double interiorPressure = 1.0;
			
			IPressureSensor exteriorSensor = new PressureSensor(exteriorPressure);
			IPressureSensor lockSensor     = new PressureSensor(lockPressure);
			IPressureSensor interiorSensor = new PressureSensor(interiorPressure);
			
			IDoor exteriorDoor = new Door(exteriorSensor, lockSensor, DoorState.CLOSED);
			IDoor interiorDoor = new Door(interiorSensor, lockSensor, DoorState.CLOSED);
			
			IAirLock airLock = new AirLock(exteriorDoor, interiorDoor, lockSensor);
			
			String menuFormatString = 
			"%s \n Exterior %s \n Lock %s \n Interior %s \n Exterior %s, \n Interior %s \n Select option: \n OX - open external door \n OI - open internal door \n \n CX - close external door \n CI - close internal door \n \n SX - set external pressure \n SI - set internal pressure \n \n EX - equalise lock with external pressure \n EI - equalise lock with internal pressure \n \n TM - toggle operation mode \n \n  Q  - quit 	Selection: ";
			
			Scanner scanner = new Scanner(System.in);
			//testLoop
			boolean finished = false;
			
			while (!finished) {		
				String ans = null;
				
				try {
					System.out.print(String.format(menuFormatString, 
						airLock,
						exteriorSensor, lockSensor, interiorSensor,
						exteriorDoor, interiorDoor));
					
					ans = scanner.nextLine();
					
					switch (ans.toUpperCase()) {
					
						case "OX" :					
							airLock.openOuterDoor();
							break;
							
						case "OI" :
							airLock.openInnerDoor();
							break;
							
						case "CX" :
							airLock.closeOuterDoor();
							break;
							
						case "CI" :
							airLock.closeInnerDoor();
							break;
							
						case "SX" :
							System.out.println("Enter external pressure: ");
							ans = scanner.nextLine();
							double exP = Double.valueOf(ans).doubleValue();
							exteriorSensor.setPressure(exP);
							if (exteriorDoor.isOpen()) {
								lockSensor.setPressure(exP);
								if (interiorDoor.isOpen()) {
									interiorSensor.setPressure(exP);
								}
							}
							break;
						
						case "SI" :
							System.out.println("Enter internal pressure: ");
							ans = scanner.nextLine();
							double inP = Double.valueOf(ans).doubleValue();
							interiorSensor.setPressure(inP);
							if (interiorDoor.isOpen()) {
								lockSensor.setPressure(inP);
								if (exteriorDoor.isOpen()) {
									exteriorSensor.setPressure(inP);
								}
							}
							break;
							
						case "EX" :
							airLock.equaliseWithEnvironmentPressure();
							break;
	
						case "EI" :
							airLock.equaliseWithCabinPressure();
							break;
	
						case "TM" :
							System.out.println("Toggle operation mode");
							airLock.toggleOperationMode();
							break;
							
						case "Q" :
							finished = true;
							break;						
							
						default:
							System.out.printf("Unrecognised option: %s\n", ans);
					}		
				}
				catch (AirLockException e) {
					System.err.println(e.getMessage());
				}
			} //while
			scanner.close();
		}
		catch (Exception e) {
			System.err.println("General error: " + e.getMessage());				
		}
		System.out.println("\nExiting\n");
	}

}
