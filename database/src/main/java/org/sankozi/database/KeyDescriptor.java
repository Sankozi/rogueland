package org.sankozi.database;

/**
 *
 * @author sankozi
 */
public final class KeyDescriptor<T> {
	private final String name;
	private final ValueType type;
	private final int length;

	enum ValueType {
		STRING;
	}

	KeyDescriptor(String name, ValueType type, int length) {
		this.name = name;
		this.type = type;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public ValueType getType() {
		return type;
	}

	public int getLength() {
		return length;
	}
}
