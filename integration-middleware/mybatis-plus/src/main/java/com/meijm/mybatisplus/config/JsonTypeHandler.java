package com.meijm.mybatisplus.config;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
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
@MappedTypes(value = JSONObject.class)
public class JsonTypeHandler extends BaseTypeHandler<JSONObject>{
        /**
         * Json编码，对象 ==> Json字符串
         */
        @Override
        public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
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
        public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
            String result = rs.getString(columnName);
            return result == null ? null : JSONUtil.parseObj(result);
        }

        /**
         * Json解码，Json字符串 ==> 对象
         */
        @Override
        public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
            String result = rs.getString(columnIndex);
            return result == null ? null : JSONUtil.parseObj(result, JSONConfig.create().setOrder(true));
        }

        /**
         * Json解码，Json字符串 ==> 对象
         */
        @Override
        public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
            String result = cs.getString(columnIndex);
            return result == null ? null : JSONUtil.parseObj(result);
        }
}

