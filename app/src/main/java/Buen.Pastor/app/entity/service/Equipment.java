package Buen.Pastor.app.entity.service;

public class Equipment {

    private int id;
    private String equipmentType;
    private String barcode;
    private String assetCode;
    private String description;
    private String status;
    private String purchaseDate;
    private String brand;
    private String model;
    private String equipmentName;
    private String orderNumber;
    private String serial;
    private Teacher responsible;
    private Location location;

    public Equipment() {
    }

    public Equipment(int id) {
        this.id = id;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Teacher getResponsible() {
        return responsible;
    }

    public void setResponsible(Teacher responsible) {
        this.responsible = responsible;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setResponsableId(int id) {
        Teacher emp = new Teacher();
        emp.setId(id);
        this.responsible = emp;
    }

    public void setUbicacionId(int id) {
        Location ubic = new Location();
        ubic.setId(id);
        this.location = ubic;
    }

}
