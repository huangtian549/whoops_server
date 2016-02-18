package com.whoops.po;

import java.util.Date;

public class Msg extends BasePo{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column msg.id
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column msg.msg
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    private String msg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column msg.post_id
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    private Integer postId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column msg.uid
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    private Integer uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column msg.type
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column msg.status
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column msg.create_date
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    private Date createDate;
    
    
    private String image;

    private String content;
    
    
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column msg.id
     *
     * @return the value of msg.id
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column msg.id
     *
     * @param id the value for msg.id
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column msg.msg
     *
     * @return the value of msg.msg
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public String getMsg() {
        return msg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column msg.msg
     *
     * @param msg the value for msg.msg
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column msg.post_id
     *
     * @return the value of msg.post_id
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column msg.post_id
     *
     * @param postId the value for msg.post_id
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column msg.uid
     *
     * @return the value of msg.uid
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column msg.uid
     *
     * @param uid the value for msg.uid
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column msg.type
     *
     * @return the value of msg.type
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column msg.type
     *
     * @param type the value for msg.type
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column msg.status
     *
     * @return the value of msg.status
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column msg.status
     *
     * @param status the value for msg.status
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column msg.create_date
     *
     * @return the value of msg.create_date
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column msg.create_date
     *
     * @param createDate the value for msg.create_date
     *
     * @mbggenerated Fri Jun 12 13:15:29 EDT 2015
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
    
    
    
}