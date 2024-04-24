package com.example.chessboard;

import java.io.OutputStream;
import com.fazecast.jSerialComm.SerialPort;

public class Arduino{
    public static void main(String[] args) {
        sendSignalToArduino(1);
    }

    public static boolean sendSignalToArduino(int signal) {
        SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length == 0) {
            return false;
        }

        SerialPort arduinoPort = null;

        for (SerialPort port : ports) {
            if (port.getSystemPortName().equals("COM5")) {
                arduinoPort = port;
                break;
            }
        }

        if (arduinoPort == null) {
            return false;
        }

        if (!arduinoPort.openPort()) {
            return false;
        }

        try {
            OutputStream outputStream = arduinoPort.getOutputStream();
            outputStream.write(signal);
            outputStream.flush();

            Thread.sleep(1000);

            arduinoPort.closePort();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}