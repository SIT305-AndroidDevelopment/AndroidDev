package com.example.assessment_2.model;

import java.io.Serializable;
import java.util.List;

public class BrandItem implements Serializable {

    public  String brandName;
    public int imgRes;
    public List<MotorItem> motoList;

    public List<MotorItem> getMotoList() {
        return motoList;
    }

    public void setMotoList(List<MotorItem> motoList) {
        this.motoList = motoList;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
