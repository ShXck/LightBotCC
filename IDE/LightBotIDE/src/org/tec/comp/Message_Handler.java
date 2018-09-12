package org.tec.comp;

public class Message_Handler {

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

	public static String no_such_var_found(String var) {
	    return "Variable " + var + " is not defined.";
    }

    public static String success_build_code() { return "Code Succesfully built. No errors found."; }
}
