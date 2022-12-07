/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.preparedOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MSI
 */
public class PreparedOrderList implements Serializable { // ~ a JavaBean for tracking prepared order
    
    private Map<Integer, PreparedOrder> items;

    public PreparedOrderList() {
    }

    public Map<Integer, PreparedOrder> getItems() {
        return items;
    }
    
    public void addOrderQueueItem(PreparedOrder item) {
        if(this.items==null) {
            this.items = new HashMap<>();
        }
        
        this.items.put(item.getId(), item);
    }
    
    public void removeOrderQueueItem(int id) {
        if(this.items==null) {
            return;
        }
        
        this.items.remove(id);
        
        if(this.items.isEmpty()) {
            this.items = null;
        }
    }
    
    public PreparedOrder gerOrderQueueItem(int id) {        
        if(this.items==null) {
            return null;
        }
        
        return this.items.get(id);
    }
}
