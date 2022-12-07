/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.preparedOrder;

import java.io.Serializable;

/**
 *
 * @author MSI
 */
public class PreparedOrderCheckoutError implements Serializable {
    // user's error
    private String nameLengthError;
    private String phoneNumberIsNotValid;
    private String addressLengthError;
    // system's error
    private String notEnoughProductForOrder;
    private String prepareOrderIsNotFound;
    private String productListIsNotExisted;
    
    public PreparedOrderCheckoutError() {
    }

    /**
     * @return the notEnoughProductForOrder
     */
    public String getNotEnoughProductForOrder() {
        return notEnoughProductForOrder;
    }

    /**
     * @param notEnoughProductForOrder the notEnoughProductForOrder to set
     */
    public void setNotEnoughProductForOrder(String notEnoughProductForOrder) {
        this.notEnoughProductForOrder = notEnoughProductForOrder;
    }
    
    /**
     * @return the productListIsNotExisted
     */
    public String getProductListIsNotExisted() {
        return productListIsNotExisted;
    }

    /**
     * @param productListIsNotExisted the productListIsNotExisted to set
     */
    public void setProductListIsNotExisted(String productListIsNotExisted) {
        this.productListIsNotExisted = productListIsNotExisted;
    }

    /**
     * @return the prepareOrderIsNotFound
     */
    public String getPrepareOrderIsNotFound() {
        return prepareOrderIsNotFound;
    }

    /**
     * @param prepareOrderIsNotFound the prepareOrderIsNotFound to set
     */
    public void setPrepareOrderIsNotFound(String prepareOrderIsNotFound) {
        this.prepareOrderIsNotFound = prepareOrderIsNotFound;
    }

    /**
     * @return the nameLengthError
     */
    public String getNameLengthError() {
        return nameLengthError;
    }

    /**
     * @param nameLengthError the nameLengthError to set
     */
    public void setNameLengthError(String nameLengthError) {
        this.nameLengthError = nameLengthError;
    }

    /**
     * @return the phoneNumberIsNotValid
     */
    public String getPhoneNumberIsNotValid() {
        return phoneNumberIsNotValid;
    }

    /**
     * @param phoneNumberIsNotValid the phoneNumberIsNotValid to set
     */
    public void setPhoneNumberIsNotValid(String phoneNumberIsNotValid) {
        this.phoneNumberIsNotValid = phoneNumberIsNotValid;
    }

    /**
     * @return the addressLengthError
     */
    public String getAddressLengthError() {
        return addressLengthError;
    }

    /**
     * @param addressLengthError the addressLengthError to set
     */
    public void setAddressLengthError(String addressLengthError) {
        this.addressLengthError = addressLengthError;
    }
    
    
}
