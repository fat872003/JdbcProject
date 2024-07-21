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
public class Client {
    private int AccountID;
    private Bank bank;

    public Client(int AccountID, Bank bank) {
        this.AccountID = AccountID;
        this.bank = bank;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int AccountID) {
        this.AccountID = AccountID;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
    
    
}
