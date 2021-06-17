import com.opencsv.bean.CsvBindByName;

public class IndianStateCodeCSV {

        @CsvBindByName(column = "State", required = true)
        public String stateName;

        @CsvBindByName(column = "Code", required = true)
        public int stateCode;

        @Override
        public String toString() {
            return "IndiaStateCodeCSV{" +
                    "stateName='" + stateName + '\'' +
                    ", stateCode=" + stateCode +
                    '}';
        }
}
