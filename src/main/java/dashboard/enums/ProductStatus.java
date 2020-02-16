package dashboard.enums;

public enum ProductStatus {
    ACTIVE("ACTIVE") {
        @Override
        public String getLabel() {
            return "Kích hoạt";
        }
    },
    OUT_OF_STOCK("OUT_OF_STOCK") {
        @Override
        public String getLabel() {
            return "Kích hoạt";
        }
    },
    SUSPENSION("SUSPENSION") {
        @Override
        public String getLabel() {
            return "Tạm dừng";
        }
    },
    STOP("STOP") {
        @Override
        public String getLabel() {
            return "Ngừng kinh doanh";
        }
    };

    private String name;

    ProductStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getLabel();
}
