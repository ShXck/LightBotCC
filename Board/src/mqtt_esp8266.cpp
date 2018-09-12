#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <Adafruit_GFX.h>
#include <Adafruit_NeoMatrix.h>
#include <Adafruit_NeoPixel.h>
#include <SPI.h>
#include <ArduinoJson.h>

//Global variables, can be simplified to reduce static memory use
#define PIN D11
const char* ssid = "Familia-Mora-Ramirez";
const char* password =  "tekila2013";
const char* mqttServer = "m12.cloudmqtt.com";
const int mqttPort = 16115;
const char* mqttUser = "nnsmxwti";
const char* mqttPassword = "37KKt6sf8N6L";
const char* listen_topic1 = "esp/test";
const char* publish_topic1 = "esp/echo";
const char * clientID = "ESP8266Client";
WiFiClient espClient;
PubSubClient client(espClient);
void callback(char* topic, byte* payload, unsigned int length);
Adafruit_NeoMatrix matrix = Adafruit_NeoMatrix(8, 8, PIN,
  NEO_MATRIX_TOP     + NEO_MATRIX_RIGHT +
  NEO_MATRIX_COLUMNS + NEO_MATRIX_PROGRESSIVE,
  NEO_GRB            + NEO_KHZ800);
const uint16_t colors[] = {
  matrix.Color(255, 0, 0), matrix.Color(0, 255, 0), matrix.Color(0, 0, 255) };



void setup() {
  matrix.begin();
  matrix.setTextWrap(false);
  matrix.setBrightness(100);
  matrix.setTextColor(colors[0]);


  Serial.begin(115200);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }
  Serial.println("Connected to the WiFi network");

  client.setServer(mqttServer, mqttPort);
  client.setCallback(callback);

  while (!client.connected()) {
    Serial.println("Connecting to MQTT...");

    if (client.connect(clientID, mqttUser, mqttPassword )) {

      Serial.println("connected");

    } else {

      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);

    }
  }

  client.publish(listen_topic1, "Hello from ESP8266");
  client.subscribe(listen_topic1);

}


//Function that processes incoming message
void callback(char* topic, byte* payload, unsigned int length) {

  char char_array[length];
  for (int i = 0; i < length; i++) {
    char_array[i]=(char)payload[i];
  }

  //The buffer size does not match with the size of the incomming string, it's calculated by the utility found in https://arduinojson.org/assistant/
  const size_t bufferSize = JSON_ARRAY_SIZE(768) + JSON_OBJECT_SIZE(2) + 1730;
  DynamicJsonBuffer jsonBuffer(bufferSize);
  JsonObject& root = jsonBuffer.parseObject(char_array);
  jsonBuffer.clear();
 if (!root.success()) {
   Serial.println("parseObject() failed");
 }


JsonArray& jsonArray = root["process"];
Serial.println(jsonArray.get<int>(0));
Serial.println(jsonArray.size());
Serial.println(root["iD"].asString());

  // //echo del mensaje
  // const char* m = message.c_str();
  // //Serial.println(m);
  // client.publish(publish_topic1, m);
  Serial.println("-----------------------");

}
/**
@brief Function for reconnecting to mqtt broker if connection is lost.
*/

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Attempt to connect
    if (client.connect(clientID)) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      //client.publish("outTopic", "hello world");
      // ... and resubscribe
      client.subscribe(listen_topic1);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(1000);
    }
  }
}




int x    = matrix.width();
int pass = 0;

void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  matrix.fillScreen(0);
  matrix.setCursor(x, 0);
  matrix.print(F("AK7"));
  if(--x < -36) {
    x = matrix.width();
    if(++pass >= 3) pass = 0;
    matrix.setTextColor(colors[pass]);
  }
  matrix.show();
  delay(100);
}
