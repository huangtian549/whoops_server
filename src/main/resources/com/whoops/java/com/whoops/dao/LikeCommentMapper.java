package com.whoops.dao;

import com.whoops.po.LikeComment;
import com.whoops.po.LikeCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LikeCommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int countByExample(LikeCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int deleteByExample(LikeCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int insert(LikeComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int insertSelective(LikeComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    List<LikeComment> selectByExample(LikeCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    LikeComment selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int updateByExampleSelective(@Param("record") LikeComment record, @Param("example") LikeCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int updateByExample(@Param("record") LikeComment record, @Param("example") LikeCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int updateByPrimaryKeySelective(LikeComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like_comment
     *
     * @mbggenerated Wed Apr 15 16:56:51 EDT 2015
     */
    int updateByPrimaryKey(LikeComment record);
}