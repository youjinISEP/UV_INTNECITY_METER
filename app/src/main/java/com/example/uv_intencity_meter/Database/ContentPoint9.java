package com.example.uv_intencity_meter.Database;

import android.os.Parcel;
import android.os.Parcelable;

public class ContentPoint9 implements Parcelable {
    private String title;
    private double uvTopbar, tempTopbar, humidTopbar;
    private double a1, a2, a3, a4, a5, a6, a7, a8, a9;
    private double avg, uni;
    private long id;

    public ContentPoint9(){
        super();
    }

    public ContentPoint9(Parcel in){
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
        this.a6 = in.readDouble();
        this.a7 = in.readDouble();
        this.a8 = in.readDouble();
        this.a9 = in.readDouble();
        this.avg = in.readDouble();
        this.uni = in.readDouble();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {       this.id = id;    }

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

    public double getA6() {
        return a6;
    }

    public void setA6(double a6) {
        this.a6 = a6;
    }

    public double getA7() {
        return a7;
    }

    public void setA7(double a7) {
        this.a7 = a7;
    }

    public double getA8() {
        return a8;
    }

    public void setA8(double a8) {
        this.a8 = a8;
    }

    public double getA9() {
        return a9;
    }

    public void setA9(double a9) {
        this.a9 = a9;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
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
        parcel.writeDouble(getA6());
        parcel.writeDouble(getA7());
        parcel.writeDouble(getA8());
        parcel.writeDouble(getA9());
        parcel.writeDouble(getAvg());
        parcel.writeDouble(getUni());
    }

    public static final Parcelable.Creator<ContentPoint9> CREATOR = new Parcelable.Creator<ContentPoint9>() {
        public ContentPoint9 createFromParcel(Parcel in) {
            return new ContentPoint9(in);
        }

        public ContentPoint9[] newArray(int size) {
            return new ContentPoint9[size];
        }
    };
}
