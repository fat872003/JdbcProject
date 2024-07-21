/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author ACER
 */
public class Service {
    private int id;
    private String serviceName;
    private String status;
    private int servicePrice;

    public Service() {
    }

    public Service(int id, String serviceName, String status, int servicePrice) {
        this.id = id;
        this.serviceName = serviceName;
        this.status = status;
        this.servicePrice = servicePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }



   

}
