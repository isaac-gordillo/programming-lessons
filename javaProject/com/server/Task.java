package javaProject.com.server;

import java.io.Serializable;

public class Task implements Serializable {
    private String description;
    private String status;

    public Task(String description, String status) {
        this.description = description;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String estado) {
        this.status = estado;
    }

    @Override
    public String toString() {
        return "Task [description=" + description + ", status=" + status + "]";
    }

}
