package com.whoops.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whoops.po.FavorPost;
import com.whoops.po.Post;
import com.whoops.po.PostExample;

public interface PostMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int countByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int deleteByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int insert(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int insertSelective(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    List<Post> selectByExample(PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    Post selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int updateByExampleSelective(@Param("record") Post record, @Param("example") PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int updateByExample(@Param("record") Post record, @Param("example") PostExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int updateByPrimaryKeySelective(Post record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table post
     *
     * @mbggenerated Fri Mar 06 18:31:40 EST 2015
     */
    int updateByPrimaryKey(Post record);

	List<Post> listHotByLocation(Post post);

	List<Post> listNewBySchool(Post post);

	List<Post> listHotBySchool(Post post);

	List<Post> listByUid(Post post);

	List<Post> listByCommentAndUid(Post post);
	
	List<Post> listHotAll(Post post);
	
	List<Post> listFavorPostByUid(FavorPost favorPost);

	List<Post> listNewByLocation(Post post);
}