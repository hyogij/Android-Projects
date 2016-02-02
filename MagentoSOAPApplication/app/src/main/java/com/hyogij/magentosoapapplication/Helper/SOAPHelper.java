package com.hyogij.magentosoapapplication.Helper;

import android.util.Log;

import com.hyogij.magentosoapapplication.Const;
import com.hyogij.magentosoapapplication.Datas.Customer;

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
    private static final String CLASS_NAME = SOAPHelper.class.getCanonicalName();

    private static SoapSerializationEnvelope env = null;
    private static HttpTransportSE androidHttpTransport = null;

    public static String getSession() {
        Log.d(CLASS_NAME, "getSession ");
        String sessionId = null;
        try {
            env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            SoapObject request = new SoapObject(Const.NAMESPACE, "login");
            request.addProperty("username", Const.USER_NAME);
            request.addProperty("apiKey", Const.API_KEY);
            env.setOutputSoapObject(request);

            androidHttpTransport = new HttpTransportSE(Const.URL);
            androidHttpTransport.call("", env);
            Object result = env.getResponse();
            sessionId = result.toString();
            Log.d(CLASS_NAME, "sessionId : " + sessionId);
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }

        return sessionId;
    }

    public static ArrayList<Customer> onCustomerCustomerList() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            String sessionId = getSession();
            if (sessionId == null) {
                return null;
            }

            // Making call to get list of customers
            SoapObject request = new SoapObject(Const.NAMESPACE, "customerCustomerList");
//            SoapObject request = new SoapObject(Const.NAMESPACE, "customer.list");
            request.addProperty("sessionId", sessionId);


            env.setOutputSoapObject(request);
            androidHttpTransport.call("", env);

            Object result = env.getResponse();
            Log.d(CLASS_NAME, "Customer List : " + result.toString());

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

    public static ArrayList<Customer> onStoreList() {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            String sessionId = getSession();
            if (sessionId == null) {
                return null;
            }

            // Making call to get list of products
            SoapObject request = new SoapObject(Const.NAMESPACE, "storeList");
            request.addProperty("sessionId", sessionId);

            env.setOutputSoapObject(request);
            androidHttpTransport.call("", env);

            Object result = env.getResponse();
            Log.d(CLASS_NAME, "catalog Product List : " + result.toString());

            SoapObject response = (SoapObject) result;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) response.getProperty(i);
                Log.d(CLASS_NAME, property.toString());
            }
        } catch (Exception e) {
            Log.d(CLASS_NAME, "Exception " + e.getMessage());
        }
        return customers;
    }
}
