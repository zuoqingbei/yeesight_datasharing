package com.haier.datamart.airflowsupport.model;

/**
 * Created by long on 2018/11/12.
 */
public class KettleSupport {
    private String id;
    private String dagName;
    private String bashCommand;
    private String schedule;
    private String startDate;
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDagName() {
        return dagName;
    }

    public void setDagName(String dagName) {
        this.dagName = dagName;
    }

    public String getBashCommand() {
        return bashCommand;
    }

    public void setBashCommand(String bashCommand) {
        this.bashCommand = bashCommand;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
