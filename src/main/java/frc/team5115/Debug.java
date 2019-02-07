package frc.team5115;

import edu.wpi.first.hal.PortsJNI;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.team5115.robot.Robot;
import frc.team5115.subsystems.Drivetrain;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Debug implements Runnable{

    DriverStation DS;
    PowerDistributionPanel PDP;
    Map<Object, ArrayList<Object>> CANBus;

    NetworkTableEntry voltage;

    double[] current;
    double currentThreshold = 2.5;
    double motorThreshold = 4;

    //0 ok, 1 low, 2 critical
    int batteryState = 0;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl() throws IOException, JSONException {
        try (InputStream is = new URL("http://172.22.11.2:1250/?action=getdevices").openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    public static Map<Object, ArrayList<Object>> returnCANBus(){
        try {
            JSONArray server = readJsonFromUrl().getJSONArray("DeviceArray");
            Map<Object, ArrayList<Object>> CANBus = new HashMap<>();
            for(int i = 0; i < 5/*This should be the number of expected device*/; i++){
                JSONObject current = server.getJSONObject(i);
                ArrayList<Object> metadata = new ArrayList<>();
                metadata.add(current.get("Name"));
                metadata.add(current.get("Model"));
                metadata.add(current.get("SoftStatus"));
                CANBus.put(current.get("UniqID"), metadata);
            }
            return CANBus;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Debug(){
        DS = DriverStation.getInstance();
        PDP = new PowerDistributionPanel();
        current = new double[PortsJNI.getNumPDPChannels()];
        voltage = Robot.tab.add("Voltage", 0).getEntry();
        try {
            CANBus = returnCANBus();
            for(int i = 0; i < CANBus.size(); i++){
                ArrayList<Object> device = CANBus.get(i);
                for(Object value: device){
                    System.out.println(value);
                }

            }
        } catch (Exception e){
            e.printStackTrace();
            DS.reportWarning("Something went wrong while trying to get phoenix diagnosics!", e.getStackTrace());
        }
    }

    private void PDPCheck(){
        for(int i = 0; i < PortsJNI.getNumPDPChannels(); i++){
            switch(i){
                //ports with motor plugged in
                case 0:
                case 1:
                case 14:
                case 15:
                    if(current[i] >  PDP.getCurrent(i) + motorThreshold || current[i] < PDP.getCurrent(i) - motorThreshold){
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
                    }
                    break;
                    //ports returning bad voltages
                case 2:
                case 3:
                case 12:
                case 13:
                    break;
                default:
                    if(current[i] >  PDP.getCurrent(i) + currentThreshold || current[i] < PDP.getCurrent(i) - currentThreshold){
                        DS.reportWarning("Current at PDP port: " + i + "spiked, and not within our thresholds!", false);
                    }
                    break;
            }
            current[i] = PDP.getCurrent(i);
        }
    }

    private void batteryCheck(){
        if(PDP.getVoltage() > 10 && batteryState != 0){
            batteryState = 0;
        } else if((PDP.getVoltage() < 10 && PDP.getVoltage() > 8) && batteryState != 1){
            DS.reportWarning("Battery Low!", false);
            batteryState = 1;
        } else if((PDP.getVoltage() < 7) && batteryState != 2){
            DS.reportError("Battery Critical!", false);
            batteryState = 2;
        }
        voltage.setDouble(PDP.getVoltage());
    }

    public void run(){
        while(true) {
            try {
                PDPCheck();
                batteryCheck();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                DS.reportWarning("Monitor interrupted! stopping thread", e.getStackTrace());
                break;
            }
        }
    }
}