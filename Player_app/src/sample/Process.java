package sample;

import java.util.List;

public class Process {

    private List<String> process;
    private String id;

    /**
     * Constructor del msj a enviar
     */
    public Process() {
        process = null;
        id = "App";
    }

    /**
     * Obtencion del proceso
     * @return
     */
    public List<String> getProcess() {
        return process;
    }

    /**
     * Seleccion del proceso
     * @param process
     */
    public void setProcess(List<String> process) {
        this.process = process;
    }

    /**
     * Obtencion del id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Seleccion del id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

}

