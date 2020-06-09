package my.nvinz.responseshare.data.enums;

public enum RequestMethodType {
    HTTP("HTTP"),
    MQ("MQ");

    private String type;

    RequestMethodType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
