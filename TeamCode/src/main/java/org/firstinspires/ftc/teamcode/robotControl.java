package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp(name = "robotControl")
public class  robotControl extends OpMode {
    ElapsedTime timer;
    // Motors / servos
    private DcMotor roll_in;
    private DcMotor roll_mid_right;
    private DcMotor launcher;
    private Servo trapDoor;

    private RobotMovementController WheelDrive;
    // Simple state flag so loop runs once
    private double LAUNCH_POWER = 0.52;
    private static final double LAUNCH_POWER_INIT = 0.7;

    private boolean roll_in_state = false;
    private boolean mid_roll_state = false;
    private boolean launching = true;
    private boolean trapdooring = false;

    @Override
    public void init() {
        // Map hardware
        timer = new ElapsedTime();
        roll_in = hardwareMap.get(DcMotor.class, "roll_in");
        roll_mid_right = hardwareMap.get(DcMotor.class, "roll_mid_right");
        launcher = hardwareMap.get(DcMotor.class, "launch");
        trapDoor = hardwareMap.get(Servo.class, "trapdoor");
        WheelDrive = new RobotMovementController(hardwareMap);
        // Motor directions
        roll_in.setDirection(DcMotor.Direction.REVERSE);
        roll_mid_right.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        double drive =  -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double rotate = -gamepad1.right_stick_y; //bc y stick inverts
        WheelDrive.drive(drive*0.1,strafe*0.1,rotate*0.1);
        if(gamepad2.aWasReleased()) {
            roll_in_state = !roll_in_state;
            mid_roll_state = !mid_roll_state;
            roll_in.setPower(roll_in_state ? 1 : 0);
            roll_mid_right.setPower(mid_roll_state ? 1 : 0);
            // sleep(500);
        }
        if(gamepad2.bWasReleased()) {
            trapdooring = !trapdooring;
            trapDoor.setPosition(trapdooring ? 0.3 : 0.0);
        }
        if(gamepad2.xWasReleased()) {
            launching = !launching;
        }
        if(gamepad2.leftBumperWasReleased()) {
            LAUNCH_POWER = gamepad2.right_trigger == 0 ? LAUNCH_POWER_INIT : gamepad2.right_trigger;
        }
        launcher.setPower(launching ? LAUNCH_POWER : 0);
//        if (timer.seconds() > 2.0) {
//            launcher.setPower(launching ? LAUNCH_POWER : 0);
//            timer.reset();
//        } //TIMER EXAMPLE
}
    @Override
    public void stop() {
        // Stop everything
        launcher.setPower(0);
        roll_in.setPower(0);
        roll_mid_right.setPower(0);
        WheelDrive.drive(0,0,0);

        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}
