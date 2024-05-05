package edu.uncc.weatherapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Forecast implements Serializable {
    private String dt_text;
    private String description;
    private String icon;
    private double temp;
    private double humidity;
    private String windSpeed;



    public Forecast() {
    }
    public Forecast(JSONObject jsonObject) throws JSONException {
        JSONObject relativeHumidity = jsonObject.getJSONObject("relativeHumidity");

        this.dt_text = jsonObject.getString("startTime");
        this.temp = jsonObject.getDouble("temperature");
        this.windSpeed = jsonObject.getString("windSpeed");
        this.description = jsonObject.getString("shortForecast");
        this.humidity = relativeHumidity.getDouble("value"); // double check
        this.icon = jsonObject.getString("icon");


    }

    public String getDt_text() {
        return dt_text;
    }

    public void setDt_txt(String dt_text) {
        this.dt_text = dt_text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getIconUrl(){
        return icon;
    }

}