/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ducdmd.utils;

/**
 *
 * @author MSI
 */
public class MyApplicationConstants {

    public class FirstRequestFeature {

        public static final String LOGIN_PAGE = "loginPage";
        public static final String ACCOUNT_ADMINISTRATION_ACTION = "accountAdministrationAction";
        public static final String SHOPPING_ACTION = "shoppingAction";
    }

    public class LoginFeature {
        public static final String LOGIN_ERROR_PAGE = "loginErrorPage";
        public static final String INVALID_PAGE = "invalidPage";
        public static final String ACCOUNT_ADMINISTRATION_ACTION = "accountAdministrationAction";
        public static final String SHOPPING_ACTION = "shoppingAction";

    }

    public class LogoutFeature {

        public static final String LOGIN_PAGE = "loginPage";
    }

    public class RegisterFeature {
        public static final String LOGIN_PAGE = "loginPage";
        public static final String ERROR_PAGE = "registerErrorPage";
    }
    
    public class AuthorizationFeature {
        public static final String HOME_ACTION = "";
        public static final String LOGIN_PAGE = "loginPage";
        public static final String LOGOUT_ACTION = "logoutAction";
        public static final String ACCOUNT_ADMINISTRATION_ACTION = "accountAdministrationAction";
        public static final String SHOPPING_ACTION = "shoppingAction";
        public static final String VIEW_CART_ACTION = "viewCartAction";
    }
    
    public class CartFeature {
        public static final String VIEW_CART_ACTION = "viewCartAction";
        public static final String REMOVE_ITEM_FROM_CART_ACTION = "removeItemFromCartAction";
        public static final String CONFIRM_CHECKOUT_ACTION = "confirmCheckoutAction";
    }

    public class ShoppingFeature {
        public static final String SHOPPING_PAGE = "shoppingPage";
    }
    
    public class AddToCartFeature {
        public static final String ERROR_PAGE = "errorPage";
        public static final String SHOPPING_ACTION = "shoppingAction";
    }
    
    public class ViewCartFeature {
        public static final String VIEW_CART_PAGE = "viewCartPage";
    }
    
    public class RemoveItemFromCartFeature {
        public static final String VIEW_CART_ACTION = "viewCartAction";
    }

    public class ConfirmCheckOutFeature {
        public static final String CHECKOUT_FAIL_PAGE = "checkOutFailPage";
        public static final String VIEW_CART_ACTION = "viewCartAction";
        public static final String CONFIRM_CHECKOUT_PAGE = "confirmCheckoutPage";
    }
    
    public class CheckOutFeature {
        public static final String CHECKOUT_FAIL_PAGE = "checkOutFailPage";
        public static final String CHECKOUT_SUCCESS_PAGE = "checkOutSuccessPage";
        public static final String PHONE_NUMBER_REGEX = "phoneNumberRegex";
        public static final String RE_CONFIRM_CHECKOUT_ACTION = "reConfirmCheckoutAction";
    }

    public class ReConfirmCheckOutFeature {

        public static final String CHECKOUT_FAIL_PAGE = "checkOutFailPage";
        public static final String CONFIRM_CHECKOUT_PAGE = "confirmCheckoutPage";
    }
    
    public class AccountAdministrationFeature {
        public static final String LOGIN_PAGE = "loginPage";
        public static final String SEARCH_PAGE = "searchPage";
        public static final String ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE = "roleFeatureAuthorizationErrorPage";
    }
    public class SearchLastnameFeature {
        public static final String SEARCH_PAGE = "searchPage";
        public static final String SEARCH_RESULT_PAGE = "searchPage";
        public static final String ACCOUNT_FEATURE_CONSTRAINT_ERROR_PAGE = "roleFeatureAuthorizationErrorPage";
    }
    
    public class UpdateAccountFeature {
        public static final String ERROR_PAGE = "errorPage";
        public static final String SEARCH_LASTNAME_ACTION = "searchLastnameAction";
    }
    
    public class DeleteAccountFeature {
        public static final String ERROR_PAGE = "errorPage";
        public static final String SEARCH_LASTNAME_ACTION = "searchLastnameAction";
    }
    
    
//    public class DispatchFeature {
//        public static final String FIRST_REQUEST_CONTROLLER = "firstRequestController";
//        public static final String LOGIN_PAGE = "loginPage";
//        public static final String LOGIN_CONTROLLER = "loginController";
//        public static final String SEARCH_LASTNAME_CONTROLLER = "searchLastnameController";
//        public static final String DELETE_ACCOUNT_CONTROLLER = "deleteAccounController";
//        public static final String UPDATE_ACCOUNT_CONTROLLER = "updateAccountController";
//        public static final String ADD_TO_CART_CONTROLLER = "addToCartController";
//        public static final String VIEW_CART_CONTROLLER = "viewCartController";
//        public static final String SHOPPING_CONTROLLER = "shoppingController";
//        public static final String REMOVE_ITEM_FROM_CART_CONTROLLER = "removeItemFromCartController";
//        public static final String LOGOUT_CONTROLLER = "logoutController";
//        public static final String REGISTER_CONTROLLER = "registerController";
//        public static final String CHECK_OUT_CONTROLLER = "checkOutController";
//        public static final String CONFIRM_CHECKOUT_CONTROLLER = "confirmCheckoutController";
//        public static final String ACCOUNT_ADMINISTRATION_CONTROLLER = "accountAdministrationController";
//    }
}
