package airlock.entities;

import airlock.exceptions.AirLockException;
import airlock.exceptions.DoorException;
import airlock.exceptions.PressureException;

public class AirLock implements IAirLock {

    private IDoor outerDoor;
    private IDoor innerDoor;
    private IPressureSensor lockSensor;

    private AirLockState state;
    private OperationMode mode;

    public AirLock(IDoor externalDoor, IDoor internalDoor, IPressureSensor lockSensor) {
        outerDoor = externalDoor;
        innerDoor = internalDoor;
        this.lockSensor = lockSensor;

        mode = OperationMode.MANUAL;
        if (innerDoor.isClosed() && outerDoor.isClosed()) {
            state = AirLockState.SEALED;
        } else {
            state = AirLockState.UNSEALED;

        }
    }

    @Override
    public void openOuterDoor() throws AirLockException {
        if (outerDoor.isOpen()) {
            throw new AirLockException("Door is already open");
        } else {
            try {
                if (mode == OperationMode.AUTO) {
                    if (innerDoor.isOpen()) {
                        closeInnerDoor();
                    }
                    equaliseWithEnvironmentPressure();
                }
                outerDoor.open();
                state = AirLockState.UNSEALED;
            } catch (DoorException e) {
                throw new AirLockException(e);
            }
        }
    }

    @Override
    public void closeOuterDoor() throws AirLockException {
        try {
            outerDoor.close();
            if (innerDoor.isClosed()) {
                state = AirLockState.SEALED;
            }
        } catch (DoorException e) {
            throw new AirLockException(e);
        }
    }

    @Override
    public void openInnerDoor() throws AirLockException {
        if (innerDoor.isOpen()) {
            throw new AirLockException("Door is already open");
        } else {
            try {
                if (mode == OperationMode.AUTO) {
                    if (outerDoor.isOpen()) {
                        closeOuterDoor();
                    }
                    equaliseWithCabinPressure();
                }
                innerDoor.open();
                state = AirLockState.UNSEALED;
            } catch (DoorException e) {
                throw new AirLockException(new DoorException("InnerDoor Open malfunction"));
            }
        }
    }

    @Override
    public void closeInnerDoor() throws AirLockException {
        try {
            innerDoor.close();
            if (outerDoor.isClosed()) {
                state = AirLockState.SEALED;
            }
        } catch (DoorException e) {
            throw new AirLockException(e);
        }
    }

    @Override
    public void equaliseWithCabinPressure() throws AirLockException {
        if (state == AirLockState.UNSEALED) {
            throw new AirLockException("Airlock is currently unsealed");
        } else {
            try {
                lockSensor.setPressure(innerDoor.getExternalPressure());
            } catch (PressureException e) {
                throw new AirLockException(e);
            }

        }
    }

    @Override
    public void equaliseWithEnvironmentPressure() throws AirLockException {
        if (state == AirLockState.UNSEALED) {
            throw new AirLockException("Airlock is currently unsealed");
        } else {
            try {
                lockSensor.setPressure(outerDoor.getExternalPressure());
            } catch (PressureException e) {
                throw new AirLockException(e);
            }
        }
    }

    @Override
    public void toggleOperationMode() throws AirLockException {
        if (state == AirLockState.UNSEALED) {
            throw new AirLockException("Airlock is currently unsealed");
        } else {
            if (mode == OperationMode.MANUAL) {
                mode = OperationMode.AUTO;
            } else {
                mode = OperationMode.MANUAL;
            }
        }
    }

    @Override
    public boolean isInManualMode() {
        return mode == OperationMode.MANUAL;
    }

    @Override
    public boolean isInAutoMode() {
        return mode == OperationMode.AUTO;
    }

    @Override
    public boolean isOuterDoorClosed() {
        return outerDoor.isClosed();
    }

    @Override
    public boolean isInnerDoorClosed() {
        return innerDoor.isClosed();
    }

    @Override
    public boolean isSealed() {
        return state == AirLockState.SEALED;
    }

    @Override
    public boolean isUnsealed() {
        return state == AirLockState.UNSEALED;
    }

    public String toString() {
        return String.format(
                "Airlock: state: %s, mode: %s",
                state, mode);
    }

}
