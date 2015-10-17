package com.whoops.vo;

import com.whoops.po.Msg;

public class MsgVo extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String content;
	
	private String comment;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	

}
