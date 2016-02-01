package com.hyogij.magentosoapapplication.SOAP;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * A class to request SOAP and to receive data
 * http://inchoo.net/dev-talk/android-development/magento-v2-api-soap-android/
 */
public class SOAPHelper {
    private static final String CLASS_NAME = SOAPHelper.class.getCanonicalName();

    private static final String SOAP_ACTION = "http://www.w3schools" +
            ".com/webservices/CelsiusToFahrenheit";
    private static final String METHOD_NAME = "CelsiusToFahrenheit";
//    private static final String NAMESPACE = "http://www.w3schools.com/webservices/";
//    private static final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";

    private static final String NAMESPACE = "urn:Magento";
    private static final String URL = "http://ec2-52-69-188-47.ap-northeast-1.compute.amazonaws.com/api/v2_soap?wsdl";

    private static SoapPrimitive soapPrimitive = null;

    public static void login() {
        try {
            SoapObject request = new SoapObject(NAMESPACE, "login");

            request.addProperty("username", "test-client");
            request.addProperty("apiKey", "12345678");
            SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            env.dotNet = false;
            env.xsd = SoapSerializationEnvelope.XSD;
            env.enc = SoapSerializationEnvelope.ENC;

            env.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;

            try {

                androidHttpTransport.call("", env);
                Object res = env.getResponse();
                String ssid = res.toString();
                Log.d(CLASS_NAME, "sessionId " + ssid);

            } catch (Exception e) {
                String ss = e.getMessage();
                Log.d(CLASS_NAME, "Soapfault " + ss);
            }

        } catch (Exception e) {

            Log.d(CLASS_NAME, "Error " + e.toString());
        }

    }
}
