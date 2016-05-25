package de.superteam2000.gwt.shared.bo;

public class Merkzettel extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private int gemerkterId = 0;
	private int merkerId = 0;

	public int getGemerkterId() {
		return gemerkterId;
	}

	public void setGemerkterId(int gemerkterId) {
		this.gemerkterId = gemerkterId;
	}

	public int getMerkerId() {
		return merkerId;
	}

	public void setMerkerId(int merkerId) {
		this.merkerId = merkerId;
	}

}
