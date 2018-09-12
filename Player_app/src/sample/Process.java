package sample;

import java.util.List;

public class Process {

    private List<String> process;
    private String iD;

    public Process() {
        process = null;
        iD = "App";
    }

    public List<String> getProcess() {
        return process;
    }

    public void setProcess(List<String> process) {
        this.process = process;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }
}

