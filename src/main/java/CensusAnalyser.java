import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<StateCensusCSV> censusCSVIterator = getCSVFileIterator(reader, StateCensusCSV.class);
            int numOfEateries = 0;
            while (censusCSVIterator.hasNext()) {
                numOfEateries++;
                censusCSVIterator.next();
            }
            return numOfEateries;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NOT_A_CSV_TYPE_OR_HEADERS_INVALID);
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            Iterator<IndianStateCodeCSV> censusCSVIterator = getCSVFileIterator(reader, IndianStateCodeCSV.class);
            int numOfEateries = 0;
            while (censusCSVIterator.hasNext()) {
                numOfEateries++;
                censusCSVIterator.next();
            }
            return numOfEateries;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NOT_A_CSV_TYPE_OR_HEADERS_INVALID);
        }
    }

    public <E> Iterator<E> getCSVFileIterator (Reader reader, Class<E> csvClass) throws CensusAnalyserException{
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }catch (IllegalStateException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }catch (RuntimeException e){
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NOT_A_CSV_TYPE_OR_HEADERS_INVALID);
        }
    }

}