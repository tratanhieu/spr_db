package dashboard.enums;

public enum EntityStatus {
	ACTIVE("ACTIVE") {
		@Override
		public String getLabel() {
			return "Hiển thị";
		}
	},
	SUSPENSION("SUSPENSION") {
		@Override
		public String getLabel() {
			return "Ngừng kinh doanh";
		}
	},
	STOP("STOP") {
		@Override
		public String getLabel() {
			return "Đã dừng";
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
