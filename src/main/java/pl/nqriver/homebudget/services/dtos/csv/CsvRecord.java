package pl.nqriver.homebudget.services.dtos.csv;

import java.util.List;

public abstract class CsvRecord {
    public abstract List<String> getPropertiesAsList();
    public abstract List<String> getHeader();
}
