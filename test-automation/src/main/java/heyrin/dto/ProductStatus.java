package heyrin.dto;

public enum ProductStatus {

    SOLD_OUT("status-sold-out"),
    AVAILABLE("status-available");
    private String styleClass;

    ProductStatus(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
