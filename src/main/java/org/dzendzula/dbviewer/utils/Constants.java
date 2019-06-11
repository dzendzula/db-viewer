package org.dzendzula.dbviewer.utils;

public class Constants {

    //REST mapping
    public static final String DB_SETTINGS_CONTROLLER_MAPPING = "/settings";

    //DB connection constants
    public static final String JDBC_POSTGRESQL_PREFIX = "jdbc:postgresql://";

    //JDBC constants

    public static final String JDBC_SCHEMA_INFO_COLUMNAME = "TABLE_SCHEM";
    public static final String JDBC_TABLE_INFO_COLUMNAME = "TABLE_NAME";

    public static final String JDBC_COLUMN_INFO_NAME = "COLUMN_NAME";
    public static final String JDBC_COLUMN_INFO_DATA_TYPE = "DATA_TYPE";
    public static final String JDBC_COLUMN_INFO_TYPE_NAME = "TYPE_NAME";
    public static final String JDBC_COLUMN_INFO_COLUMN_SIZE = "COLUMN_SIZE";
    public static final String JDBC_COLUMN_INFO_DECIMAL_DIGITS = "DECIMAL_DIGITS";
    public static final String JDBC_COLUMN_INFO_NUM_PREC_RADIX = "NUM_PREC_RADIX";
    public static final String JDBC_COLUMN_INFO_REMARKS = "REMARKS";
    public static final String JDBC_COLUMN_INFO_COLUMN_DEF = "COLUMN_DEF";
    public static final String JDBC_COLUMN_INFO_CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";
    public static final String JDBC_COLUMN_INFO_ORDINAL_POSITION = "ORDINAL_POSITION";
    public static final String JDBC_COLUMN_INFO_IS_NULLABLE = "IS_NULLABLE";
    public static final String JDBC_COLUMN_INFO_IS_AUTOINCREMENT = "IS_AUTOINCREMENT";
    public static final String JDBC_COLUMN_INFO_IS_GENERATEDCOLUMN = "IS_GENERATEDCOLUMN";

    public static final String JDBC_PK_INFO_COLUMNAME = "COLUMN_NAME";
    public static final String JDBC_PK_INFO_PKNAME = "PK_NAME";

    public static final String SQL_SELECT_ALL_FROM = "SELECT * FROM ";


    public static final String PAGE_DAFAULT_LIMIT = "50";
}
