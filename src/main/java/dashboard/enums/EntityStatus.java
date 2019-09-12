package dashboard.enums;

public enum EntityStatus {
	
	ACTIVE("ACTIVE") {
		@Override
		public String getLabel() {
			return "Hiển thị";
		}
	},
	HIDDEN("HIDDEN") {
		@Override
		public String getLabel() {
			return "Bị ẩn";
		}
	},
	DELETED("DELETED") {
		@Override
		public String getLabel() {
			return "Đã xóa";
		}
	};
	
	private String name;
	
	EntityStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract String getLabel();
}
