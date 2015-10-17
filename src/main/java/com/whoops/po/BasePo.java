package com.whoops.po;

import java.io.Serializable;

public class BasePo implements Serializable {

	private int startPage;
	private int endPage;

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

}
