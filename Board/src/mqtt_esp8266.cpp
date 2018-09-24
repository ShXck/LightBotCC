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
#define PIN D3
#define PINNorte D12
#define PINEste D9
#define PINSur D10
#define PINOeste D13
#define PINPito D8

const char* ssid = "jk";
const char* password =  "abcdefgh";
const char* mqttServer = "m20.cloudmqtt.com";
const int mqttPort = 12525;
const char* mqttUser = "fckzxtel";
const char* mqttPassword = "R3Rs1bph3H4R";
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
void doMove(int jsonArray[],int p);
void T_Left();
void up();
void jump_UP();
void T_Right();
void on_offLight();
bool checkL();
bool checkH();
void prev_now();
void move();
void loadProce(JsonArray& jsonArrayP);
void songWin();
void apagarTodo();
void shearchPos();
//-----------------------------------------^-------------------------------------------------@Sebastian


int matriz[8][8];
int matrizRespaldo[8][8];
int process[800];
int f_now = 0;
int c_now = 0;
int f_prev = 0;
int c_prev = 0;
int coordenada = 0; //Inicio hacia el Norte:0   Este:1   Sur:2   Oeste:3
int contadorDeProcedimiento = 0;
int cANTIDAD_DE_PASOS=0;

bool nuevoProcess=false;
bool nuevaMatriz=false;


void setup() {
  //start(3,3);
  Serial.begin(115200);
  pinMode(PINNorte, OUTPUT);
  pinMode(PINEste, OUTPUT);
  pinMode(PINSur, OUTPUT);
  pinMode(PINOeste, OUTPUT);
  pinMode(PINPito, OUTPUT);

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
      //break;
    } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(2000);
    }

  }
  client.publish(listen_topic1, "Hello from ESP8266");
  client.subscribe(listen_topic1);

  digitalWrite(PINNorte, HIGH);

  int qMinicio[8][8]={
    {1000,1000,1000,1000,1100,1000,1000,1100},{1000,1000,1000,1000,1100,1100,1100,1100},
    {1000,1000,1000,1000,1100,1100,1100,1100},{1000,1000,1000,1000,1100,1000,1000,1100},
    {1100,1100,1100,1100,1000,1000,1000,1000},{1000,1100,1100,1000,1000,1000,1000,1000},
    {1000,1100,1100,1000,1000,1000,1000,1000},{1100,1100,1100,1100,1000,1000,1000,1000}};

  displayMatrix(qMinicio);

}

//Function that processes incoming message
void callback(char* topic, byte* payload, unsigned int length) {
  char message[length];
  for (int i = 0; i < length; i++) {
    message[i] = (char)payload[i];
  }
  JsonObject& root = parseString2JSON(message);
  String id = root["id"];
  if (id.equals("ide")) {
      nuevaMatriz=true;

      JsonArray& jsonArray = root["board"];
      addElement(jsonArray);                                                                                          //carga de la matri<
  }
  else if (id.equals("app")) {
      nuevoProcess =true;

      JsonArray& jsonArrayPROCESS = root["process"];
      contadorDeProcedimiento=0;
      shearchPos();
      apagarTodo();
      digitalWrite(PINNorte, HIGH);
      coordenada=0;

      loadProce(jsonArrayPROCESS);
  }
  else {
    Serial.println("JSON no valido");
  }
  Serial.println(root["id"].asString());
  Serial.println("---------Recibimiento de datos exitosamente--------------");

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
    if (client.connect(clientID, mqttUser, mqttPassword )) { //client.connect(clientID, mqttUser, mqttPassword )
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

bool WinLost(){
int numWL1=0;
int numWL2=0;

  for (int wL=0; wL<8 ;wL++){ //Fila
    for (int lW=0; lW<8 ;lW++){ //Columna
      numWL1 = (matriz[wL][lW]  / 100) % 10; //_#__
      numWL2 = (matriz[wL][lW] / 10) % 10; //__#_

      // Serial.println(String(numWL1)+"<_>"+String(numWL2) );
      if(numWL1==1 && numWL2==0){
        return false;
      }
    }
  }
  return true;
}

//No hay manera de no hacerlo alambrado el tamaño de la matriz a menos de pasar la cantidad de columnas y filas por parámetros, igual no hay problema
void displayMatrix(int matrixArray[8][8]) {


  //showMatriz();
  for (int i = 0; i < 8; i++ ) {
    for (int j = 0; j < 8; j++) {
      String item = String(matrixArray[i][j]);//lo hice con string previendo por si hay nivel 0, lo cual no seria un int valido
      int nivel=(item[0]-'0')-1;
     //Si hay robot
      if ((item[3]-'0')==1)
        matrix.drawPixel(i, j, colores_robot[nivel]);

      //Si hay luz
      else if((item[1]-'0')==1){
        if((item[2]-'0')==1)//si esta encendido
          matrix.drawPixel(i, j, colores_bombillo[3]);
        else//la prende segun el nivel que este
            matrix.drawPixel(i, j, colores_bombillo[nivel]);
      }
      else//es piso comun y corriente
        matrix.drawPixel(i, j, colores_piso[nivel]);
    }
  }
  matrix.show();
}

/*
   Metodo para crear/cargar la matriz
*/
void addElement(JsonArray& jsonArray) {
  for (int i = 0; i < 8; i++) {
    for (int e = 0; e < 8; e++) {
      matriz[i][e] = jsonArray[i][e];
      matrizRespaldo[i][e] = jsonArray[i][e];

      if(matriz[i][e]%10 == 1){
        f_now = i;
        c_now = e;
      }                                                                                          // VERIFICAR QUE ESTO SE PUEDE HACER
    }
  }
}

void shearchPos(){
  for (int i = 0; i < 8; i++) {
    for (int e = 0; e < 8; e++) {
      if(matriz[i][e]%10 == 1){
        f_now = i;
        c_now = e;
      }                                                                                          // VERIFICAR QUE ESTO SE PUEDE HACER
    }
  }
}

/*
Metodo para cargar los pasos
*/
void loadProce(JsonArray& jsonArrayP){
    for (int ix = 0; ix < jsonArrayP.size(); ix++) {
      process[ix]=jsonArrayP[ix];
      Serial.print(String(process[ix])+" ");
  }
  cANTIDAD_DE_PASOS=jsonArrayP.size(); //asigno valor limite
}

/*
Metodo para hacer un movimiento
*/
void doMove(int jsonArray[],int p) {       //Este metodo solo debe hacer solo un paso

    if (jsonArray[p] == 1) {
      up();
    } else if (jsonArray[p] == 2) {
      T_Left();
    } else if (jsonArray[p] == 3) {
      T_Right();
    } else if (jsonArray[p] == 4) {
      on_offLight();
    } else if (jsonArray[p] == 5) {
      jump_UP();
    } else {
      Serial.println("Error de codigo de lectura 13213-ERROR-1232121");
    }
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
  prev_now();

  Serial.print("Move to ");
  if (coordenada == 0) { //Norte
    Serial.println("Nort");
    f_now = f_now - 1;    //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    if (checkL() && checkH()) {  //Limite y altura

      move();
      showUP(coordenada);

    } else {
      f_now = f_now + 1;
    }
  }
  else if (coordenada == 3) { //Este
    Serial.println("East");
    c_now = c_now + 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    if (checkL() && checkH() ) {

      move();
      showUP(coordenada);
    } else {
      c_now = c_now - 1;
    }
  }
  else if (coordenada == 2) { //Sur
    f_now = f_now + 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    Serial.println("South");
    if (checkL() && checkH() ) {

      move();
      showUP(coordenada);
    } else {
      f_now = f_now - 1;
    }
  }
  else if (coordenada == 1) { //Oeste
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
  int n1 = (matriz[f_prev][c_prev] / 10) * 10;
  int n2 = (matriz[f_now][c_now] / 10) * 10 + 1;

  matriz[f_prev][c_prev] = n1;  // colocamos un 0 al final ###0 ->El Robot ya no esta hay...
  matriz[f_now][c_now] = n2;  // colocamos un 1 al final ###1 ->El Robot esta en esta celda dentro de la matriz...

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
  Serial.println("------------------------------> DeltaH: " + String(deltaH));

  if (deltaH >= 0) {
    return true;
  } else {
    Serial.println(">>>>> Error <<<<<< [Impossible jump]");
    return false;
  }
}

/*
   Verificacion la altura para realizar un paso en la matriz
*/
bool checkH_SALTO() {
  int h1 = (matriz[f_prev][c_prev] / 1000);
  int h2 = (matriz[f_now][c_now] / 1000);

  int deltaH_SALTO = h1 - h2;
  Serial.println("------------------------------> DeltaH: " + String(deltaH_SALTO));

  if (deltaH_SALTO==-1 || deltaH_SALTO==1 ) {
    return true;
  } else {
    Serial.println(">>>>> Error <<< [SALTO NO REALIZADO]");
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
  prev_now();

  Serial.print("Move to ");
  if (coordenada == 0) { //Norte
    Serial.println("Nort");
    f_now = f_now - 1;    //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    if (checkL() && checkH_SALTO()) {  //Limite y altura
      move();
      showUP(coordenada);
    } else {
      f_now = f_now + 1;
    }
  }
  else if (coordenada == 3) { //Este
    Serial.println("East");
    c_now = c_now + 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    if (checkL()&& checkH_SALTO()) {

      move();
      showUP(coordenada);
    } else {
      c_now = c_now - 1;
    }
  }
  else if (coordenada == 2) { //Sur
    f_now = f_now + 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    Serial.println("South");
    if (checkL()&& checkH_SALTO()) {

      move();
      showUP(coordenada);
    } else {
      f_now = f_now - 1;
    }
  }
  else if (coordenada == 1) { //Oeste
    c_now = c_now - 1;   //AQUI VERIFICACION DE QUE NO SE SALGA DE LA MATRIZ
    Serial.println("West");
    if (checkL()&& checkH_SALTO()) {
      move();
      showUP(coordenada);
    } else {
      c_now = c_now + 1;
    }
  }
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




void apagarTodo(){
  digitalWrite(PINNorte, LOW);
  digitalWrite(PINEste, LOW);
  digitalWrite(PINSur, LOW);
  digitalWrite(PINOeste, LOW);
}


//Metdos graficos para la placa JK


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
    up();


    c++;
  }
  apagarTodo();
}



void showUP(int coo) {
  delay(500);

  if(coo==0){
    apagarTodo();
    digitalWrite(PINNorte, HIGH);
  }
  if(coo==3){
    apagarTodo();
    digitalWrite(PINEste, HIGH);
  }
  if(coo==2){
    apagarTodo();
    digitalWrite(PINSur, HIGH);
  }
  if(coo==1){
    apagarTodo();
    digitalWrite(PINOeste, HIGH);
  }

}

void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();
  matrix.fillScreen(0);

// verificar si ya jugo y si ya envio un nuevo proceso

if(nuevoProcess==true || nuevaMatriz==true){
  if ( String(matriz[7][7]).length()==4){
    int xwx = process[contadorDeProcedimiento];
    //  Serial.println("< P: "+String(xwx));
    if(xwx==1 || xwx==2 || xwx==3|| xwx==4 || xwx==5){
      //  Serial.println(">>>>>"+String(contadorDeProcedimiento)+"  --- Process:"+String(process[contadorDeProcedimiento]));
      doMove(process,contadorDeProcedimiento);
      displayMatrix(matriz);
      contadorDeProcedimiento++;
      //  Serial.println("~~~~> "+String(contadorDeProcedimiento)+" : "+String(cANTIDAD_DE_PASOS));
      if((contadorDeProcedimiento)==cANTIDAD_DE_PASOS){
        if(WinLost()){
          Serial.println("GANOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
          delay(1500);
          int qM[8][8]={
            {1001,1001,1001,1001,1001,1001,1001,1001},{1001,1000,1000,1001,1001,1000,1000,1001},
            {1001,1000,1000,1001,1001,1000,1000,1001},{1001,1001,1001,1001,1001,1001,1001,1001},
            {1001,1000,1001,1001,1001,1001,1000,1001},{1001,1000,1001,1001,1001,1001,1000,1001},
            {1001,1001,1000,1000,1000,1000,1001,1001},{1001,1001,1001,1001,1001,1001,1001,1001}};
            displayMatrix(qM);
            songWin();
            delay(5000);
            for(int aa = 0;aa<8;aa++){
              for(int aaa = 0;aaa<8;aaa++){
                matriz[aa][aaa]=matrizRespaldo[aa][aaa];
              }
            }
            nuevoProcess=false;
            nuevaMatriz=false;
          //  contadorDeProcedimiento=0;
          }else{
            Serial.println("PERDIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            delay(1500);
            int qM[8][8]={
            {3000,3000,3000,3000,3000,3000,3000,3000},{3000,1000,1000,3000,3000,1000,1000,3000},
            {3000,1000,1000,3000,3000,1000,1000,3000},{3000,3000,3000,3000,3000,3000,3000,3000},
            {3000,3000,1000,1000,1000,1000,3000,3000},{3000,1000,3000,3000,3000,3000,1000,3000},
            {3000,1000,3000,3000,3000,3000,1000,3000},{3000,3000,3000,3000,3000,3000,3000,3000}};
            displayMatrix(qM);
            digitalWrite(PINPito, HIGH);
            delay(5000);
            digitalWrite(PINPito, LOW);
            for(int aa = 0;aa<8;aa++){
              for(int aaa = 0;aaa<8;aaa++){
                matriz[aa][aaa]=matrizRespaldo[aa][aaa];
              }
            }
            //contadorDeProcedimiento=0;
            nuevoProcess=false;
            nuevaMatriz=false;
          }
        }
      }
      displayMatrix(matriz);
      delay(500);
    }else{
      //NADA
    }
  }
}

void songWin(){
  digitalWrite(PINPito, HIGH);
  delay(300);
  digitalWrite(PINPito, LOW);
  delay(300);
  digitalWrite(PINPito, HIGH);
  delay(300);
  digitalWrite(PINPito, LOW);
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




----------->
Pin13 = Este
Pin10 = Sur
Pin9 = Oeste
Pin12 = Norte
Pin8 = Pito
*/
