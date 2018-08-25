package org.tec.comp;

public class Error_Msg_Handler {

    /**
     * Crea un mensaje de error cuando no hay código en el IDE.
     * @return el mensaje de error.
     */
	public static String build_no_code_err() {
		return "You haven't loaded any source code file, please load your code file.";
	}

    /**
     * Crea un mensaje informativo al guardar el código en un archivo.
     * @return el mensaje con el resultado de la operación.
     */
	public static String build_code_saved_msg() {
		return "Code successfully saved";
	}
}
