package dashboard.enums;

public enum UserStatus {
	ACTIVE("ACTIVE") {
		@Override
		public String getLabel() {
			return "Hoạt động";
		}
	},
	BLOCK("BLOCK") {
		@Override
		public String getLabel() {
			return "Bị khoá";
		}
	},
	INACTIVE("INACTIVE") {
		@Override
		public String getLabel() {
			return "Chưa kích hoạt";
		}
	},
	DELETED("DELETED") {
		@Override
		public String getLabel() {
			return "Bị xoá";
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
