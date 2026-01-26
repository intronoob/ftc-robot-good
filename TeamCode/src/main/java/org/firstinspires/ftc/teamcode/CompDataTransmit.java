package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;

@I2cDeviceType
@DeviceProperties(
        name = "ESP32 Comp Monitor",
        description = "ESP32 I2C data transmitter",
        xmlTag = "esp32"
)
public class CompDataTransmit extends I2cDeviceSynchDevice<I2cDeviceSynch> {

    /** Registers on the ESP32 side */
    public enum Register {
        DATA(0x28);

        public final int bVal;

        Register(int value) {
            this.bVal = value;
        }
    }

    public CompDataTransmit(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {
        super(deviceClient, deviceClientIsOwned);

        this.deviceClient.setI2cAddress(I2cAddr.create7bit(0x28));
        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Other;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {
        return "ESP32 Comp Monitor";
    }

    /** Send a short (2 bytes) to ESP32 */
    public void writeShort(Register reg, short value) {
        deviceClient.write(reg.bVal, TypeConversion.shortToByteArray(value));
    }

    /** Send raw bytes (string, packet, etc.) */
    public void writeBytes(Register reg, byte[] data) {
        deviceClient.write(reg.bVal, data);
    }
}
