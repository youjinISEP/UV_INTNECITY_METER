package com.example.uv_intencity_meter.Database;

import android.os.Parcel;
import android.os.Parcelable;

public class ContentPoint5 implements Parcelable {
    private String title;
    private double uvTopbar, tempTopbar, humidTopbar;
    private double a1, a2, a3, a4, a5;
    private double uni, avg;
    private long id;

    public ContentPoint5(){
        super();
    }

    public ContentPoint5(Parcel in){
        super();
        this.id = in.readLong();
        this.title = in.readString();
        this.uvTopbar = in.readDouble();
        this.tempTopbar = in.readDouble();
        this.humidTopbar = in.readDouble();
        this.a1 = in.readDouble();
        this.a2 = in.readDouble();
        this.a3 = in.readDouble();
        this.a4 = in.readDouble();
        this.a5 = in.readDouble();
        this.avg = in.readDouble();
        this.uni = in.readDouble();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getUvTopbar() {
        return uvTopbar;
    }

    public void setUvTopbar(double uvTopbar) {
        this.uvTopbar = uvTopbar;
    }

    public double getTempTopbar() {
        return tempTopbar;
    }

    public void setTempTopbar(double tempTopbar) {
        this.tempTopbar = tempTopbar;
    }

    public double getHumidTopbar() {
        return humidTopbar;
    }

    public void setHumidTopbar(double humidTopbar) {
        this.humidTopbar = humidTopbar;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public double getA3() {
        return a3;
    }

    public void setA3(double a3) {
        this.a3 = a3;
    }

    public double getA4() {
        return a4;
    }

    public void setA4(double a4) {
        this.a4 = a4;
    }

    public double getA5() {
        return a5;
    }

    public void setA5(double a5) {
        this.a5 = a5;
    }

    public double getUni() {
        return uni;
    }

    public void setUni(double uni) {
        this.uni = uni;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(getId());
        parcel.writeString(getTitle());
        parcel.writeDouble(getUvTopbar());
        parcel.writeDouble(getTempTopbar());
        parcel.writeDouble(getHumidTopbar());
        parcel.writeDouble(getA1());
        parcel.writeDouble(getA2());
        parcel.writeDouble(getA3());
        parcel.writeDouble(getA4());
        parcel.writeDouble(getA5());
        parcel.writeDouble(getAvg());
        parcel.writeDouble(getUni());
    }

    public static final Parcelable.Creator<ContentPoint5> CREATOR = new Parcelable.Creator<ContentPoint5>() {
        public ContentPoint5 createFromParcel(Parcel in) {
            return new ContentPoint5(in);
        }

        public ContentPoint5[] newArray(int size) {
            return new ContentPoint5[size];
        }
    };
}
