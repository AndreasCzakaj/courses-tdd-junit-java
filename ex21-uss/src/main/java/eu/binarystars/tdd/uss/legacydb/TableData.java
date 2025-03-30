package eu.binarystars.tdd.uss.legacydb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableData {
    final List<Map<String, Object>> data = new ArrayList<>();
    private int cursorIndex = 0;

    public boolean hasNext() {
        return cursorIndex < data.size();
    }

    public void next() throws FreakyDbException {
        cursorIndex++;
    }


    public Object getColumnData(String name) throws FreakyDbException {
        try {
            final var row = this.data.get(this.cursorIndex);
            return row.get(name);
        } catch (Exception e) {
            throw new FreakyDbException(e);
        }
    }
}
