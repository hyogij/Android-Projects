package com.hyogij.magentosoapapplication.Helper;

import android.util.Log;

import com.hyogij.magentosoapapplication.Const;
import com.hyogij.magentosoapapplication.Datas.Customer;
import com.hyogij.magentosoapapplication.Datas.Product;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * A class to request SOAP and to receive data
 * http://inchoo.net/dev-talk/android-development/magento-v2-api-soap-android/
 */
public class SOAPHelper {
    private static final String CLASS_NAME = SOAPHelper.class
            .getCanonicalName();

    private static final int TIME_OUT_MILLISECOND = 30000;
    private static final int REQUEST_PRODUCT_COUNT = 10;

    private static SoapSerializationEnvelope env = null;
    private static HttpTransportSE androidHttpTransport = null;

    private static String sessionId = null;

    // Making call to get session
    public static String getSession() {
        try {
            env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            SoapObject request = new SoapObject(Const.NAMESPACE, "login");
            request.addProperty("username", Const.USER_NAME);
            request.addProperty("apiKey", Const.API_KEY);
            env.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(Const.URL, TIME_OUT_MILLISECOND);
            androidHttpTransport.call("", env);
            Object result = env.getResponse();
            sessionId = result.toString();
            Log.d(CLASS_NAME, "sessionId : " + sessionId);
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }

        return sessionId;
    }

    // Making call to get list of customers
    public static ArrayList<Customer> onCustomerCustomerList() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            sessionId = getSession();
            if (sessionId == null) {
                return null;
            }

            SoapObject request = new SoapObject(Const.NAMESPACE,
                    "customerCustomerList");
            request.addProperty("sessionId", sessionId);

            env.setOutputSoapObject(request);
            androidHttpTransport.call("", env);

            Object result = env.getResponse();
            SoapObject response = (SoapObject) result;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) response.getProperty(i);
                Customer customer = new Customer(property);
                Log.d(CLASS_NAME, customer.toString());
                customers.add(customer);
            }
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }
        return customers;
    }

    // Making call to get list of products
    public static ArrayList<Product> onProductList() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            sessionId = getSession();
            if (sessionId == null) {
                return null;
            }

            SoapObject request = new SoapObject(Const.NAMESPACE,
                    "catalogProductList");
            request.addProperty("sessionId", sessionId);

            env.setOutputSoapObject(request);

            androidHttpTransport.call("", env);
            Object result = env.getResponse();

            SoapObject response = (SoapObject) result;
//            for (int i = 0; i < response.getPropertyCount(); i++) {
            for (int i = 0; i < REQUEST_PRODUCT_COUNT; i++) {
                SoapObject property = (SoapObject) response.getProperty(i);
                Product product = new Product(property);
//                getPrice(product);
                getImageFile(product);
                getImageUrl(product);

                Log.d(CLASS_NAME, product.toString());
                products.add(product);
            }
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }
        return products;
    }

    // Making call to get price of product
    private static void getPrice(Product product) {
        try {
            SoapObject request = new SoapObject(Const.NAMESPACE,
                    "catalogProductInfo");
            request.addProperty("sessionId", sessionId);
            request.addProperty("productId", product.getProduct_id());

            env.setOutputSoapObject(request);
            androidHttpTransport.call("", env);

            Object result = env.getResponse();
            SoapObject response = (SoapObject) result;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) response.getProperty(i);
                product.setPrice(property.getProperty("price").toString());
            }
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }
    }

    // Making call to get image file of product
    private static void getImageFile(Product product) {
        try {
            SoapObject request = new SoapObject(Const.NAMESPACE,
                    "catalogProductAttributeMediaList");
            request.addProperty("sessionId", sessionId);
            request.addProperty("product", product.getProduct_id());

            env.setOutputSoapObject(request);
            androidHttpTransport.call("", env);

            Object result = env.getResponse();
            SoapObject response = (SoapObject) result;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) response.getProperty(i);
                product.setFile(property.getProperty("file").toString());
            }
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }
    }

    // Making call to get image url of product
    private static void getImageUrl(Product product) {
        try {
            SoapObject request = new SoapObject(Const.NAMESPACE,
                    "catalogProductAttributeMediaList");
            request.addProperty("sessionId", sessionId);
            request.addProperty("product", product.getProduct_id());
            request.addProperty("file", product.getFile());

            env.setOutputSoapObject(request);
            androidHttpTransport.call("", env);

            Object result = env.getResponse();

            SoapObject response = (SoapObject) result;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) response.getProperty(i);
                product.setImage(property.getProperty("url").toString());
            }
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }
    }
}
