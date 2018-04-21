package org.emobile.myitmarket.utils;

/**
 * Created by Elvedin on 7/31/2017.
 * Application Constants
 */

public class Constants {

    public static final String SHARED_PREFERENCES_NAME = "AuthStatePreference";

    public static final String BASE_URL = "http://192.168.0.102:8080";
//    public static final String BASE_URL = "http://192.168.43.76:8080";
//    public static final String BASE_URL = "http://10.0.1.15:8080";
//    public static final String BASE_URL = "http://192.168.0.102:8080";
//    public static final String BASE_URL = "http://192.168.0.105:8080";
    public static final String PRODUCTS_URL = "/private/products";
    //Private Public
    public static final String PRIVATE = "private/";
    public static final String PUBLIC = "public/";

    public static final String GET_ALL_CATEGORIES = BASE_URL + PRODUCTS_URL + "/get-all-categories";
    public static final String GET_ROOT_CATEGORIES = BASE_URL + PRODUCTS_URL + "/get-root-categories";
    public static final String GET_LEAF_CATEGORIES = BASE_URL + PRODUCTS_URL + "/get-leaf-categories";
    public static final String GET_CATEGORIES = BASE_URL + PRODUCTS_URL + "/get-categories-or-subcategories";
    public static final String GET_SUBCATEGORIES = BASE_URL + PRODUCTS_URL + "/get-subcategory";
    public static final String GET_OFFERS_PER_CATEGORY = BASE_URL + PRODUCTS_URL + "/get-offers-per-category";
    public static final String GET_OFFERS_FOR_PRODUCT = BASE_URL + PRODUCTS_URL + "/get-offers-with-same-product";
    public static final String ADD_PRODUCT_TO_FAVORITES = BASE_URL + PRODUCTS_URL + "/add-product-to-favorites";
    public static final String CHECK_IF_PRODUCT_IS_FAVORITE = BASE_URL + PRODUCTS_URL + "/is_product_favorite";
    public static final String GET_MY_FAVORITE_PRODUCTS = BASE_URL + PRODUCTS_URL + "/get_my_favorites";
    public static final String GET_MOST_FAVORITE_PRODUCTS = BASE_URL + PRODUCTS_URL + "/get_most_favorites";
    public static final String GET_FILTERED_PRODUCTS = BASE_URL + PRODUCTS_URL + "/filter-search";
    public static final String SEARCH_PHRASE = "search_phrase";
    public static final String LANG = "lang";
    public static final String PAGE = "page";
    public static final String PER_PAGE = "per_page";

    //header keys
    public static final String REFRESH_TOKEN_HEADER = "refresh-token";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String IPADDRESS = "ip-address";
    public static final String UUID = "uuid";
    public static final String PHONEMODEL = "phone-model";
    public static final String OS = "os";
    public static final String OSVERSION = "os-version";
    public static final String DEVICENAME = "device-name";
    public static final String APPVERSION = "application-version";

    public static final String DEVICE_ID_HEADER = "device-id";
    public static final String ACCEPT_HEADER = "Accept";
    public static final String CONTENT_TYPE_HEADER = "Content-type";
    public static final String MSISDN_HEADER = "msisdn-added";

    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_MK = "mk";

    //server links
    public static final String LOGIN = PRIVATE + "login";
    public static final String USER_LOGIN = PRIVATE + "user-login";
    public static final String LOGIN_OTP = PRIVATE + "login-otp";
    public static final String LOCATION = PUBLIC + "locations";
    public static final String CATALOGUE = PUBLIC + "news";
    public static final String CARDS = CATALOGUE + "/cards";
    public static final String NEWSANDPROMOTIONSDETAILS = CATALOGUE + "/details";
    public static final String CONTACT = PUBLIC + "contact";
    public static final String PROFILE = PRIVATE + "profile";
    public static final String PAYMENT_VALIDATE = PRIVATE + "validate-payment";
    public static final String PAYMENT_INIT = PRIVATE + "initiate-payment";
    public static final String PAYMENT_SAVE_TEMPLATE = PRIVATE + "save-template";
    public static final String PAYMENT_GET_TEMPLATES = PRIVATE + "templates";
    public static final String CURRENCYLIST = PUBLIC + "currency";
    public static final String CREDIT = PRIVATE + "credits";
    public static final String AMORT_PLAN = PRIVATE + "amort-for-credit";
    public static final String MYCARDS = PRIVATE + "cards";
    public static final String TRANSACTIONS = PRIVATE + "transactions";
    public static final String TRANSACTION_DETAILS = PRIVATE + "transaction-details";

    //Payment types
    public static final String PAYMENT_PP_30 = "io.medialab.stbbt.payment.pp.30";
    public static final String PAYMENT_PP_50 = "io.medialab.stbbt.payment.pp.50";

    //Persistence
    public static final String PERSISTENCE_CUSTOMER_ID = "io.medialab.stbbt.persistence.customer.id";
    public static final String PERSISTENCE_PIN = "io.medialab.stbbt.persistence.pin";

    //Persistance keys
    public static final String AUTH_STATE = "AUTH_STATE";
    public static final String FRESH_TOKEN = "FRESH_TOKEN";
    public static final String USED_INTENT = "USED_INTENT";
    public static final String KEY_STATE = "user_state";
    public static final String KEY_EXPIRATION_TIME = "expiration_time";

    public static final String ROOT_CATEGORIES = "root";


}
