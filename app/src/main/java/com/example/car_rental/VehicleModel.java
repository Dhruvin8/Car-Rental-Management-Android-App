package com.example.car_rental;

public class VehicleModel {


    public VehicleModel(String VId, String VDes, String Vtype, String VCategory) {
        this.VId = VId;
        this.VDes = VDes;
        this.Vtype = Vtype;
        this.VCategory= VCategory;
    }

    private String VId;
    private String VDes;
    private String Vtype;
    private String VCategory;

    public String getVId() {
        return VId;
    }

    public void setVId(String VId) {
        this.VId = VId;
    }

    public String getVDes() {
        return VDes;
    }

    public void setVDes(String VDes) {
        this.VDes = VDes;
    }

    public String getVtype() {
        return Vtype;
    }

    public void setVtype(String phone) {
        this.Vtype = phone;
    }

    public String getVCategory() {
        return VCategory;
    }

    public void setVCategory(String phone) {
        this.VCategory = phone;
    }
}
