/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class Employee {
    private int AccountID;
    private Date dayOfBirth;
    private String identify_ID;
    private Date workingDay;
    private Double salary;
    private Major major;

    public Employee(int AccountID, Date dayOfBirth, String identify_ID, Date workingDay, Double salary, Major major) {
        this.AccountID = AccountID;
        this.dayOfBirth = dayOfBirth;
        this.identify_ID = identify_ID;
        this.workingDay = workingDay;
        this.salary = salary;
        this.major = major;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int AccountID) {
        this.AccountID = AccountID;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getIdentify_ID() {
        return identify_ID;
    }

    public void setIdentify_ID(String identify_ID) {
        this.identify_ID = identify_ID;
    }

    public Date getWorkingDay() {
        return workingDay;
    }

    public void setWorkingDay(Date workingDay) {
        this.workingDay = workingDay;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }



 
    
    
}
