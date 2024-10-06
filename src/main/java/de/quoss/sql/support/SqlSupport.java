package de.quoss.sql.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SqlSupport {

    private SqlSupport() {}

    public static List<Map<String, Object>> execute(final String url, final String sql) throws SqlSupportException {
        final List<Map<String, Object>> result = new LinkedList<>();
        try (final Connection connection = DriverManager.getConnection(url);
                final Statement statement = connection.createStatement()) {
            if (statement.execute(sql)) {
                try (final ResultSet resultSet = statement.getResultSet()) {
                    final ResultSetMetaData metaData = resultSet.getMetaData();
                    while (resultSet.next()) {
                        final Map<String, Object> row = new LinkedHashMap<>();
                        for (int i = 1; i <= metaData.getColumnCount(); i++) {
                            row.put(metaData.getColumnName(i), resultSet.getObject(i));
                        }
                        result.add(row);
                    }
                }
            } else {
                final Map<String, Object> row = new LinkedHashMap<>();
                result.add(row);
                row.put("UPDATE_COUNT", statement.getUpdateCount());
            }
        } catch (final SQLException e) {
            throw new SqlSupportException(e.getMessage(), e);
        }
        return Collections.unmodifiableList(result);
    }

}
