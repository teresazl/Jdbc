package com.teresazl.jdbc.template;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Teresa
 */
public interface RawMapper {
    public Object rawMap(ResultSet rs) throws SQLException;
}
