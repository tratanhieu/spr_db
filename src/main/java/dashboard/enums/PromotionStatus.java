package dashboard.enums;

public enum PromotionStatus {
    AVAILABLE("AVAILABLE"){
        @Override
        public String getLabel() {
            return "Đang diễn ra";
        }
    },
    NOT_AVAILABLE("NOT_AVAILABLE"){
        @Override
        public String getLabel(){
            return "chưa diễn ra";
        }
    },
    STOP("STOP"){
        @Override
        public String getLabel(){
            return "kết thúc";
        }
    };

    private String name;

    PromotionStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getLabel();
}
