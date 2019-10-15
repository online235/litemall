package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallMerSms;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallUserExample;

public interface LitemallUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    long countByExample(LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int insert(LitemallUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int insertSelective(LitemallUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    LitemallUser selectOneByExample(LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    LitemallUser selectOneByExampleSelective(@Param("example") LitemallUserExample example, @Param("selective") LitemallUser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    List<LitemallUser> selectByExampleSelective(@Param("example") LitemallUserExample example, @Param("selective") LitemallUser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    List<LitemallUser> selectByExample(LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    LitemallUser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallUser.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    LitemallUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    LitemallUser selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallUser record, @Param("example") LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallUser record, @Param("example") LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);

    int addOrTokenLogin(@Param("token")String token ,@Param("time") String time,@Param("username")String username);

    int updTokenCreate(@Param("token")String token ,@Param("time") String time);

    LitemallUser selectLitemallLogin(@Param("username") String username);

    LitemallUser selectLitemallLoginToekn(@Param("token") String token);

    int AddLitemallUser(LitemallUser litemallUser);

    int selectLitemallByPhone(@Param("phone") String phone);

    LitemallUser LoginPhone(@Param("phone") String phone);

    int updTokenLitemallUser(@Param("phone") String phone,@Param("token") String token,@Param("time") String time);

    int updOutLoginLitemallUser(@Param("phone") String phone,@Param("token") String token,@Param("username")String username);


}