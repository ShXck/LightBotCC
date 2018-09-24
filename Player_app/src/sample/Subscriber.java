package sample;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */
public class Subscriber implements MqttCallback {

    private final int qos = 2;
    private String topic;
    private MqttClient client;

    /**
     * Constructor
     * @param server
     * @param username
     * @param password
     * @param clientId
     * @param topic
     * @throws MqttException
     */
    public Subscriber(String server,String username,String password,String clientId,String topic) throws MqttException {
        this.topic=topic;
        server="tcp://"+server;
        this.client = new MqttClient(server, clientId, new MemoryPersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: "+server);
        this.client.setCallback(this);
        this.client.connect(connOpts);
        this.client.subscribe(this.topic, qos);
    }

    /**
     * Metodo para enviar msj
     * @param payload
     * @throws MqttException
     */
    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
       // System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
    }
}