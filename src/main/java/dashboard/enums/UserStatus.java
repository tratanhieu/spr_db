package dashboard.enums;

public enum UserStatus {
	ACTIVE("ACTIVE") {
		@Override
		public String getLabel() {
			return "Hiển thị";
		}
	},
	BLOCK("BLOCK") {
		@Override
		public String getLabel() {
			return "Bị ẩn";
		}
	},
	DELETED("DELETED") {
		@Override
		public String getLabel() {
			return "Bị ẩn";
		}
	};
	
	private String name;
	
	UserStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract String getLabel();
}
