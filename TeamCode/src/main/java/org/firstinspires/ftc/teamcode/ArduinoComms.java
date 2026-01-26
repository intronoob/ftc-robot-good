package org.firstinspires.ftc.teamcode.helpers;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArduinoComms {

    private final DigitalChannel clk;
    private final DigitalChannel dat;
    private final DigitalChannel en;

    private final ElapsedTime timer = new ElapsedTime();

    public ArduinoComms(HardwareMap hardwareMap,
                                 String clkName,
                                 String datName,
                                 String enName) {

        clk = hardwareMap.get(DigitalChannel.class, clkName);
        dat = hardwareMap.get(DigitalChannel.class, datName);
        en  = hardwareMap.get(DigitalChannel.class, enName);

        clk.setMode(DigitalChannel.Mode.OUTPUT);
        dat.setMode(DigitalChannel.Mode.OUTPUT);
        en.setMode(DigitalChannel.Mode.OUTPUT);

        clk.setState(false);
        dat.setState(false);
        en.setState(false);
    }

    public void sleepMs(long ms) {
        timer.reset();
        while (timer.milliseconds() < ms) {
            // intentionally empty (OpMode-safe delay)
        }
    }

    public void sendData(int datVal, int length) {
        en.setState(true);

        for (int i = 0; i < length; i++) {
            dat.setState((datVal & 1) == 1);

            clk.setState(true);
            clk.setState(false);

            datVal >>= 1;
        }

        en.setState(false);
    }
}
