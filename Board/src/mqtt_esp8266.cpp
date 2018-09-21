//----------------------------------------------v-------------------------------------------@Sebastian
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
const char* ssid = "AndroidAP";
const char* password =  "pnbz4650";
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


const uint16_t colores_piso[] = {matrix.Color(255, 255, 255), matrix.Color(255, 20, 147), matrix.Color(255, 0, 0)};
const uint16_t colores_bombillo[] = {matrix.Color(0, 0, 255), matrix.Color(70, 130, 180), matrix.Color(0, 255, 255), matrix.Color(255, 255, 0)};
const uint16_t colores_robot[] = {matrix.Color(0, 255, 150) , matrix.Color(20, 255, 100), matrix.Color(50, 255, 20)};

void callback(char* topic, byte* payload, unsigned int length);
JsonObject& parseString2JSON(char json[]);
void displayMatrix(int matrixArray[8][8]);
void espiral(int vueltas);
void showUP(int coo);
void addElement(JsonArray& jsonArray);
void doMove(JsonArray& jsonArray);
void T_Left();
void up();
void copiarMatriz();
void jump_UP();
void T_Right();
void on_offLight();
bool checkL();
bool checkH();
void prev_now();
void move();
//-----------------------------------------^-------------------------------------------------@Sebastian


int matriz[8][8];
int matrizRespaldo[8][8];
int f_now = 0;
int c_now = 0;
int f_prev = 0;
int c_prev = 0;
int coordenada = 2; //Inicio hacia el Norte:0   Este:1   Sur:2   Oeste:3

void setup() {
Serial.begin(115200);
  pinMode(D2, OUTPUT);
  pinMode(D3, OUTPUT);
  pinMode(D4, OUTPUT);
  pinMode(D5, OUTPUT);

  //--------------------v------------------@Sebastian
  matrix.begin();
  matrix.setTextWrap(false);
  matrix.setBrightness(200);


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

  //-------------------^--------------------@Sebastian
}

//------------------------------v----------------------@Sebastian


//Function that processes incoming message
void callback(char* topic, byte* payload, unsigned int length) {
  char message[length];
  for (int i = 0; i < length; i++) {
    message[i] = (char)payload[i];
  }

  JsonObject& root = parseString2JSON(message);
  String id = root["id"];
  if (id.equals("ide")) {
    JsonArray& jsonArray = root["board"];
    /*llamar lógica de cuando le entra el mapa*/
    addElement(jsonArray);                                                                                                     //carga de la matri<
  }
  else if (id.equals("app")) {
    JsonArray& jsonArray = root["process"];
    /* llamar lógica de cuando le entra un proceso*/
    doMove(jsonArray);

  }
  else {
    Serial.println("JSON no valido");
  }



  Serial.println(root["id"].asString());
  Serial.println("-----------------------");

}

JsonObject& parseString2JSON(char json[]) {
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
void displayMatrix(int matrixArray[8][8]) {
  for (int i = 0; i < 8; i++ ) {
    for (int j = 0; j < 8; j++) {
      if (matrixArray[i][j] == 1)
        matrix.drawPixel(i, j, colores_robot[0]);
      if (matrixArray[i][j] == 2)
        matrix.drawPixel(i, j, colores_robot[1]);
      if (matrixArray[i][j] == 3)
        matrix.drawPixel(i, j, colores_robot[2]);
    }
  }
  matrix.show();
}


// int x    = matrix.width();
// int pass = 0;

//-------------------------------^------------------------------@Sebastian

void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  matrix.fillScreen(0);
  int ejem[8][8] = {{1, 2, 3, 4, 5, 6, 7, 8}, {1, 2, 3, 4, 5, 6, 7, 8}, {1, 2, 3, 4, 5, 6, 7, 8}, {1, 2, 3, 4, 5, 6, 7, 8}, {1, 2, 3, 4, 5, 6, 7, 8}, {1, 2, 3, 4, 5, 6, 7, 8}, {1, 2, 3, 4, 5, 6, 7, 8}, {1, 2, 3, 4, 5, 6, 7, 8}};
  displayMatrix(ejem);
  delay(100);
}
void espiral(int vueltas) { //Como posible representacion del que el jugador gano
  int c = 0;
  while (c < vueltas) {
    delay(100);
    T_Left();
    up();
    delay(100);
    T_Left();
    delay(100);
    up();
    T_Left();
    delay(100);
    up();
    T_Left();
    delay(100);
    c++;
  }
}

void showUP(int coo) {
  delay(200);
  digitalWrite(coo + 2, LOW);
  delay(500);
  digitalWrite(coo + 2, HIGH);
  delay(500);
  digitalWrite(coo + 2, LOW);
  delay(500);
  digitalWrite(coo + 2, HIGH);
  delay(200);
}

/*
   Metodo para crear la matriz
*/
void addElement(JsonArray& jsonArray) {                                                                                                   // revisar el &

  for (int i = 0; i < 8; i++) {
    for (int e = 0; e < 8; e++) {
      matriz[i][e] = jsonArray[i][e];                                                                                             // VERIFICAR QUE ESTO SE PUEDE HACER
    }
  }
  copiarMatriz();
//  Serial.println(matriz[0][0]);
//  jsonArray.prettyPrintTo(Serial);
}

void doMove(JsonArray& jsonArray) {
  for (int r = 0; r < jsonArray.size(); r++) {
    Serial.println("Contador: "+String(r));
    if (jsonArray[r] == 1) {
      up();
    } else if (jsonArray[r] == 2) {
      //T_Left();
    } else if (jsonArray[r] == 3) {
      //T_Right();
    } else if (jsonArray[r] == 4) {
      on_offLight();
    } else if (jsonArray[r] == 5) {
      jump_UP();
    } else {
      Serial.println("Error de codigo de lectura 132131232121");
    }

  }
}


void copiarMatriz() {
  //matrizRespaldo = matriz;
}

/*
   Metodo para imprimir la matriz en el Monitor Serial
*/
void ShowElement() {
  Serial.println("Show matriz...");
  String res;
  for (int i1 = 0; i1 < 8; i1++) {
    res = "";
    for (int e1 = 0; e1 < 8; e1++) {
      res = res + String(matriz[i1][e1]) + "|";
    }
    Serial.println(res);
  }
  Serial.println("");
}

/*
   Metodo para colocar la posicion inicial del robot
*/
void start(int fila, int columna) {
  //digitalWrite(2, HIGH);  //Norte
  Serial.println("Start in [" + String(fila) + "][" + String(columna) + "]");
  f_now = fila;  c_now = columna;
  int a = ( matriz[f_now][c_now] / 10) * 10 + 1;
  matriz[fila][columna] = a;  //Realiza el reemplazo en la matriz
}

/*
   Metodo para que el robot realice una traslacion(movimiento) en la matriz..
   ---El metodo cuenta con las verificaciones necesarias
*/
void up() {
  Serial.print("Move to ");
  prev_now();
  if (coordenada == 2) { //Norte
    Serial.println("Nort");
    f_now = f_now - 1;    //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    if (checkL() && checkH() ) {
      move();
      showUP(coordenada);
    } else {
      f_now = f_now + 1;
    }
  }
  else if (coordenada == 1) { //Este
    Serial.println("East");
    c_now = c_now + 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    if (checkL() && checkH() ) {
      move();
      showUP(coordenada);
    } else {
      c_now = c_now - 1;
    }

  }
  else if (coordenada == 0) { //Sur
    f_now = f_now + 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    Serial.println("South");
    if (checkL() && checkH() ) {
      move();
      showUP(coordenada);
    } else {
      f_now = f_now - 1;
    }

  }
  else if (coordenada == 3) { //Oeste
    c_now = c_now - 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    Serial.println("West");
    if (checkL() && checkH() ) {
      move();
      showUP(coordenada);
    } else {
      c_now = c_now + 1;
    }
  }
}

/*
   Metodo para realizar un movimiento en la matriz
*/
void move() { //Hacer verificaciones del altura........
  Serial.println("Ready move..");
  matriz[f_prev][c_prev] = (matriz[f_prev][c_prev] / 10) * 10;  // colocamos un 0 al final ###0 ->El Robot ya no esta hay...
  matriz[f_now][c_now] = (matriz[f_now][c_now] / 10) * 10 + 1;  // colocamos un 1 al final ###1 ->El Robot esta en esta celda dentro de la matriz...
}

/*
   Metodo para actualizar filas y columnas
*/
void prev_now() {
  f_prev = f_now;
  c_prev = c_now;
}

/*
   Metodo para la verificacion de intevalos de la matriz (LIMITES)
*/
bool checkL() {
  if (f_now > 7 || f_now < 0  || c_now > 7 || c_now < 0 ) {
    Serial.println(">>>>> Error <<<<<< [Matrix range exceeded]");
    return false; //Error(salio de la matriz)
  } else {
    return true; // NO hay problemas
  }
}

/*
   Verificacion la altura para realizar un paso en la matriz
*/
bool checkH() {
  int h1 = (matriz[f_prev][c_prev] / 1000);
  int h2 = (matriz[f_now][c_now] / 1000);

  int deltaH = h1 - h2;
  Serial.println("DeltaH: " + String(deltaH));

  if (deltaH >= -1) {
    return true;
  } else {
    Serial.println(">>>>> Error <<<<<< [Impossible jump]");
    return false;
  }

}

/*
   Metodo para encender o apagar el bombillo
*/
void on_offLight() {
  int l1 = (matriz[f_now][c_now]  / 100) % 10; //_#__
  int l2 = (matriz[f_now][c_now] / 10) % 10; //__#_
  int m11 = (matriz[f_now][c_now]  / 100);
  if (l1 == 1) {
    if (l2 == 0) {
      int new_num = (m11 * 10 + 1) * 10 + matriz[f_now][c_now] % 10;
      matriz[f_now][c_now] = new_num;

    } else if (l2 == 1) {
      int new_num = (m11 * 100) + matriz[f_now][c_now] % 10;
      matriz[f_now][c_now] = new_num;

    } else {
      Serial.println("Error! (#Light)");
    }
  } else {
    Serial.println("Without Light :B ");
  }

}

/*
   Metodo para realizar un salto
*/
void jump_UP() {
  //verificar altura siguiente_usar su vista y f_now y c_now
  Serial.println("Salto...");
  up();

}

/*
   Metodo para cambia el sentido derecho de hacia donde observa el robot (Usar luces del cuadro pequeño -JK-)
*/
void T_Right() {
  Serial.println("Turn Right.../n :"+String(coordenada));
  if (coordenada == 3) {
  //  digitalWrite(coordenada + 2, LOW);
    coordenada = 0;
  //  digitalWrite(coordenada + 2, HIGH);
    //delay(500);
  }
  else {
    //digitalWrite(coordenada + 2, LOW);
    coordenada++;
    //digitalWrite(coordenada + 2, HIGH);
    //delay(500);
  }
}
/*
   Metodo para cambia el sentido izquierdo de hacia donde observa el robot (Usar luces del cuadro pequeño -JK-)
*/
void T_Left() {
  Serial.println("Turn Left.../n :"+String(coordenada));
  if (coordenada == 0) {
    //digitalWrite(coordenada + 2, LOW);
    coordenada = 3;
    //digitalWrite(coordenada + 2, HIGH);
    //delay(500);
  }
  else {
    //digitalWrite(coordenada + 2, LOW);
    coordenada--;
    //digitalWrite(coordenada + 2, HIGH);
    //delay(500);
  }
}

/**
   Reglas de interpretacion de los valores dentro de la matriz:
   -> Posicion: {1,2,3} , Siendo el 1 el nivel "normal=0" (piso) , 2 un nivel de "1" , 3 un nivel de "2"
          _____
      ____|-3-|____
  _1__|-2-|   |-2-|__1_

  -> Bombillo: {1,0} ; 1: hay bombillo <> 0: no hay bombillo.

  -> Encendido: {1,0} ; 1: bombillo encendido <> 0: bombillo apagado.

  -> Robot: {1,0} ; 1: Robot en esta celda <> 0: Ausencia de Robot en la celda.

  EJEMPLO : 3101
  Significado> Bloque 2do nivel.
               Hay un bombillo aqui.
               Él cual esta apagado.
               El Robot se encuentra aqui.

  #Nota: No se puede tener la combinación: #01# -> Pues no contiene logica.(No hay bombillo pero esta encendido en esta celda) :v

*/
