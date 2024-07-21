/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Lenovo
 */
public class Bank {
    private int bankID;
    private String bankName;
    private String bankNum;

    public Bank(int bankID, String bankName, String bankNum) {
        this.bankID = bankID;
        this.bankName = bankName;
        this.bankNum = bankNum;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }
    
    
    
}
