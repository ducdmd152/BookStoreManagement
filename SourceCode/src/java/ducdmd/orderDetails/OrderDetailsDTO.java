/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.orderDetails;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class OrderDetailsDTO implements Serializable {
    private int orderId;
    private String sku;
    private float price;
    private int quantiy;
    private float total;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(int orderId, String sku, float price, int quantiy, float total) {
        this.orderId = orderId;
        this.sku = sku;
        this.price = price;
        this.quantiy = quantiy;
        this.total = total;
    }

    

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the sku
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return the quantiy
     */
    public int getQuantiy() {
        return quantiy;
    }

    /**
     * @param quantiy the quantiy to set
     */
    public void setQuantiy(int quantiy) {
        this.quantiy = quantiy;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }
    
    
}
