package com.hyogij.soapsampleapplication.helper;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * A class to request SOAP and to receive data
 */
public class SOAPHelper {
    private static final String CLASS_NAME = SOAPHelper.class.getCanonicalName();

    private static final String SOAP_ACTION = "http://www.w3schools" +
            ".com/webservices/CelsiusToFahrenheit";
    private static final String METHOD_NAME = "CelsiusToFahrenheit";
    private static final String NAMESPACE = "http://www.w3schools.com/webservices/";
    private static final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";

    private static SoapPrimitive soapPrimitive = null;

    public static void soapRequest(String celsius) {
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("Celsius", celsius);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            soapPrimitive = (SoapPrimitive) soapEnvelope.getResponse();

            Log.d(CLASS_NAME, "Result Celsius: " + soapPrimitive);
        } catch (Exception ex) {
            Log.d(CLASS_NAME, "Error: " + ex.getMessage());
        }
    }

    public static SoapPrimitive getSoapPrimitive() {
        return soapPrimitive;
    }

    public static void getFahrenheit(String celsius) {
        //Create request
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //Property which holds input parameters
        PropertyInfo celsiusPI = new PropertyInfo();
        //Set Name
        celsiusPI.setName("Celsius");
        //Set Value
        celsiusPI.setValue(celsius);
        //Set dataType
        celsiusPI.setType(double.class);
        //Add the property to request object
        request.addProperty(celsiusPI);
        //Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        //Set output SOAP object
        envelope.setOutputSoapObject(request);
        //Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            //Invole web service
            androidHttpTransport.call(SOAP_ACTION, envelope);
            //Get the response
            soapPrimitive = (SoapPrimitive) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
