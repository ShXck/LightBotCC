package org.tec.comp.utilities;

import org.tec.comp.interpreter.Action_Type;

public class Message_Handler {

    /**
     * @return error de que no existe código que compilar.
     */
	public static String build_no_code_err() {
		return "No source code file has been loaded, please load your source file.";
	}

    /**
     *
     * @return mensaje de código guardado.
     */
	public static String build_code_saved_msg() {
		return "Code successfully saved";
	}

    /**
     * @param var el nombre de la variable.
     * @return Error sobre variable sin encontrar en el código.
     */
	public static String no_such_var_found(String var) {
	    return "Variable " + var + " is not defined.";
    }

    /**
     * @param proc nombre del procedimiento.
     * @return error sobre procedimiento no encontrado.
     */
    public static String no_such_proc_found(String proc) {
        return "Procedure " + proc + " is not defined.";
    }

    /**
     * @param id el nombre de la variable.
     * @return error sobre nombre de variable inválido.
     */
    public static String invalid_length_variable_id(String id) { return "Variable " + id + " has an invalid length."; }

    /**
     * @return mesaje de éxito al compilar el código.
     */
    public static String success_build_code() { return "Code Succesfully built. No errors found."; }

    /**
     * @param x posición en x.
     * @param y posición en y.
     * @return error sobre posición inválida en la matriz.
     */
    public static String invalid_position(int x, int y) {
	    return "The position " + String.valueOf(x) + "," + String.valueOf(y) + " is invalid. Does not fit the board.";
    }

    /**
     * @return error sobre variables sin encontrar en el código.
     */
    public static String no_vars_found() { return "No variables found in the code."; }

    /**
     * @param action acción sin encontrar.
     * @return error sobre acción sin encontrar en el código.
     */
    public static String no_action_found(Action_Type action) {
	    switch (action) {
            case PUT_LIGHT: {
                return "The map has no lights. Use the command put-light";
            }
            case POS_START: {
                return "Robot's initial position has not been declared. Use PosStart(x,y).";
            }
            case COMMENT: {
                return "The code has no comments";
            }
        }
        return "";
	}

    /**
     * @return mensaje sobre envío exitoso de mapa al tablero.
     */
    public static String map_sent_success() { return "The map has been sent successfully to the board."; }

    /**
     * @return error sobre mapa no construido.
     */
    public static String map_not_built() { return  "The code has not been built yet."; }

    /**
     * @param var el nombre de la variable.
     * @return error sobre variable no asignada.
     */
    public static String var_no_assigned(String var) { return "Variable " + var +  " is not assigned therefore no actions can be performed."; }
}
