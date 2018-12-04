package tech.qijin.util4j.mybatis.handler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import tech.qijin.util4j.lang.constant.EnumValue;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//TODO 叫converter会更好
public class EnumValueTypeHandler<E extends Enum<?> & EnumValue> extends BaseTypeHandler<E> {
    private Class<E> type;

    public EnumValueTypeHandler(Class<E> type) {
        this.type = type;
        Enum[] enums = (Enum[]) type.getEnumConstants();
        if (enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : this.getEnumInstance(this.type, value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : this.getEnumInstance(this.type, value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : this.getEnumInstance(this.type, value);
    }

    private E getEnumInstance(Class<E> type, int enumValue) {
        for (E e : type.getEnumConstants()) {
            if (e.value() == enumValue) {
                return e;
            }
        }
        return null;
    }
}