package my.nvinz.responseshare.data.enums;

public enum RequestLanguageType {
    XML("XML"),
    JSON("JSON");

    private String language;

    RequestLanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
