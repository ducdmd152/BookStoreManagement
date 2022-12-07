/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.preparedOrder;

import ducdmd.product.ProductObject;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author MSI
 */
public class PreparedOrder implements Serializable {

    private static int identity = 0;

    private int id;
    private List<ProductObject> productsInOrder;

    public PreparedOrder() {
        this.id = ++identity;
    }

    public PreparedOrder(List<ProductObject> productsInOrder) {
        this.id = ++identity;
        this.productsInOrder = productsInOrder;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the productsInOrder
     */
    public List<ProductObject> getProductsInOrder() {
        return productsInOrder;
    }

    /**
     * @param productsInOrder the productsInOrder to set
     */
    public void setProductsInOrder(List<ProductObject> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public float getTotal() {
        if(this.productsInOrder==null) {
            return 0;
        }
        
        float totalOfOrder = 0;
        for (ProductObject product : this.productsInOrder) {
            totalOfOrder += product.getTotal();
        }
        
        return totalOfOrder;
    }
}
