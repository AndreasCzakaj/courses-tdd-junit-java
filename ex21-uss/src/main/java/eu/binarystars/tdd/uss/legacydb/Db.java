package eu.binarystars.tdd.uss.legacydb;

import java.util.HashMap;
import java.util.Map;

public class Db {
    private Db() {
        final var users =  new TableData();
        users.data.add(Map.of("email", "unverified@gmail.com", "passwordHash", "correctPassword", "verified", Boolean.FALSE));
        users.data.add(Map.of("email", "verified@gmail.com", "passwordHash", "correctPassword", "verified", Boolean.TRUE));
        this.tables.put("users", users);
    }

    private static final Db instance = new Db();

    public static Db getInstance() {
        return instance;
    }

    private final Map<String, TableData> tables = new HashMap<>();

    public TableData getTableData(String name) throws FreakyDbException {
        if (this.tables.containsKey(name)) {
            return tables.get(name);
        }
        throw new FreakyDbException("SQL Error: no such table: " + name);
    }


    public TableData executeQuery(String table, String filter) throws FreakyDbException {
        final var out =  new TableData();
        final var tableData = getTableData("users");
        tableData.data.stream()
                .filter(row -> row.get("email").equals(filter))
                .forEach(row -> out.data.add(
                        Map.of(
                            "email", row.get("email"),
                            "passwordHash", row.get("passwordHash"),
                            "verified", row.get("verified")
                        )
                )
        );
        return out;
    }
}
