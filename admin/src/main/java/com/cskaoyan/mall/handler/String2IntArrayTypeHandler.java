package com.cskaoyan.mall.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Integer[].class)
public class String2IntArrayTypeHandler implements TypeHandler<Integer[]> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Integer[] integers, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, parseIntArray2String(integers));
    }

    private String parseIntArray2String(Integer[] integers) {
        try {
            return objectMapper.writeValueAsString(integers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return parseString2IntArray(string);
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        return parseString2IntArray(string);
    }

    @Override
    public Integer[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return parseString2IntArray(string);
    }

    private Integer[] parseString2IntArray(String string) {
        try {
            return objectMapper.readValue(string, Integer[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Integer[0];
    }
}
