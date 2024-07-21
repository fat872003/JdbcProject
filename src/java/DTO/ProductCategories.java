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
public class ProductCategories {

    private int cate_ID;
    private String name;
    private String icon;
    private String status;
    
    public ProductCategories() {
    }

    public ProductCategories(int cate_ID, String name, String icon, String status) {
        this.cate_ID = cate_ID;
        this.name = name;
        this.icon = icon;
        this.status = status;
    }

    public int getCate_ID() {
        return cate_ID;
    }

    public void setCate_ID(int cate_ID) {
        this.cate_ID = cate_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}