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
public class Contact {
    private int contactID;
    private Service service;//FK
    private Transaction transaction;//FK
    private String status;

    public Contact(int contactID, Service service, Transaction transaction, String status) {
        this.contactID = contactID;
        this.service = service;
        this.transaction = transaction;
        this.status = status;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
