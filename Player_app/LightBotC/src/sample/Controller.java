package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    //variable del entorno grafico
    @FXML private ImageView fondo;
    //Conjunto de ImageView para el Main
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
    //Conjunto de ImageView para el Proc1
    @FXML private ImageView p1;
    @FXML private ImageView p2;
    @FXML private ImageView p3;
    @FXML private ImageView p4;
    @FXML private ImageView p5;
    @FXML private ImageView p6;
    @FXML private ImageView p7;
    @FXML private ImageView p8;
    //Conjunto de ImageView para el Proc2
    @FXML private ImageView p11;
    @FXML private ImageView p21;
    @FXML private ImageView p31;
    @FXML private ImageView p41;
    @FXML private ImageView p51;
    @FXML private ImageView p61;
    @FXML private ImageView p71;
    @FXML private ImageView p81;
    @FXML private CheckBox music,main10,proc10,proc101;

    private static boolean bandera = true;

    //musica de fondo -----------------------------> Posible error por ruta propia de PC (home/josenunez/IdeaProjects....)
    private Media musicFile = new Media("file:///home/josenunez/IdeaProjects/LightBotC/src/resource/music/WaLLe_Music.mp3");
    private MediaPlayer mediaPlayer = new MediaPlayer(musicFile);

    //Imagenes para los fondos de pantalla
    private Image imageWALLE = new Image(getClass().getResourceAsStream("/resource/wallpaper/walle.jpg"));
    private Image imageMO = new Image(getClass().getResourceAsStream("/resource/wallpaper/mo.jpg"));
    private Image imageEVA = new Image(getClass().getResourceAsStream("/resource/wallpaper/eva.jpg"));
    private Image imageNULL = new Image(getClass().getResourceAsStream("/resource/wallpaper/smooth.jpg"));

    //Imagenes de control
    private Image imageUP = new Image(getClass().getResourceAsStream("/resource/control/up.png"));
    private Image imageTURN_LEFT = new Image(getClass().getResourceAsStream("/resource/control/turn_left.png"));
    private Image imageTURN_RIGHT = new Image(getClass().getResourceAsStream("/resource/control/turn_right.png"));
    private Image imageLIGHT = new Image(getClass().getResourceAsStream("/resource/control/light.png"));
    private Image imageSUSPEN = new Image(getClass().getResourceAsStream("/resource/control/suspen.png"));
    private Image imageP1 = new Image(getClass().getResourceAsStream("/resource/control/p1FIN.png"));
    private Image imageP2 = new Image(getClass().getResourceAsStream("/resource/control/p2FIN.png"));

    //Lista para seleccion del imagen de control
    private List<String> listMAIN = new ArrayList<String>();
    private List<String> listPROC1 = new ArrayList<String>();
    private List<String> listPROC11 = new ArrayList<String>();
    private static List<Image> listIMAGE = new ArrayList<Image>();
    private static List<ImageView> listIMAGEVIEW_MAIN = new ArrayList<ImageView>();
    private static List<ImageView> listIMAGEVIEW_PROC1 = new ArrayList<ImageView>();
    private static List<ImageView> listIMAGEVIEW_PROC11 = new ArrayList<ImageView>();

    //Listas de optimizacion de codigo
    private static List<List<ImageView>> listMeSt = new ArrayList<List<ImageView>>();
    private static List<List<String> > listMPP = new ArrayList<List<String> >();

    //Control
    private String listCONTROL[] = {"UP","TURN_LEFT","TURN_RIGHT","LIGHT","JUMP","P1","P2"};
    private String resultadoFinal;

    //metodos graficos (fondos de pantalla)
    public void Wallpaper_Walle(){
        fondo.setImage(imageWALLE);
    }
    public void Wallpaper_Eva(){
        fondo.setImage(imageEVA);
    }
    public void Wallpaper_Mo(){
        fondo.setImage(imageMO);
    }
    public void Wallpaper_Null(){
        fondo.setImage(imageNULL);
    }

    //Metodos Graficos
    //reproduccion de musica de fondo
    public void play_music(){
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
            proc101.setSelected(false);
        }
    }
    public void control_PROC1() {
         if (proc10.isSelected()) {
             main10.setSelected(false);
             proc101.setSelected(false);
         }
    }
    public void control_PROC11() {
        if (proc101.isSelected()) {
            main10.setSelected(false);
            proc10.setSelected(false);
        }
    }

    //Mensaje de alerta
    private void alert_MAIN_PROC(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Please, select a option []MAIN , []PROC1 or []PROC2");
        alert.setContentText("You need to select an option to add a movement!");
        alert.showAndWait();
    }
    private void alert_LIMIT_MAIN(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        if (s.equals("MAIN")){
            alert.setHeaderText("Sorry, your MAIN box is full");
        }else if (s.equals("PROC1")){
            alert.setHeaderText("Sorry, your PROC1 box is full");
        }else if (s.equals("PROC2")){
            alert.setHeaderText("Sorry, your PROC2 box is full");
        }
        alert.setContentText("To add another element you must modify \nyour current boxes!");
        alert.showAndWait();
    }

    //Inicio del programa
    private void updateALL(){
        if (bandera) {
            updateIMAGE();
            updateIMAGEVIEW();
        }
        bandera=false;
    }
    private void updateIMAGE(){
        listIMAGE.add(imageUP);
        listIMAGE.add(imageTURN_LEFT);
        listIMAGE.add(imageTURN_RIGHT);
        listIMAGE.add(imageLIGHT);
        listIMAGE.add(imageSUSPEN);
        listIMAGE.add(imageP1);
        listIMAGE.add(imageP2);
    }
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

    //Metodo para reorganizar las imagenes segun la imagen que se elimino.
    private void merges(int x){
        for(int c = 0; c < listMPP.get(x).size(); c++){
            if(listMPP.get(x).get(c).equals("UP")) {
                listMeSt.get(x).get(c).setImage(listIMAGE.get(0));
            }
            else if(listMPP.get(x).get(c).equals("TURN_LEFT")) {
                listMeSt.get(x).get(c).setImage(listIMAGE.get(1));
            }
            else if(listMPP.get(x).get(c).equals("TURN_RIGHT")) {
                listMeSt.get(x).get(c).setImage(listIMAGE.get(2));
            }
            else if(listMPP.get(x).get(c).equals("LIGHT")) {
                listMeSt.get(x).get(c).setImage(listIMAGE.get(3));
            }
            else if(listMPP.get(x).get(c).equals("JUMP")) {
                listMeSt.get(x).get(c).setImage(listIMAGE.get(4));
            }
            else if(listMPP.get(x).get(c).equals("P1")) {
                listMeSt.get(x).get(c).setImage(listIMAGE.get(5));
            }
            else if(listMPP.get(x).get(c).equals("P2")) {
                listMeSt.get(x).get(c).setImage(listIMAGE.get(6));
            }
            //"UP","TURN_LEFT","TURN_RIGHT","LIGHT","JUMP","P1"
        }
    }
    private void mergesStandar(String MP1P2){

        if(MP1P2.equals("M")){
            merges(0);
        }else if(MP1P2.equals("P1")){
            merges(1);
        }else if(MP1P2.equals("P2")){
            merges(2);
        }else{
            System.out.print("error.. :(");
        }
    }

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
    public void B_SUSPEN(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
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
    public void B_P1(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[5]);
            mergesStandar("M");
        }else if (proc10.isSelected() || proc101.isSelected()){
            alert_PP();
        }else {
            alerta555();
        }
    }
    public void B_P11(){
        updateALL();
        if (checkMP1P2()){
            alert_MAIN_PROC();
        }
        else if (main10.isSelected() && listMAIN.size()<12){
            listMAIN.add(listCONTROL[6]);
            mergesStandar("M");
        }else if (proc10.isSelected() || proc101.isSelected()){
            alert_PP();
        }else {
            alerta555();
        }
    }

    private void alert_PP(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Caution can not add P# inside P# ");
        alert.setContentText("Loop!");
        alert.showAndWait();
    }


    private boolean checkMP1P2(){
        return (!main10.isSelected() &&  !proc10.isSelected() &&  !proc101.isSelected());
    }

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
    public void PLAY_Send(){
        System.out.println("Lista del MAIN : "+listMAIN.toString()+"\nLista del PROC1 : "+listPROC1.toString()+"\nLista del PROC2 : "+listPROC11.toString());
        resultadoFinal="/";
        for(int rF=0;rF<listMAIN.size();rF++){
            if (listMAIN.get(rF).equals("P1")){
                for(int p1=0;p1<listPROC1.size();p1++) {
                    resultadoFinal += listPROC1.get(p1)+"/";
                }
            }else if (listMAIN.get(rF).equals("P2")){
                for(int p2=0;p2<listPROC11.size();p2++) {
                    resultadoFinal += listPROC11.get(p2)+"/";
                }
            }else {
                resultadoFinal += listMAIN.get(rF)+"/";
            }
        }
        System.out.printf("> "+resultadoFinal );
    }

    public void E_M_1(){
        if (listMAIN.size()>=1){
            listMAIN.remove(0);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_2(){
        if (listMAIN.size()>=2){
            listMAIN.remove(1);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_3(){
        if (listMAIN.size()>=3){
            listMAIN.remove(2);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_4(){
        if (listMAIN.size()>=4){
            listMAIN.remove(3);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_5(){
        if (listMAIN.size()>=5){
            listMAIN.remove(4);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_6(){
        if (listMAIN.size()>=6){
            listMAIN.remove(5);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_7(){
        if (listMAIN.size()>=7){
            listMAIN.remove(6);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_8(){
        if (listMAIN.size()>=8){
            listMAIN.remove(7);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_9(){
        if (listMAIN.size()>=9){
            listMAIN.remove(8);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_10(){
        if (listMAIN.size()>=10){
            listMAIN.remove(9);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_11(){
        if (listMAIN.size()>=11){
            listMAIN.remove(10);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }
    public void E_M_12(){
        if (listMAIN.size()>=12){
            listMAIN.remove(11);
            listIMAGEVIEW_MAIN.get(listMAIN.size()).setImage(null);
            mergesStandar("M");
        }
    }


    public void E_P_1(){
        if (listPROC1.size()>=1){
            listPROC1.remove(0);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    public void E_P_2(){
        if (listPROC1.size()>=2){
            listPROC1.remove(1);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    public void E_P_3(){
        if (listPROC1.size()>=3){
            listPROC1.remove(2);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    public void E_P_4(){
        if (listPROC1.size()>=4){
            listPROC1.remove(3);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    public void E_P_5(){
        if (listPROC1.size()>=5){
            listPROC1.remove(4);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    public void E_P_6(){
        if (listPROC1.size()>=6){
            listPROC1.remove(5);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    public void E_P_7(){
        if (listPROC1.size()>=7){
            listPROC1.remove(6);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }
    public void E_P_8(){
        if (listPROC1.size()>=8){
            listPROC1.remove(7);
            listIMAGEVIEW_PROC1.get(listPROC1.size()).setImage(null);
            mergesStandar("P1");
        }
    }

    //Tablero del PROC2
    public void E_P_11(){
        if (listPROC11.size()>=1){
            listPROC11.remove(0);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    public void E_P_21(){
        if (listPROC11.size()>=2){
            listPROC11.remove(1);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    public void E_P_31(){
        if (listPROC11.size()>=3){
            listPROC11.remove(2);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    public void E_P_41(){
        if (listPROC11.size()>=4){
            listPROC11.remove(3);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    public void E_P_51(){
        if (listPROC11.size()>=5){
            listPROC11.remove(4);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    public void E_P_61(){
        if (listPROC11.size()>=6){
            listPROC11.remove(5);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    public void E_P_71(){
        if (listPROC11.size()>=7){
            listPROC11.remove(6);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }
    public void E_P_81(){
        if (listPROC11.size()>=8){
            listPROC11.remove(7);
            listIMAGEVIEW_PROC11.get(listPROC11.size()).setImage(null);
            mergesStandar("P2");
        }
    }

}
