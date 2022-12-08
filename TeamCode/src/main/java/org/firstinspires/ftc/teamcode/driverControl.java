package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;

@TeleOp
public class driverControl extends LinearOpMode {

    //initialize motors
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    //init sensors/camera
    OpenCvCamera camera;



    @Override
    public void runOpMode() {

        //init motors
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        //double check which motors are reversed, assumption is right-side
        /*
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

         */

        telemetry.addLine("Initialized");


        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");



            // Things to Change;
            //

            //Original Code
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;








            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if(gamepad1.dpad_left){
                frontLeftPower += -0.1;
                backLeftPower += -0.1;
                frontRightPower += 0.1;
                backRightPower += 0.1;
            }
            if(gamepad1.dpad_right){
                frontLeftPower += 0.1;
                backLeftPower += 0.1;
                frontRightPower += -0.1;
                backRightPower += -0.1;
            }

            //*0.5 to set more reasonable speed
            frontRight.setPower(frontRightPower*0.5);
            frontLeft.setPower(frontLeftPower*0.5);
            backRight.setPower(backRightPower*0.5);
            backLeft.setPower(backLeftPower*0.5);

            //last line
            telemetry.addData("At Position",  "%7d :%7d",
                    frontLeft.getCurrentPosition(),

                    backLeft.getCurrentPosition());

            telemetry.addData("Speed", "%7d",
                    frontRight.getPower(),
                    backRight.getPower()
                    );
            telemetry.update();
        }
    }
}

