package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    //variable del entorno grafico
    @FXML private ImageView fondo;
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

    @FXML private ImageView p1;
    @FXML private ImageView p2;
    @FXML private ImageView p3;
    @FXML private ImageView p4;
    @FXML private ImageView p5;
    @FXML private ImageView p6;
    @FXML private ImageView p7;
    @FXML private ImageView p8;


    @FXML private CheckBox music,main10,proc10;

    static boolean bandera = true;


    //musica de fondo -----------------------------> Posible error por ruta propia de PC (home/josenunez/IdeaProjects....)
    Media musicFile = new Media("file:///home/josenunez/IdeaProjects/LightBotCC/src/resource/music/WaLLe_Music.mp3");
    MediaPlayer mediaPlayer = new MediaPlayer(musicFile);

    //Imagenes para los fondos de pantalla
    Image imageWALLE = new Image(getClass().getResourceAsStream("/resource/wallpaper/walle.jpg"));
    Image imageMO = new Image(getClass().getResourceAsStream("/resource/wallpaper/mo.jpg"));
    Image imageEVA = new Image(getClass().getResourceAsStream("/resource/wallpaper/eva.jpg"));
    Image imageNULL = new Image(getClass().getResourceAsStream("/resource/wallpaper/smooth.jpg"));

    //Imagenes de control
    Image imageUP = new Image(getClass().getResourceAsStream("/resource/control/up.png"));
    Image imageTURN_LEFT = new Image(getClass().getResourceAsStream("/resource/control/turn_left.png"));
    Image imageTURN_RIGHT = new Image(getClass().getResourceAsStream("/resource/control/turn_right.png"));
    Image imageLIGHT = new Image(getClass().getResourceAsStream("/resource/control/light.png"));
    Image imageSUSPEN = new Image(getClass().getResourceAsStream("/resource/control/suspen.png"));
    Image imageP1 = new Image(getClass().getResourceAsStream("/resource/control/p1.png"));

    //Lista para seleccion del imagen de control
    private List<String> listMAIN = new ArrayList<String>();
    private List<String> listPROC1 = new ArrayList<String>();
    static List<Image> listIMAGE = new ArrayList<Image>();
    static List<ImageView> listIMAGEVIEW_MAIN = new ArrayList<ImageView>();
    static List<ImageView> listIMAGEVIEW_PROC1 = new ArrayList<ImageView>();

    private String listCONTROL[] = {"UP","TURN_LEFT","TURN_RIGHT","LIGHT","SUSPEN","P1"};

    //metodos graficos (fondos de pantalla)
    public void Wallpaper_Walle(ActionEvent event){
        fondo.setImage(imageWALLE); System.out.println("Wallpaper_Walle");
    }
    public void Wallpaper_Eva(ActionEvent event){
        fondo.setImage(imageEVA);     System.out.println("Wallpaper_Eva");
    }
    public void Wallpaper_Mo(ActionEvent event){
        fondo.setImage(imageMO);     System.out.println("Wallpaper_Mo");
    }
    public void Wallpaper_Null(ActionEvent event){
        fondo.setImage(imageNULL);     System.out.println("Wallpaper_Null");
    }

    //metodos graficos (reproduccion de musica de fondo)
    public void play_music(ActionEvent event){
        System.out.println(music.isSelected());
        if (music.isSelected()){
            mediaPlayer.play();

        }else{
            mediaPlayer.pause();

        }
    }

    //metodos graficos (checkbox de seleccion)
    public void control_MAIN(){
        if (main10.isSelected()){
            proc10.setSelected(false);
            System.out.println("control_MAIN");
        }


    }
    public void control_PROC1() {
         if (proc10.isSelected()) {
            main10.setSelected(false);
             System.out.println("control_PROC1");
        }
    }

    //Mensaje de alerta
    private void alert_MAIN_PROC(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Please, select a option []MAIN or []PROC1");
        alert.setContentText("You need to select an option to add a movement!");
        alert.showAndWait();
    }


    private void alert_LIMIT_MAIN(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        if (s.equals("MAIN")){
            alert.setHeaderText("Sorry, your MAIN box is full");
        }else{
            alert.setHeaderText("Sorry, your PROC1 box is full");
        }
        alert.setContentText("To add another element you must modify \nyour current boxes!");
        alert.showAndWait();
    }


    public void updateALL(){
        if (bandera) {
            updateIMAGE();
            updateIMAGEVIEW();
        }
        bandera=false;
    }

    public void updateIMAGE(){
        listIMAGE.add(imageUP);
        listIMAGE.add(imageTURN_LEFT);
        listIMAGE.add(imageTURN_RIGHT);
        listIMAGE.add(imageLIGHT);
        listIMAGE.add(imageSUSPEN);
        listIMAGE.add(imageP1);

        System.out.println("Realy Image _ "+listIMAGE.size());
    }

    public void updateIMAGEVIEW(){
        listIMAGEVIEW_MAIN.add(m1);        listIMAGEVIEW_MAIN.add(m2);        listIMAGEVIEW_MAIN.add(m3);        listIMAGEVIEW_MAIN.add(m4);
        listIMAGEVIEW_MAIN.add(m5);        listIMAGEVIEW_MAIN.add(m6);        listIMAGEVIEW_MAIN.add(m7);        listIMAGEVIEW_MAIN.add(m8);
        listIMAGEVIEW_MAIN.add(m9);        listIMAGEVIEW_MAIN.add(m10);        listIMAGEVIEW_MAIN.add(m11);      listIMAGEVIEW_MAIN.add(m12);

        listIMAGEVIEW_PROC1.add(p1);        listIMAGEVIEW_PROC1.add(p2);        listIMAGEVIEW_PROC1.add(p3);        listIMAGEVIEW_PROC1.add(p4);
        listIMAGEVIEW_PROC1.add(p5);        listIMAGEVIEW_PROC1.add(p6);        listIMAGEVIEW_PROC1.add(p7);        listIMAGEVIEW_PROC1.add(p8);

        System.out.println("Realy ImageView _ "+listIMAGEVIEW_MAIN.size() +" and "+listIMAGEVIEW_PROC1.size());

    }

    public void mergesMAIN(){
        for(int c = 0; c < listMAIN.size(); c++){
            if(listMAIN.get(c).equals("UP")) {
                listIMAGEVIEW_MAIN.get(c).setImage(listIMAGE.get(0));
            }
            if(listMAIN.get(c).equals("TURN_LEFT")) {
                listIMAGEVIEW_MAIN.get(c).setImage(listIMAGE.get(1));
            }
            if(listMAIN.get(c).equals("TURN_RIGHT")) {
                listIMAGEVIEW_MAIN.get(c).setImage(listIMAGE.get(2));
            }
            if(listMAIN.get(c).equals("LIGHT")) {
                listIMAGEVIEW_MAIN.get(c).setImage(listIMAGE.get(3));
            }
            if(listMAIN.get(c).equals("SUSPEN")) {
                listIMAGEVIEW_MAIN.get(c).setImage(listIMAGE.get(4));
            }
            if(listMAIN.get(c).equals("P1")) {
                listIMAGEVIEW_MAIN.get(c).setImage(listIMAGE.get(5));
            }   //"UP","TURN_LEFT","TURN_RIGHT","LIGHT","SUSPEN","P1"
        }

    }

    public void mergesPROC(){
        for(int p = 0; p < listPROC1.size(); p++){
            if(listPROC1.get(p).equals("UP")) {
                listIMAGEVIEW_PROC1.get(p).setImage(listIMAGE.get(0));
            }
            if(listPROC1.get(p).equals("TURN_LEFT")) {
                listIMAGEVIEW_PROC1.get(p).setImage(listIMAGE.get(1));
            }
            if(listPROC1.get(p).equals("TURN_RIGHT")) {
                listIMAGEVIEW_PROC1.get(p).setImage(listIMAGE.get(2));
            }
            if(listPROC1.get(p).equals("LIGHT")) {
                listIMAGEVIEW_PROC1.get(p).setImage(listIMAGE.get(3));
            }
            if(listPROC1.get(p).equals("SUSPEN")) {
                listIMAGEVIEW_PROC1.get(p).setImage(listIMAGE.get(4));
            }
            if(listPROC1.get(p).equals("P1")) {
                listIMAGEVIEW_PROC1.get(p).setImage(listIMAGE.get(5));
            }   //"UP","TURN_LEFT","TURN_RIGHT","LIGHT","SUSPEN","P1"
        }

    }

    public void B_UP(ActionEvent event){
        updateALL();
        if (main10.isSelected()==false &&  proc10.isSelected()==false){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[0]);
            mergesMAIN();
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[0]);
            mergesPROC();
        }else if (listMAIN.size()>=12) {
            alert_LIMIT_MAIN("MAIN");
        }else if (listPROC1.size()>=8) {
            alert_LIMIT_MAIN("PROC1");
        }else{
            alert_MAIN_PROC();
        }


    }
    public void B_TURN_LEFT (ActionEvent event){
        updateALL();
        if (main10.isSelected()==false &&  proc10.isSelected()==false){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[1]);
            mergesMAIN();
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[1]);
            mergesPROC();
        }else if (listMAIN.size()>=12) {
            alert_LIMIT_MAIN("MAIN");
        }else if (listPROC1.size()>=8) {
            alert_LIMIT_MAIN("PROC1");
        }else{
            alert_MAIN_PROC();
        }
    }
    public void B_TURN_RIGHT(ActionEvent event){
        updateALL();
        if (main10.isSelected()==false &&  proc10.isSelected()==false){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[2]);
            mergesMAIN();
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[2]);
            mergesPROC();
        }else if (listMAIN.size()>=12) {
            alert_LIMIT_MAIN("MAIN");
        }else if (listPROC1.size()>=8) {
            alert_LIMIT_MAIN("PROC1");
        }else{
            alert_MAIN_PROC();
        }
    }
    public void B_LIGHT(ActionEvent event){
        updateALL();
        if (main10.isSelected()==false &&  proc10.isSelected()==false){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[3]);
            mergesMAIN();
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[3]);
            mergesPROC();
        }else if (listMAIN.size()>=12) {
            alert_LIMIT_MAIN("MAIN");
        }else if (listPROC1.size()>=8) {
            alert_LIMIT_MAIN("PROC1");
        }else{
            alert_MAIN_PROC();
        }
    }
    public void B_SUSPEN(ActionEvent event){
        updateALL();
        if (main10.isSelected()==false &&  proc10.isSelected()==false){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[4]);
            mergesMAIN();
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[4]);
            mergesPROC();
        }else if (listMAIN.size()>=12) {
            alert_LIMIT_MAIN("MAIN");
        }else if (listPROC1.size()>=8) {
            alert_LIMIT_MAIN("PROC1");
        }else{
            alert_MAIN_PROC();
        }
    }
    public void B_P1(ActionEvent event){
        updateALL();
        if (main10.isSelected()==false &&  proc10.isSelected()==false){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[5]);
            mergesMAIN();
        }else if (proc10.isSelected() && listPROC1.size()<8 ){
            listPROC1.add(listCONTROL[5]);
            mergesPROC();
        }else if (listMAIN.size()>=12) {
            alert_LIMIT_MAIN("MAIN");
        }else if (listPROC1.size()>=8) {
            alert_LIMIT_MAIN("PROC1");
        }
    }


    public void PLAY_Send(ActionEvent event){
        System.out.println("Lista del MAIN : "+listMAIN.toString()+"\nLista del PROC1 : "+listPROC1.toString());
    }

    public void E_M_1(ActionEvent event){
        if (listMAIN.size()>=1){
            listMAIN.remove(0);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_2(ActionEvent event){
        if (listMAIN.size()>=2){
            listMAIN.remove(1);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_3(ActionEvent event){
        if (listMAIN.size()>=3){
            listMAIN.remove(2);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_4(ActionEvent event){
        if (listMAIN.size()>=4){
            listMAIN.remove(3);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_5(ActionEvent event){
        if (listMAIN.size()>=5){
            listMAIN.remove(4);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_6(ActionEvent event){
        if (listMAIN.size()>=6){
            listMAIN.remove(5);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_7(ActionEvent event){
        if (listMAIN.size()>=7){
            listMAIN.remove(6);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_8(ActionEvent event){
        if (listMAIN.size()>=8){
            listMAIN.remove(7);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_9(ActionEvent event){
        if (listMAIN.size()>=9){
            listMAIN.remove(8);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_10(ActionEvent event){
        if (listMAIN.size()>=10){
            listMAIN.remove(9);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_11(ActionEvent event){
        if (listMAIN.size()>=11){
            listMAIN.remove(10);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }
    public void E_M_12(ActionEvent event){
        if (listMAIN.size()>=12){
            listMAIN.remove(11);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesMAIN();
        }
    }


    public void E_P_1(ActionEvent event){
        if (listPROC1.size()>=1){
            listPROC1.remove(0);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }
    public void E_P_2(ActionEvent event){
        if (listPROC1.size()>=2){
            listPROC1.remove(1);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }
    public void E_P_3(ActionEvent event){
        if (listPROC1.size()>=3){
            listPROC1.remove(2);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }
    public void E_P_4(ActionEvent event){
        if (listPROC1.size()>=4){
            listPROC1.remove(3);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }
    public void E_P_5(ActionEvent event){
        if (listPROC1.size()>=5){
            listPROC1.remove(4);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }
    public void E_P_6(ActionEvent event){
        if (listPROC1.size()>=6){
            listPROC1.remove(5);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }
    public void E_P_7(ActionEvent event){
        if (listPROC1.size()>=7){
            listPROC1.remove(6);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }
    public void E_P_8(ActionEvent event){
        if (listPROC1.size()>=8){
            listPROC1.remove(7);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesPROC();
        }
    }



}
