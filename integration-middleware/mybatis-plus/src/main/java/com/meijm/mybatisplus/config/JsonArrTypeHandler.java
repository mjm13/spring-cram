package com.meijm.mybatisplus.config;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(value = JSONArray.class)
public class JsonArrTypeHandler extends BaseTypeHandler<JSONArray>{
        /**
         * Json编码，对象 ==> Json字符串
         */
        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, JSONArray parameter, JdbcType jdbcType) throws SQLException {
            String value = parameter.toString();
            if (jdbcType == null) {
                ps.setObject(i, value);
            } else {
                ps.setObject(i, value, jdbcType.TYPE_CODE);
            }
        }

        /**
         * Json解码，Json字符串 ==> 对象
         */
        @Override
        public JSONArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
            String result = rs.getString(columnName);
            return result == null ? null : JSONUtil.parseArray(result);
        }

        /**
         * Json解码，Json字符串 ==> 对象
         */
        @Override
        public JSONArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            String result = rs.getString(columnIndex);
            return result == null ? null : JSONUtil.parseArray(result);
        }

        /**
         * Json解码，Json字符串 ==> 对象
         */
        @Override
        public JSONArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            String result = cs.getString(columnIndex);
            return result == null ? null : JSONUtil.parseArray(result);
        }
}

