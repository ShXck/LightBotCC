package sample;

import java.util.List;

public class Process {

    private List<String> process;
    private String id;

    public Process() {
        process = null;
        id = "App";
    }

    public List<String> getProcess() {
        return process;
    }

    public void setProcess(List<String> process) {
        this.process = process;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

