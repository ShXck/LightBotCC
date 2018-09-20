package org.tec.comp;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws MqttException {
        SwingUtilities.invokeLater(new IDE_Window());

    }
}
