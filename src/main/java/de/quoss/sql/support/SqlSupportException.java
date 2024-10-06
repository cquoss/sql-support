package de.quoss.sql.support;

public class SqlSupportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SqlSupportException(final String s) {
        super(s);
    }

    public SqlSupportException(final Throwable t) {
        super(t);
    }

    public SqlSupportException(final String s, final Throwable t) {
        super(s, t);
    }

}
