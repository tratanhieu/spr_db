package dashboard.enums;

public enum FeaturesStatus {

    ACTIVE("YES") {
        @Override
        public String getLabel() {
            return "Yes";
        }
    },
    INACTIVE("NO") {
        @Override
        public String getLabel() {
            return "No";
        }
    };

    private String name;

    FeaturesStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getLabel();
}
