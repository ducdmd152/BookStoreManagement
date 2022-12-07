/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.authorization;

import ducdmd.utils.MyApplicationConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author MSI
 */
public class Authorization {
    private Map<String, String> featureNavs;
    private static final String HOME_FEATURE_LABEL = "Home";
    private static final String LOGIN_FEATURE_LABEL = "Login";
    private static final String LOGOUT_FEATURE_LABEL = "Log out";
    private static final String ACCOUNT_ADMINISTRATION_FEATURE_LABEL = "Account Administration";
    private static final String SHOPPING_FEATURE_LABEL = "Shopping";
    private static final String VIEW_CART_FEATURE_LABEL = "View Cart";
    private static String[] adminFeatures = {
        HOME_FEATURE_LABEL,
        ACCOUNT_ADMINISTRATION_FEATURE_LABEL,
//        SHOPPING_FEATURE_LABEL,
//        VIEW_CART_FEATURE_LABEL,
        LOGOUT_FEATURE_LABEL};
    private static String[] custFeatures = {
        HOME_FEATURE_LABEL, 
        SHOPPING_FEATURE_LABEL, 
        VIEW_CART_FEATURE_LABEL, 
        LOGOUT_FEATURE_LABEL};
    private static String[] guestFeatures = {
        HOME_FEATURE_LABEL, 
        LOGIN_FEATURE_LABEL, 
        SHOPPING_FEATURE_LABEL, 
        VIEW_CART_FEATURE_LABEL};
    
    public Authorization() {
        featureNavs = new HashMap<>();
        
        featureNavs.put(HOME_FEATURE_LABEL , MyApplicationConstants.ApplicationScope.HOME_ACTION);        
        
        featureNavs.put(LOGIN_FEATURE_LABEL, MyApplicationConstants.ApplicationScope.LOGIN_PAGE);
        
        featureNavs.put(LOGOUT_FEATURE_LABEL, MyApplicationConstants.ApplicationScope.LOGOUT_ACTION);
        
        featureNavs.put(ACCOUNT_ADMINISTRATION_FEATURE_LABEL, MyApplicationConstants.ApplicationScope.ACCOUNT_ADMINISTRATION_ACTION);
        
        featureNavs.put(SHOPPING_FEATURE_LABEL, MyApplicationConstants.ApplicationScope.SHOPPING_ACTION);
        
        featureNavs.put(VIEW_CART_FEATURE_LABEL, MyApplicationConstants.ApplicationScope.VIEW_CART_ACTION);
    }
    
    public List<Pair<String, String>> getFeatureNavs(Boolean role) {
        List<Pair<String, String>> result = new ArrayList<>();
        String[] features = guestFeatures;
        
        if(role!=null) {
            if(role==true) {
                features = adminFeatures;
            }
            else {
                features = custFeatures;
            }
        }
        for(String feature : features) {
            Pair<String, String> pair = new Pair<>(feature, featureNavs.get(feature));
            result.add(pair);
        }
        
        return result;
    }
    
//    public boolean isAuthorized(Boolean role, String resourceLabel) {
//        String[] features = guestFeatures;
//        
//        if(role!=null) {
//            if(role==true) {
//                features = adminFeatures;
//            }
//            else {
//                features = custFeatures;
//            }
//        }
//        
//        boolean result = false;
//        for(String feature : features) {
//            if(feature.equals(resourceLabel)) {
//                result = true;
//                break;
//            }
//        }
//        
//        return result;
//    }
}
