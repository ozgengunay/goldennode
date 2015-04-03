package com.goldennode.api.replicatedmemorycluster;

public enum LockTypes {

	APPLICATION("$application"), CLUSTERED_OBJECT_MANAGER("$clusteredObjectManager"), CLUSTERED_SERVER_MANAGER(
			"$clusteredServerManager");

	private String name;

	private LockTypes(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}