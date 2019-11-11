package lk.excellent.pharamacy_management.asset.commonAsset.Enum;

public enum  Status {

    OUTOFSTOCK("Out of Stock"),
    ONDEMAND("On demand");
    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
