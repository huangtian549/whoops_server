package com.whoops.po;

import java.util.Date;

public class FavorSchool extends BasePo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_school.Id
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_school.uid
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    private Integer uid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_school.school_id
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    private Integer schoolId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column favor_school.create_date
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    private Date createDate;

	private String nameCn;

	private String nameEn;

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column favor_school.Id
	 * 
	 * @return the value of favor_school.Id
	 * 
	 * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
	 */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_school.Id
     *
     * @param id the value for favor_school.Id
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column favor_school.uid
     *
     * @return the value of favor_school.uid
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_school.uid
     *
     * @param uid the value for favor_school.uid
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column favor_school.school_id
     *
     * @return the value of favor_school.school_id
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_school.school_id
     *
     * @param schoolId the value for favor_school.school_id
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column favor_school.create_date
     *
     * @return the value of favor_school.create_date
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column favor_school.create_date
     *
     * @param createDate the value for favor_school.create_date
     *
     * @mbggenerated Mon Mar 09 17:01:09 EDT 2015
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}