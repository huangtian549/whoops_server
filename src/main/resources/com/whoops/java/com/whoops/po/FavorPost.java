package com.whoops.po;

import java.util.Date;

public class FavorPost {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_post.id
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_post.uid
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    private Integer uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_post.post_id
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    private Integer postId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_post.create_date
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    private Date createDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column favor_post.id
     *
     * @return the value of favor_post.id
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_post.id
     *
     * @param id the value for favor_post.id
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column favor_post.uid
     *
     * @return the value of favor_post.uid
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_post.uid
     *
     * @param uid the value for favor_post.uid
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column favor_post.post_id
     *
     * @return the value of favor_post.post_id
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public Integer getPostId() {
        return postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_post.post_id
     *
     * @param postId the value for favor_post.post_id
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column favor_post.create_date
     *
     * @return the value of favor_post.create_date
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_post.create_date
     *
     * @param createDate the value for favor_post.create_date
     *
     * @mbggenerated Tue Mar 31 19:27:19 EDT 2015
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}