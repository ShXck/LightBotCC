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
const char* ssid = "OnePlus 3";
const char* password =  "sebasmora";
const char* mqttServer = "m12.cloudmqtt.com";
const int mqttPort = 16115;
const char* mqttUser = "nnsmxwti";
const char* mqttPassword = "37KKt6sf8N6L";
const char* listen_topic1 = "esp/test";
const char * clientID = "ESP8266Cl**hient";
WiFiClient espClient;
PubSubClient client(espClient);
Adafruit_NeoMatrix matrix = Adafruit_NeoMatrix(8, 8, PIN,
  NEO_MATRIX_TOP     + NEO_MATRIX_RIGHT +
  NEO_MATRIX_COLUMNS + NEO_MATRIX_PROGRESSIVE,
  NEO_GRB            + NEO_KHZ800);


const uint16_t colores_piso[]={matrix.Color(255, 255, 255), matrix.Color(255,20,147), matrix.Color(255, 0, 0)};
const uint16_t colores_bombillo[]={matrix.Color(0,0,255), matrix.Color(70,130,180), matrix.Color(0,255,255),matrix.Color(255,255,0)};
const uint16_t colores_robot[]={matrix.Color(0,255,150) ,matrix.Color(20,255,100),matrix.Color(50,255,20)};

void callback(char* topic, byte* payload, unsigned int length);
JsonObject& parseString2JSON(char json[]);
void displayMatrix(int matrixArray[8][8]);


void setup() {
  matrix.begin();
  matrix.setTextWrap(false);
  matrix.setBrightness(200);


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
  char message[length];
  for (int i = 0; i < length; i++) {
    message[i]=(char)payload[i];
  }

  JsonObject& root = parseString2JSON(message);
  String id = root["id"];
  if(id.equals("ide")){
      JsonArray& jsonArray = root["board"];
      /*llamar lógica de cuando le entra el mapa*/
  }
  else if (id.equals("app")) {
      JsonArray& jsonArray = root["process"];
    /* llamar lógica de cuando le entra un proceso*/
  }
  else{
    Serial.println("JSON no valido");
  }



  Serial.println(root["id"].asString());
  Serial.println("-----------------------");

}

JsonObject& parseString2JSON(char json[]){
  //The buffer size does not match with the size of the incomming string, it's calculated by the utility found in https://arduinojson.org/assistant/
  const size_t bufferSize = JSON_ARRAY_SIZE(768) + JSON_OBJECT_SIZE(2) + 1730;
  DynamicJsonBuffer jsonBuffer(bufferSize);
  JsonObject& root = jsonBuffer.parseObject(json);
  jsonBuffer.clear();
  if (!root.success()) {
   Serial.println("parseo fallido");
  }
  return root;
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

//No hay manera de no hacerlo alambrado el tamaño de la matriz a menos de pasar la cantidad de columnas y filas por parámetros, igual no hay problema
void displayMatrix(int matrixArray[8][8]){
    for(int i=0;i<8;i++ ){
        for(int j=0;j<8;j++) {
            if(matrixArray[i][j]==1)
                matrix.drawPixel(i, j, colores_robot[0]);
            if(matrixArray[i][j]==2)
                matrix.drawPixel(i, j, colores_robot[1]);
            if(matrixArray[i][j]==3)
                matrix.drawPixel(i, j, colores_robot[2]);
        }
    }
    matrix.show();
}


// int x    = matrix.width();
// int pass = 0;

void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  matrix.fillScreen(0);
  int ejem[8][8]={{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}};
  displayMatrix(ejem);
  delay(100);
}
