package com.example.invoicelogger.database;

public class LogDbSchema {
    public static final class invoiceTable {
        public static final String NAME = "invoices";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String DESTINATION = "destination";
            public static final String TYPE = "type";
            public static final String LAT = "lat";
            public static final String LON = "lon";
            public static final String COMMENT = "comment";
            public static final String DURATION = "duration";
        }
    }
    public static final class SettingsTable {
        public static final String NAME = "settings";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String GENDER = "gender";
            public static final String COMMENT = "comment";
        }
    }
}
