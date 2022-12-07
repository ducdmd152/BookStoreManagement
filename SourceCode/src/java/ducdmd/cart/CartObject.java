/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.cart;

import ducdmd.orderDetails.OrderDetailsDAO;
import ducdmd.product.ProductObject;
import ducdmd.product.ProductDAO;
import ducdmd.product.ProductDTO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author MSI
 */
public class CartObject implements Serializable {

    private Map<String, Integer> items;

    /// khong co set vi khong up xot hang loat, them tung item
    public Map<String, Integer> getItems() {
        return items;
    }

    public void addItemToCart(String sku, int quantity) {
        if (sku == null) {
            return;
        }

        if (sku.trim().isEmpty()) {
            return;
        }

        if (quantity == 0) {
            return;
        }

        if (this.items == null) {
            this.items = new HashMap<>();
        } // items have not existed

        if (this.items.containsKey(sku)) {
            quantity += this.items.get(sku);
        }

        // update items
        this.items.put(sku, quantity); /// put existed key?
    }

    public void removeItemFromCart(String sku) {
        if (sku == null) {
            return;
        }
        if (sku.trim().isEmpty()) {
            return;
        }

        if (this.items == null) {
            return;
        }

        if (this.items.containsKey(sku)) {
            this.items.remove(sku);
            if (this.items.isEmpty()) { /// chu dong huy doi tuong empty
                this.items = null;
            }
        }
    }

    public void refresh()
            throws SQLException, NamingException {
        if (this.items == null) {
            return;
        }

        ProductDAO productDAO = new ProductDAO();
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

        for (String sku : this.items.keySet()) {
            
            int orderedQuantity = orderDetailsDAO.getOrderedQuantityOf(sku);            
            ProductDTO dto = productDAO.getProduct(sku);
            int restQuantity = dto.getQuantity() - orderedQuantity;
            
            int quantityInCart = this.items.get(sku);          
            
            int updateQuantityInCart = Integer.min(restQuantity, quantityInCart);
            
            if (updateQuantityInCart == 0) { // remove
                this.removeItemFromCart(sku);
            } else { // update
                this.items.put(sku, updateQuantityInCart);
            }
        }
    }

    public List<ProductObject> getSelectedProductsInCart(String[] selectedItems) 
            throws SQLException, NamingException {
        if(selectedItems==null) {
            return null;
        }
        if(selectedItems.length==0) {
            return null;
        }
        
        if(this.items==null) {
            return null;
        }
        
        List<ProductObject> result = new ArrayList<>();
        ProductDAO dao = new ProductDAO();
        
        for (String sku : selectedItems) {
            if(this.items.containsKey(sku)==false) {
                continue;
            }
            
            ProductDTO dto = dao.getProduct(sku);

            String name = dto.getName();
            String description = dto.getDescription();
            float price = dto.getPrice();
            int quantity = this.items.get(sku);

            ProductObject productInCart = new ProductObject(sku, name, description, quantity, price);
            
            result.add(productInCart);
        }
        
        if(result.isEmpty()) {
            result=null;
        }
        
        return result;
    }
    
    public List<ProductObject> getAllProductsInCart() 
            throws SQLException, NamingException {        
        if(this.items==null) {
            return null;
        }
        
        List<ProductObject> result = new ArrayList<>();
        ProductDAO dao = new ProductDAO();
        
        for (String sku : this.items.keySet()) {
            ProductDTO dto = dao.getProduct(sku);

            String name = dto.getName();
            String description = dto.getDescription();
            float price = dto.getPrice();
            int quantity = this.items.get(sku);

            ProductObject productInCart = new ProductObject(sku, name, description, quantity, price);
            
            result.add(productInCart);
        }
        
        return result;
    }
    
}
