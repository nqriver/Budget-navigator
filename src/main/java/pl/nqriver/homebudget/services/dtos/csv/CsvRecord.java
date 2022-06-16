package pl.nqriver.homebudget.services.dtos.csv;

import java.util.List;

public interface CsvRecord {
    List<String> getPropertiesAsList();
    List<String> getHeader();
}
