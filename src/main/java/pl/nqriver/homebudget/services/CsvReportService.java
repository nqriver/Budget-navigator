package pl.nqriver.homebudget.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import pl.nqriver.homebudget.exceptions.ReportGenerationException;
import pl.nqriver.homebudget.services.dtos.csv.CsvRecord;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class CsvReportService {
    public static final char DELIMITER = ';';

    public <T extends CsvRecord> ByteArrayInputStream writeAsCsv(List<T> allRecords) {
        String[] header = getHeader(allRecords);
        CSVFormat format = CSVFormat.Builder
                .create()
                .setHeader(header)
                .setDelimiter(DELIMITER)
                .build();
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)
        ) {
            for (var recordDto : allRecords) {
                csvPrinter.printRecord(recordDto.getPropertiesAsList());
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException exception) {
            throw new ReportGenerationException("Failed to export data to csv file\n[Internal exception is]:" + exception.getMessage());
        }
    }

    private <T extends CsvRecord> String[] getHeader(List<T> allRecords) {
        return !allRecords.isEmpty() ? allRecords.get(0)
                .getHeader().toArray(String[]::new) : new String[]{};
    }

}
