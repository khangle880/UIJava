package home.resource.classForDB;

import javafx.beans.property.SimpleStringProperty;

public class Address {
    private SimpleStringProperty completeStreet;
    private SimpleStringProperty village;
    private SimpleStringProperty district;
    private SimpleStringProperty province;

    Address() {
        this.completeStreet = new SimpleStringProperty();
        this.village = new SimpleStringProperty();
        this.district = new SimpleStringProperty();
        this.province = new SimpleStringProperty();
    };

    public Address(String completeStreet, String village, String district, String province) {
        this.completeStreet = new SimpleStringProperty(completeStreet);
        this.village = new SimpleStringProperty(village);
        this.district = new SimpleStringProperty(district);
        this.province = new SimpleStringProperty(province);
    }

    public String getCompleteStreet() {
        return completeStreet.get();
    }

    public void setCompleteStreet(String completeStreet) {
        this.completeStreet.set(completeStreet);
    }

    public String getVillage() {
        return village.get();
    }

    public void setVillage(String village) {
        this.village.set(village);
    }

    public String getDistrict() {
        return district.get();
    }

    public void setDistrict(String district) {
        this.district.set(district);
    }

    public String getProvince() {
        return province.get();
    }

    public void setProvince(String province) {
        this.province.set(province);
    }
}
