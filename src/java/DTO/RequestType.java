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
public class RequestType {

    private int rqTyID;
    private String rqTyName;

    public RequestType() {
    }

    public RequestType(int rqTyID, String rqTyName) {
        this.rqTyID = rqTyID;
        this.rqTyName = rqTyName;
    }

    public int getRqTyID() {
        return rqTyID;
    }

    public void setRqTyID(int rqTyID) {
        this.rqTyID = rqTyID;
    }

    public String getRqTyName() {
        return rqTyName;
    }

    public void setRqTyName(String rqTyName) {
        this.rqTyName = rqTyName;
    }
}