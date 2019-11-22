package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Collect;
import com.cskaoyan.mall.bean.CollectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectMapper {

    long countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    Collect queryCollect(@Param("userId") Integer userId, @Param("valueId") Integer valueId);

    void deleteById(@Param("valueId") Integer valueId, @Param("userId") Integer userId);

    void insertById(@Param("userId") Integer userId, @Param("valueId") Integer valueId);

    List<Integer> queryGoodsIdByUserId(@Param("userId") Integer userId);
}
