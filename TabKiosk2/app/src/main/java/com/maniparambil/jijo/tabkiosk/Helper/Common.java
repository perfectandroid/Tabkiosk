package com.maniparambil.jijo.tabkiosk.Helper;

public class Common {

    public static final int timeInMillisecond=180000;
    private static final boolean HOSTNAMEVERFICATION_MANUAL=true;


    public final static String HOSTNAME_SUBJECT="CLIENT-DEMO";
    public final static String CERTIFICATE_ASSET_NAME="client-demo.pem";
    public final static String BASE_URL="https://202.21.32.35:14001";



    private static final String API_NAME= "/cusFeedback/api/MV2";

    //==== ==== ==== ===== ===== ===== ==== ==== ==== ==== ==== ==== ==== ==== ==== ====

    public static boolean isHostnameverficationManual() {
        return HOSTNAMEVERFICATION_MANUAL;
    }

    public static String getHostnameSubject() {
        return HOSTNAME_SUBJECT;
    }

    public static String getCertificateAssetName() {
        return CERTIFICATE_ASSET_NAME;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getApiName() {
        return API_NAME;
    }

}
