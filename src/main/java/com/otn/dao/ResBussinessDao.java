package com.otn.dao;

import com.otn.entity.ResBussiness;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * 光通道（业务）层的Dao接口
 */
@Repository
public interface ResBussinessDao extends Mapper<ResBussiness> {

    @Insert("<script>" +
            "insert into res_bussiness(\n" +
            "        bussiness_name,\n" +
            "        bussiness_rate,\n" +
            "        circle_id,\n" +
            "        main_route,\n" +
            "        main_frequency,\n" +
            "        main_input_powers,\n" +
            "        main_output_powers,\n" +
            "        spare_route,\n" +
            "        spare_frequency,\n" +
            "        spare_input_powers,\n" +
            "        spare_output_powers,\n" +
            "        version_id) values\n" +
            "        <foreach collection=\"list\" item=\"item\" index=\"index\"\n" +
            "                 separator=\",\">\n" +
            "            (\n" +
            "            #{item.bussinessName},\n" +
            "            #{item.bussinessRate},\n" +
            "            #{item.circleId},\n" +
            "            #{item.mainRoute},\n" +
            "            #{item.mainFrequency},\n" +
            "            #{item.mainInputPowers},\n" +
            "            #{item.mainOutputPowers},\n" +
            "            #{item.spareRoute,jdbcType=VARCHAR},\n" +
            "            #{item.spareFrequency,jdbcType=VARCHAR},\n" +
            "            #{item.spareInputPowers,jdbcType=VARCHAR},\n" +
            "            #{item.spareOutputPowers,jdbcType=VARCHAR},\n" +
            "            #{item.versionId}\n" +
            "            )\n" +
            "        </foreach>" +
            "</script>")
    void batchInsert(@Param("list") List<ResBussiness> List);


}



