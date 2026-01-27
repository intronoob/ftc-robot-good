 package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.helpers.ArduinoComms;

 @TeleOp(name = "AutoMaker (Debug)")
public class AutoMaker extends OpMode {
    private ArduinoComms comm;
    private LauncherController launchSystem;
//    private DcMotor roll_in;
//    private DcMotor roll_mid_right;
//    private DcMotor launcher;
//    private Servo trapDoor;


    private RobotMovementController WheelDrive;
    private double MOVPWR = 0.25;
    private double MOV_AMT_INCREASE = 1;
    private double moveAmt = 0.5;
    private double xMoveAmt = 0;
    private double yMoveAmt = 0;
    private double rotMoveAmt = 0;
    @Override
    public void init() {
        comm = new ArduinoComms(hardwareMap,"pin0","pin1","pin2");
        WheelDrive = new RobotMovementController(hardwareMap);
        launchSystem = new LauncherController(hardwareMap);


        telemetry.addData("Status", "Initialized");
        telemetry.update();


    }

    @Override
    public void loop() {
        // ---- RESET ----
        if (gamepad1.leftStickButtonWasReleased()) {
            if(xMoveAmt != 0) {
                comm.sendData(xMoveAmt<<8|0x0<<4 ,40);
            }
            xMoveAmt = 0;
            yMoveAmt = 0;
            rotMoveAmt = 0;
            comm.sendData(0x0F,32);
            comm.sleepMs(1000);
            moveAmt = MOV_AMT_INCREASE;
        } else if (gamepad1.dpadUpWasReleased()) {
            moveAmt += MOV_AMT_INCREASE;
        } else if (gamepad1.dpadDownWasReleased()) {
            moveAmt -= MOV_AMT_INCREASE;
        } else if (gamepad1.aWasReleased()) {
            WheelDrive.moveInches(-moveAmt, 0,0, MOVPWR);
            yMoveAmt -= moveAmt;
        } else if (gamepad1.yWasReleased()) {
            WheelDrive.moveInches(moveAmt, 0,0, MOVPWR);
            yMoveAmt += moveAmt;
        } else if (gamepad1.xWasReleased()) {
            WheelDrive.moveInches(0, -moveAmt, 0, MOVPWR);
            xMoveAmt -= moveAmt;
        } else if (gamepad1.bWasReleased()) {
            WheelDrive.moveInches(0, moveAmt, 0, MOVPWR);
            xMoveAmt += moveAmt;
        } else if (gamepad1.leftBumperWasReleased()) {
            WheelDrive.moveInches(0, 0, moveAmt, MOVPWR);
            rotMoveAmt += moveAmt;
        } else if (gamepad1.rightBumperWasReleased()) {
            WheelDrive.moveInches(0, 0, -moveAmt, MOVPWR);
            rotMoveAmt -= moveAmt;
        } else if (gamepad1.rightStickButtonWasReleased()) {
            launchSystem.runLaunchSequence();
        }

        // ---- TELEMETRY ----
        telemetry.addData("Move Step", "%.2f in", moveAmt);
        telemetry.addData("X Move (in)", "%.2f", xMoveAmt);
        telemetry.addData("Y Move (in)", "%.2f", yMoveAmt);
        telemetry.addData("Rot Move (in)", "%.2f", rotMoveAmt);
        telemetry.update();

    }
}
