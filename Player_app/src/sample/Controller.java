package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class Controller {

    //--> VARIABLES DEL ENTORNO GRAFICO (ImageView)
    //Fondo de pantalla
    @FXML private ImageView fondo;
    //Conjunto para el bloque Main
    @FXML private ImageView m1;
    @FXML private ImageView m2;
    @FXML private ImageView m3;
    @FXML private ImageView m4;
    @FXML private ImageView m5;
    @FXML private ImageView m6;
    @FXML private ImageView m7;
    @FXML private ImageView m8;
    @FXML private ImageView m9;
    @FXML private ImageView m10;
    @FXML private ImageView m11;
    @FXML private ImageView m12;
    //Conjunto para el bloque Proc1
    @FXML private ImageView p1;
    @FXML private ImageView p2;
    @FXML private ImageView p3;
    @FXML private ImageView p4;
    @FXML private ImageView p5;
    @FXML private ImageView p6;
    @FXML private ImageView p7;
    @FXML private ImageView p8;
    //Conjunto para el bloque Proc2
    @FXML private ImageView p11;
    @FXML private ImageView p21;
    @FXML private ImageView p31;
    @FXML private ImageView p41;
    @FXML private ImageView p51;
    @FXML private ImageView p61;
    @FXML private ImageView p71;
    @FXML private ImageView p81;
    @FXML private ImageView musiX;
    @FXML private CheckBox main10,proc10,proc101;

    @FXML private MenuItem newMenuItemDELETE = new MenuItem("Delete");

    //Bandera para iniciar programa(cargar listas)
    private static boolean bandera = true;
    //Musica
    private Media musicFile = new Media(Paths.get("LBots.mp3").toUri().toString());
    //Reproductor de musica
    private MediaPlayer mediaPlayer = new MediaPlayer(musicFile);
    //Imagenes para los fondos de pantalla
    private Image celeste = new Image(getClass().getResourceAsStream("/resource/wallpaper/celeste.png"));
    private Image roza = new Image(getClass().getResourceAsStream("/resource/wallpaper/roza.png"));
    private Image dark = new Image(getClass().getResourceAsStream("/resource/wallpaper/dark.png"));
    //Imagenes de control
    private Image imageUP = new Image(getClass().getResourceAsStream("/resource/control/Up.png"));
    private Image imageTURN_LEFT = new Image(getClass().getResourceAsStream("/resource/control/Tleft.png"));
    private Image imageTURN_RIGHT = new Image(getClass().getResourceAsStream("/resource/control/Tright.png"));
    private Image imageLIGHT = new Image(getClass().getResourceAsStream("/resource/control/Light.png"));
    private Image imageSUSPEN = new Image(getClass().getResourceAsStream("/resource/control/Jump.png"));
    private Image imageP1 = new Image(getClass().getResourceAsStream("/resource/control/P1.png"));
    private Image imageP2 = new Image(getClass().getResourceAsStream("/resource/control/P2.png"));
    private Image playMusic = new Image(getClass().getResourceAsStream("/resource/control/play.png"));
    private Image pauseMusic = new Image(getClass().getResourceAsStream("/resource/control/pause.png"));

    private Image xImage = new Image(getClass().getResourceAsStream("/resource/control/pause.png"));

    static Subscriber s;

    static {
        try {
            s = new Subscriber("m20.cloudmqtt.com:12525","fckzxtel","R3Rs1bph3H4R","java_app","esp/test");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    //Lista para seleccion del imagen de control
    private List<String> listMAIN = new ArrayList<>();
    private List<String> listPROC1 = new ArrayList<>();
    private List<String> listPROC11 = new ArrayList<>();
    private static List<Image> listIMAGE = new ArrayList<>();
    private static List<ImageView> listIMAGEVIEW_MAIN = new ArrayList<>();
    private static List<ImageView> listIMAGEVIEW_PROC1 = new ArrayList<>();
    private static List<ImageView> listIMAGEVIEW_PROC11 = new ArrayList<>();

    //Listas de optimizacion de codigo
    private static List<List<ImageView>> listMeSt = new ArrayList<>();
    private static List<List<String> > listMPP = new ArrayList<>();

    //Control
    private String listCONTROL[] = {"UP","TURN_LEFT","TURN_RIGHT","LIGHT","JUMP","P1","P2"};

    public Controller() throws MqttException {
    }


    /**
     * Cambio de fondo de pantalla (Color : Celeste)
     */
    public void Wallpaper_Walle(){
        fondo.setImage(celeste);
    }

    /**
     * Cambio de fondo de pantalla (Color : Roza)
     */
    public void Wallpaper_Eva(){
        fondo.setImage(roza);
    }

    /**
     * Cambio de fondo de pantalla (Color : Oscuro)
     */
    public void Wallpaper_Mo(){
        fondo.setImage(dark);
    }

    /**
     * Seleccion del icono
     */
    public void addIcon(){
        ImageView openView = new ImageView(xImage);
       // openView.setFitWidth(15);
       // openView.setFitHeight(15);
        newMenuItemDELETE.setGraphic(openView);

       //newMenuItemDELETE.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
    }
    /**
     * Reproduccion de musica de fondo
     */
    public void play_music(){
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.READY) || mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)){
            mediaPlayer.play();
            musiX.setImage(pauseMusic);
        }else{
            mediaPlayer.pause();
            musiX.setImage(playMusic);
        }
    }

    /**
     * Metodos graficos-checkbox de seleccion- MAIN
     */
    public void control_MAIN(){
        if (main10.isSelected()){
            proc10.setSelected(false);
            proc101.setSelected(false);
        }
    }

    /**
     * Metodos graficos-checkbox de seleccion- PROC1
     */
    public void control_PROC1() {
         if (proc10.isSelected()) {
             main10.setSelected(false);
             proc101.setSelected(false);
         }
    }

    /**
     * Metodos graficos-checkbox de seleccion- PROC2
     */
    public void control_PROC11() {
        if (proc101.isSelected()) {
            main10.setSelected(false);
            proc10.setSelected(false);
        }
    }

    /**
     * Mensaje de alerta (Please, select a option []MAIN , []PROC1 or []PROC2)
     */
    private void alert_MAIN_PROC(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Please, select a option []MAIN , []PROC1 or []PROC2");
        alert.setContentText("You need to select an option to add a movement!");
        alert.showAndWait();
    }

    /**
     * Mensaje de alerta(Sorry, your xxxxx box is full)
     * @param s Tipo de bloque lleno
     */
    private void alert_LIMIT_MAIN(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        switch (s) {
            case "MAIN":
                alert.setHeaderText("Sorry, your MAIN box is full");
                break;
            case "PROC1":
                alert.setHeaderText("Sorry, your PROC1 box is full");
                break;
            case "PROC2":
                alert.setHeaderText("Sorry, your PROC2 box is full");
                break;
        }
        alert.setContentText("To add another element you must modify \nyour current boxes!");
        alert.showAndWait();
    }

    /**
     * Inicio del programa (Carga imagenes a las listas)
     */
    private void updateALL(){
        if (bandera) {
            addIcon();
            updateIMAGE();
            updateIMAGEVIEW();

        }
        bandera=false;
    }

    /**
     * Metodo para abrir la ventana de instrucciones
     * @throws IOException
     */
    public void passInstructions() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("Instructions.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Instructions");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo para abrir la ventana de colores visuales
     * @throws IOException
     */
    public void passColor() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Color.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 510, 250);
        Stage stage = new Stage();
        stage.setTitle("Color");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo para finalizar la aplicacion
     */
    public void close(){
        exit(0);
    }

    /**
     * Metodo para borrar tablero
     */
    public void deleteAll(){

        listMAIN.clear();

    listPROC1.clear();
        listPROC11.clear();

        for (int asa = 0;asa<listIMAGEVIEW_MAIN.size();asa++){
            listIMAGEVIEW_MAIN.get(asa).setImage(null);
        }

        for (int asa = 0;asa<listIMAGEVIEW_PROC1.size();asa++){
            listIMAGEVIEW_PROC1.get(asa).setImage(null);
        }
        for (int asa = 0;asa<listIMAGEVIEW_PROC11.size();asa++){
            listIMAGEVIEW_PROC11.get(asa).setImage(null);
        }

    }

    /**
     * Actualiza el contenido-url de la ubucacion de las imagenes
     */
    private void updateIMAGE(){
        listIMAGE.add(imageUP);
        listIMAGE.add(imageTURN_LEFT);
        listIMAGE.add(imageTURN_RIGHT);
        listIMAGE.add(imageLIGHT);
        listIMAGE.add(imageSUSPEN);
        listIMAGE.add(imageP1);
        listIMAGE.add(imageP2);
    }

    /**
     * Actualiza el contenido-informacion de en las listas
     */
    private void updateIMAGEVIEW(){
        listMPP.add(listMAIN);    listMPP.add(listPROC1);    listMPP.add(listPROC11);
        listMeSt.add(listIMAGEVIEW_MAIN);   listMeSt.add(listIMAGEVIEW_PROC1);   listMeSt.add(listIMAGEVIEW_PROC11);
        listIMAGEVIEW_MAIN.add(m1);        listIMAGEVIEW_MAIN.add(m2);        listIMAGEVIEW_MAIN.add(m3);        listIMAGEVIEW_MAIN.add(m4);
        listIMAGEVIEW_MAIN.add(m5);        listIMAGEVIEW_MAIN.add(m6);        listIMAGEVIEW_MAIN.add(m7);        listIMAGEVIEW_MAIN.add(m8);
        listIMAGEVIEW_MAIN.add(m9);        listIMAGEVIEW_MAIN.add(m10);        listIMAGEVIEW_MAIN.add(m11);      listIMAGEVIEW_MAIN.add(m12);
        listIMAGEVIEW_PROC1.add(p1);        listIMAGEVIEW_PROC1.add(p2);        listIMAGEVIEW_PROC1.add(p3);        listIMAGEVIEW_PROC1.add(p4);
        listIMAGEVIEW_PROC1.add(p5);        listIMAGEVIEW_PROC1.add(p6);        listIMAGEVIEW_PROC1.add(p7);        listIMAGEVIEW_PROC1.add(p8);
        listIMAGEVIEW_PROC11.add(p11);        listIMAGEVIEW_PROC11.add(p21);        listIMAGEVIEW_PROC11.add(p31);        listIMAGEVIEW_PROC11.add(p41);
        listIMAGEVIEW_PROC11.add(p51);        listIMAGEVIEW_PROC11.add(p61);        listIMAGEVIEW_PROC11.add(p71);        listIMAGEVIEW_PROC11.add(p81);
    }

    /**
     *  Metodo para reorganizar las imagenes segun la imagen que se elimino.
     * @param x
     */
    private void merges(int x){
        for(int c = 0; c < listMPP.get(x).size(); c++){
            switch (listMPP.get(x).get(c)) {
                case "UP":
                    listMeSt.get(x).get(c).setImage(listIMAGE.get(0));
                    break;
                case "TURN_LEFT":
                    listMeSt.get(x).get(c).setImage(listIMAGE.get(1));
                    break;
                case "TURN_RIGHT":
                    listMeSt.get(x).get(c).setImage(listIMAGE.get(2));
                    break;
                case "LIGHT":
                    listMeSt.get(x).get(c).setImage(listIMAGE.get(3));
                    break;
                case "JUMP":
                    listMeSt.get(x).get(c).setImage(listIMAGE.get(4));
                    break;
                case "P1":
                    listMeSt.get(x).get(c).setImage(listIMAGE.get(5));
                    break;
                case "P2":
                    listMeSt.get(x).get(c).setImage(listIMAGE.get(6));
                    break;
            }
            //"UP","TURN_LEFT","TURN_RIGHT","LIGHT","JUMP","P1"
        }
    }

    /**
     * Puente de division de merger(int x)
     * @param MP1P2
     */
    private void mergesStandar(String MP1P2){

        switch (MP1P2) {
            case "M":
                merges(0);
                break;
            case "P1":
                merges(1);
                break;
            case "P2":
                merges(2);
                break;
            default:
                System.out.print("error.. :(");
                break;
        }
    }

    /**
     * Boton hacia Arriba
     */
    public void B_UP(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[0]);
            mergesStandar("M");
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[0]);
            mergesStandar("P1");
        }else if (proc101.isSelected() && listPROC11.size()<8 ){
            listPROC11.add(listCONTROL[0]);
            mergesStandar("P2");
        }else {
            alerta555();
        }
    }

    /**
     * Boton hacia Izquierda
     */
    public void B_TURN_LEFT(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[1]);
            mergesStandar("M");
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[1]);
            mergesStandar("P1");
        }else if (proc101.isSelected() && listPROC11.size()<8 ){
            listPROC11.add(listCONTROL[1]);
            mergesStandar("P2");
        }else {
            alerta555();
        }
    }

    /**
     * Boton hacia Deracha
     */
    public void B_TURN_RIGHT(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[2]);
            mergesStandar("M");
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[2]);
            mergesStandar("P1");
        }else if (proc101.isSelected() && listPROC11.size()<8 ){
            listPROC11.add(listCONTROL[2]);
            mergesStandar("P2");
        }
        else {
            alerta555();
        }
    }

    /**
     * Boton de luz
     */
    public void B_LIGHT(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[3]);
            mergesStandar("M");
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[3]);
            mergesStandar("P1");
        }else if (proc101.isSelected() && listPROC11.size()<8 ){
            listPROC11.add(listCONTROL[3]);
            mergesStandar("P2");
        }else {
            alerta555();
        }
    }

    /**
     * Boton hacia Resorte
     */
    public void B_SUSPEN(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[4]);
            mergesStandar("M");
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[4]);
            mergesStandar("P1");
        }else if (proc101.isSelected() && listPROC11.size()<8 ){
            listPROC11.add(listCONTROL[4]);
            mergesStandar("P2");
        }else {
            alerta555();
        }
    }

    /**
     * Boton PROC1
     */
    public void B_P1(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[5]);
            mergesStandar("M");
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Caution can not add P1 inside P1");
            alert.setContentText("Loop!");
            alert.showAndWait();
        }else if (proc101.isSelected()){
            alert_PP();
        }else {
            alerta555();
        }
    }

    /**
     * Boton PROC2
     */
    public void B_P11(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[6]);
            mergesStandar("M");
        }else if (proc101.isSelected()){
            alert_PP();
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[6]);
            mergesStandar("P1");
        }else {
            alerta555();
        }
    }

    /**
     * Mensaje de Warning para evitar ciclos infinitos (Loop)
     */
    private void alert_PP(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Caution can not add P1 or P2 inside P2");
        alert.setContentText("Loop!");
        alert.showAndWait();
    }

    /**
     * Verificador de seleccion del check-box
     * @return
     */
    private boolean checkMP1P2(){
        return (!main10.isSelected() &&  !proc10.isSelected() &&  !proc101.isSelected());
    }

    /**
     * Guia los mensajes de alerta
     */
    private void alerta555(){
        if (listMAIN.size()>=12) {
            alert_LIMIT_MAIN("MAIN");
        }else if (listPROC1.size()>=8) {
            alert_LIMIT_MAIN("PROC1");
        }else if (listPROC11.size()>=8) {
            alert_LIMIT_MAIN("PROC2");
        }
        else {
            alert_MAIN_PROC();
        }
    }

    /**
     * Conversion de String a Integer para realizar una lista final (Procedimientos)
     * @param stringToConvert
     * @return
     */
    private int convertString_int(String stringToConvert){
        switch (stringToConvert) {
            case "UP":
                return 1;
            case "TURN_LEFT":
                return 2;
            case "TURN_RIGHT":
                return 3;
            case "LIGHT":
                return 4;
            case "JUMP":
                return 5;
            case "P1":
                return 6;
            case "P2":
                return 7;
            default:
                return 0;
        }
    }

    /**
     * Metodo para el envio del procedimiento a seguir segun el "Robot"
     */
    public void PLAY_Send() throws MqttException {
        JSONArray array = new JSONArray();

        if(listMAIN.size()!=0) {
            for (int rF = 0; rF < listMAIN.size(); rF++) {
                if (listMAIN.get(rF).equals("P1")) {
                    for (int p1 = 0; p1 < listPROC1.size(); p1++) {
                        if (listPROC1.get(p1) == "P2") {
                            for (int z = 0; z < listPROC11.size(); z++) {
                                array.add(convertString_int(listPROC11.get(z)));
                            }
                        } else {
                            array.add(convertString_int(listPROC1.get(p1)));
                        }
                    }
                } else if (listMAIN.get(rF).equals("P2")) {
                    for (String aListPROC11 : listPROC11) {
                        array.add(convertString_int(aListPROC11));
                    }
                } else {
                    array.add(convertString_int(listMAIN.get(rF)));
                }
            }
            //JACKSON
            ObjectMapper mapper = new ObjectMapper();//Para poder hacer operaciones JSON
            Process process = new Process();
            process.setProcess(array);
            process.setId("app");

            String jsonInString = null;
            try {
                jsonInString = mapper.writeValueAsString(process);
                System.out.println(jsonInString);//aquí se ve impreso, es lo que eventualmente se envia.
                System.out.println(jsonInString.length());


            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


            s.sendMessage(jsonInString);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Please, select a process");
            alert.setContentText("You need to create a process");
            alert.showAndWait();
        }
    }

    /**
     * Metodo para eliminar movimiento en el MAIN[0][1]
     */
    public void E_M_1(){
        if (listMAIN.size()>=1){
            listMAIN.remove(0);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }

    /**
     * Metodo para eliminar movimiento en el MAIN[0][1]
     */
    public void E_M_2(){
        if (listMAIN.size()>=2){
            listMAIN.remove(1);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }

    /**
     * Metodo para eliminar movimiento en el MAIN[0][2]
     */
    public void E_M_3(){
        if (listMAIN.size()>=3){
            listMAIN.remove(2);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }

    /**
     * Metodo para eliminar movimiento en el MAIN[0][3]
     */
    public void E_M_4(){
        if (listMAIN.size()>=4){
            listMAIN.remove(3);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }

    /**
     * Metodo para eliminar movimiento en el MAIN[1][0]
     */
    public void E_M_5(){
        if (listMAIN.size()>=5){
            listMAIN.remove(4);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    /**
     * Metodo para eliminar movimiento en el MAIN[1][1]
     */
    public void E_M_6(){
        if (listMAIN.size()>=6){
            listMAIN.remove(5);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    /**
     * Metodo para eliminar movimiento en el MAIN[1][2]
     */
    public void E_M_7(){
        if (listMAIN.size()>=7){
            listMAIN.remove(6);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    /**
     * Metodo para eliminar movimiento en el MAIN[1][3]
     */
    public void E_M_8(){
        if (listMAIN.size()>=8){
            listMAIN.remove(7);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    /**
     * Metodo para eliminar movimiento en el MAIN[2][0]
     */
    public void E_M_9(){
        if (listMAIN.size()>=9){
            listMAIN.remove(8);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    /**
     * Metodo para eliminar movimiento en el MAIN[2][1]
     */
    public void E_M_10(){
        if (listMAIN.size()>=10){
            listMAIN.remove(9);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    /**
     * Metodo para eliminar movimiento en el MAIN[2][2]
     */
    public void E_M_11(){
        if (listMAIN.size()>=11){
            listMAIN.remove(10);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    /**
     * Metodo para eliminar movimiento en el MAIN[2][3]
     */
    public void E_M_12(){
        if (listMAIN.size()>=12){
            listMAIN.remove(11);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }

    /**
     * Metodo para eliminar movimiento en el PROC1[0][0]
     */
    public void E_P_1(){
        if (listPROC1.size()>=1){
            listPROC1.remove(0);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC1[0][1]
     */
    public void E_P_2(){
        if (listPROC1.size()>=2){
            listPROC1.remove(1);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC1[0][2]
     */
    public void E_P_3(){
        if (listPROC1.size()>=3){
            listPROC1.remove(2);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC1[0][3]
     */
    public void E_P_4(){
        if (listPROC1.size()>=4){
            listPROC1.remove(3);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC1[1][0]
     */
    public void E_P_5(){
        if (listPROC1.size()>=5){
            listPROC1.remove(4);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC1[1][1]
     */
    public void E_P_6(){
        if (listPROC1.size()>=6){
            listPROC1.remove(5);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC1[1][2]
     */
    public void E_P_7(){
        if (listPROC1.size()>=7){
            listPROC1.remove(6);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC1[1][3]
     */
    public void E_P_8(){
        if (listPROC1.size()>=8){
            listPROC1.remove(7);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }

    //Tablero del PROC2

    /**
     * Metodo para eliminar movimiento en el PROC2[0][0]
     */
    public void E_P_11(){
        if (listPROC11.size()>=1){
            listPROC11.remove(0);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC2[0][1]
     */
    public void E_P_21(){
        if (listPROC11.size()>=2){
            listPROC11.remove(1);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC2[0][2]
     */
    public void E_P_31(){
        if (listPROC11.size()>=3){
            listPROC11.remove(2);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC2[0][3]
     */
    public void E_P_41(){
        if (listPROC11.size()>=4){
            listPROC11.remove(3);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC2[1][0]
     */
    public void E_P_51(){
        if (listPROC11.size()>=5){
            listPROC11.remove(4);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC2[1][1]
     */
    public void E_P_61(){
        if (listPROC11.size()>=6){
            listPROC11.remove(5);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC2[1][2]
     */
    public void E_P_71(){
        if (listPROC11.size()>=7){
            listPROC11.remove(6);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    /**
     * Metodo para eliminar movimiento en el PROC2[1][3]
     */
    public void E_P_81(){
        if (listPROC11.size()>=8){
            listPROC11.remove(7);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }


}