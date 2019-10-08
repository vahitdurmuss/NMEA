package com.example.vahitdurmus.nmeaproject.nmeamessage;

/**
 * Created by vahit.durmus on 16.09.2019.
 */

import java.sql.Timestamp;

/**
 * Created by vahit.durmus on 23.05.2019.
 */

public class GGA {


    private String time;
    private volatile String latitude;
    private String NS;
    private volatile String longitude;
    private String EW;
    private int fixQuality;
    private int numberOfSatellitesInUse;
    private double hdop;
    private double altitude;
    private String altitudeUnit;
    private double wgs84;
    private String unitWgs84;

    public GGA(String nmea) {
        try{
            String[] ggaMessageArray= nmea.split(",");
            time=   ggaMessageArray[1];
            latitude= ggaMessageArray[2];
            NS=ggaMessageArray[3];
            longitude=ggaMessageArray[4];
            EW=ggaMessageArray[5];
            fixQuality= Integer.parseInt(ggaMessageArray[6]);
            numberOfSatellitesInUse= Integer.parseInt(ggaMessageArray[7]);
            hdop= Double.parseDouble(ggaMessageArray[8]);
            altitude= Double.parseDouble(ggaMessageArray[9]);
            altitudeUnit=ggaMessageArray[10];
            wgs84= Double.parseDouble(ggaMessageArray[11]);
            unitWgs84=ggaMessageArray[12];
        }
        catch (IllegalArgumentException e){
        }
        catch (NullPointerException e){
        }
    }

    public void GGA(){}

    public int getFixQuality(){
        return this.fixQuality;
    }

    public int getNumberOfSatellitesInUse(){
        return this.numberOfSatellitesInUse;
    }

    public double getLatitude(){

        try {
            double latdob= Double.valueOf(this.latitude);
            String latStringNmea= String.valueOf(latdob);

            String latdegree=latStringNmea.substring(0,2);
            String latminute=latStringNmea.substring(2,latStringNmea.length()-2);
            double latdegreeD= Double.parseDouble(latdegree);
            double latminuteD= Double.parseDouble(latminute);
            double latitudeReturn=latdegreeD+(latminuteD/60);
            return latitudeReturn;
        }
        catch (Exception e){
            return 0;
        }

    }
    public double getLongitude() {
        try {
            double longdob= Double.valueOf(this.longitude);
            String longStringNmea= String.valueOf(longdob);

            String longdegree=longStringNmea.substring(0,2);
            String longminute=longStringNmea.substring(2,longStringNmea.length()-2);
            double longdegreeD= Double.parseDouble(longdegree);
            double longminuteD= Double.parseDouble(longminute);
            double longitudeReturn=longdegreeD+(longminuteD/60);
            return longitudeReturn;
        }
        catch (Exception e){
            return 0;
        }

    }
    public String getLongLatText(){
        String returnText;
        try {
            returnText= String.valueOf(getLongitude()) + "," + String.valueOf(getLatitude());
        }
        catch (Exception e){
            returnText="empty";
        }
        return returnText;
    }
    public String getTime(){
        try {

            String hour=time.substring(0,2);
            String minute =time.substring(2,4);
            String second=time.substring(4,6);

            return hour+":"+minute+":"+second;
        }
        catch (Exception e){
           return "0.0";
        }
    }


    @Override
    public String toString() {
        return new StringBuffer().append("Location:\n"+getLongLatText()).append("\n\nfix quality:\n"+String.valueOf(fixQuality)+"\n\nnumberOfSatellitesInUse\n"+String.valueOf(getNumberOfSatellitesInUse())).toString();
    }
}
