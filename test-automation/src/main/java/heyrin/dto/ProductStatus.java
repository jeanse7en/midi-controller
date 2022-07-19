package heyrin.dto;

public enum ProductStatus {

    SOLD_OUT("status-sold-out"),
    NEW_ARRIVAL("status-available"),
    AVAILABLE("status-available"),
    LOW_IN_STOCK("status-low-in-stock");
    private String styleClass;

    ProductStatus(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
