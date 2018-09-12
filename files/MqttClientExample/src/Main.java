import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

//Ejemplo de conexión a servidor mqtt con autenticación y parseo de objetos con jackson
//Si hay que recibir datos, hay que editar el método Subscriber.messageArrived, la biblioteca se encarga de escucharlo constantemente
/*OJO el topic es jerarquico, tiene estructura de directorio, ejemplo:  test_topic/topic_1/topic_2, si alguien se subscribe a "test_topic" le llegaran
todos los mensajes de "topic_1" y "topic_2", si alguien se subscribe a "topic_1" solo le llegan los mensajes de ese topic*/


public class Main {
    public static void main(String[] args) throws MqttException {
        //Basta con instanciar un Subscriber para iniciar la conexión con el broker
        Subscriber s = new Subscriber("m12.cloudmqtt.com:16115","nnsmxwti","37KKt6sf8N6L","java_app","test_topic");
        s.sendMessage("hello from app");



        //JACKSON
        ObjectMapper mapper = new ObjectMapper();//Para poder hacer operaciones JSON
        Estudiante estudiante = new Estudiante(20, "Sebastián");
         try {
             //Pasando de objeto a string en formato json
             String jsonInString = mapper.writeValueAsString(estudiante);
             System.out.println(jsonInString);//aquí se ve impreso, es lo que eventualmente se envia.
             s.sendMessage(jsonInString);


             //OJO LA CLASE QUE QUERES PARSEAR DEBE DE TENER CONSTRUCTOR POR DEFECTO (VACIO SIN PARAMETROS)!!!!!!!!!!!!!!!
             //De string en formato JSON a objeto.
             Estudiante obj = mapper.readValue(jsonInString, Estudiante.class);
             System.out.println(obj.getNombre());

         } catch (JsonProcessingException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }



    }
}
