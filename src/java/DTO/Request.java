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
public class Request {

    private int ReqID;
    private Account Acc; //FK
    private Account adminAcc; //FK
    private Contact Contact; //FK
    private StatusType StatusType;
    private RequestType requestType; //FK
    private String Description;

    public Request() {
    }

    public Request(int ReqID, Account Acc, Account adminAcc, Contact Contact, StatusType StatusType, RequestType requestType, String Description) {
        this.ReqID = ReqID;
        this.Acc = Acc;
        this.adminAcc = adminAcc;
        this.Contact = Contact;
        this.StatusType = StatusType;
        this.requestType = requestType;
        this.Description = Description;
    }

    public int getReqID() {
        return ReqID;
    }

    public void setReqID(int ReqID) {
        this.ReqID = ReqID;
    }

    public Account getAcc() {
        return Acc;
    }

    public void setAcc(Account Acc) {
        this.Acc = Acc;
    }

    public Account getAdminAcc() {
        return adminAcc;
    }

    public void setAdminAcc(Account adminAcc) {
        this.adminAcc = adminAcc;
    }

    public Contact getContact() {
        return Contact;
    }

    public void setContact(Contact Contact) {
        this.Contact = Contact;
    }

    public StatusType getStatusType() {
        return StatusType;
    }

    public void setStatusType(StatusType StatusType) {
        this.StatusType = StatusType;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
