package com.example.sunshine;

public class DataModel {
    private int image_id;
    private String day;
    private String kindOfWeather;
    private int maxTemp;
    private int lowTemp;
    public DataModel(int image, String day, String kindOfWeather, int maxTemp, int lowTemp)
    {
        this.image_id = image;
        this.day = day;
        this.kindOfWeather = kindOfWeather;
        this.maxTemp = maxTemp;
        this.lowTemp = lowTemp;
    }
    public int getImage()
    {
        return this.image_id;
    }
    public String getDay()
    {
        return this.day;
    }
    public String getKindOfWeather()
    {
        return this.kindOfWeather;
    }
    public int getMaxTemp()
    {
        return this.maxTemp;
    }
    public int getLowTemp()
    {
        return this.lowTemp;
    }
}
