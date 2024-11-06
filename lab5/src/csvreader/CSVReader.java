package csvreader;
import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;

    /**
     * 12. Napisz funkcje CSVReader zwracające czas i datę. Ich dodatkowym parametrem powinien być format zapisu, czyli np. LocalTime getTime(int columnIndes,String format).
     *
     * Możesz wykorzystać poniższe fragmenty kodu
     *
     *         LocalTime time = LocalTime.parse("12:55",DateTimeFormatter.ofPattern("HH:mm"));
     *         System.out.println(time);
     *         time = LocalTime.parse("12:55:23",DateTimeFormatter.ofPattern("HH:mm:ss"));
     *         System.out.println(time);
     *
     *         LocalDate date = LocalDate.parse("2017-11-30", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
     *         System.out.println(date);
     *         date = LocalDate.parse("23.11.2017", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
     *         System.out.println(date);
     * Możesz także napisać funkcje zwracające LocalDateTime (połaczenie daty i czasu).
      */

     /**
     *
     * @param filename - nazwa pliku
     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    // nazwy kolumn w takiej kolejności, jak w pliku
    List<String> columnLabels = new ArrayList<>();
    // odwzorowanie: nazwa kolumny -> numer kolumny
    Map<String,Integer> columnLabelsToInt = new HashMap<>();
    String[]current;


    public CSVReader(String filename,String delimiter,boolean hasHeader) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader)parseHeader();
    }

    //implementacja 1,3:
    public CSVReader(String filename,String delimiter) throws IOException {
        this(filename,delimiter,false);
    }

    //implementacja 1,3 ciąg dalszy:
    public CSVReader(String filename) throws IOException {
        this(filename,",",false);
    }

    //implementacja 2:
    public CSVReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader)parseHeader();
    }

    //implementacja 4:
    public List<String> getColumnLabels(){
        return columnLabels;
    }

    //implementacja 5:
    public int getRecordLength(){
        return current.length;
    }

    //implementacja 6:
    public boolean isMissing(int columnIndex){
        if(columnIndex >= current.length || current[columnIndex].isEmpty()){
            return true;
        }
        return false;
    }

    //implementacja 7:
    public boolean isMissing(String columnLabel){
        return isMissing(columnLabelsToInt.get(columnLabel));
    }

    //implementacja 8:
    public String get(int columnIndex){
        if(isMissing(columnIndex)){
            return "";
        }
        return current[columnIndex];
    }

    //implementacja 8 ciąg dalszy:
    public String get(String columnLabel){
        return get(columnLabelsToInt.get(columnLabel));
    }

    //implementacja 9:
    public int getInt(int columnIndex){
        return Integer.parseInt(get(columnIndex));
    }

    //implementacja 9 ciąg dalszy:
    public int getInt(String columnLabel){
        return getInt(columnLabelsToInt.get(columnLabel));
    }

    //implementacja 10:
    public long getLong(int columnIndex){
        return Long.parseLong(get(columnIndex));
    }

    //implementacja 10 ciąg dalszy:
    public long getLong(String columnLabel){
        return getLong(columnLabelsToInt.get(columnLabel));
    }

    //implementacja 11:
    public double getDouble(int columnIndex){
        return Double.parseDouble(get(columnIndex));
    }

    //implementacja 11 ciąg dalszy:
    public double getDouble(String columnLabel){
        return getDouble(columnLabelsToInt.get(columnLabel));
    }

    //implementacja 12:
    public LocalTime getTime(int columnIndex,String format){
        return LocalTime.parse(get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    public LocalTime getTime(String columnLabel,String format){
        return LocalTime.parse(get(columnLabelsToInt.get(columnLabel)), DateTimeFormatter.ofPattern(format));
    }

    //implementacja 12 ciąg dalszy:
    public LocalDate getDate(int columnIndex,String format){
        return LocalDate.parse(get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    public LocalDate getDate(String columnLabel,String format){
        return LocalDate.parse(get(columnLabelsToInt.get(columnLabel)), DateTimeFormatter.ofPattern(format));
    }


    void parseHeader() throws IOException {
        // wczytaj wiersz
        String line = reader.readLine();
        if (line == null) {
            return;
        }
        // podziel na pola
        String[] header = line.split(delimiter);
        // przetwarzaj dane w wierszu
        for (int i = 0; i < header.length; i++) {
            // dodaj nazwy kolumn do columnLabels i numery do columnLabelsToInt
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i], i);
        }
    }

    boolean next() throws IOException {
        // czyta następny wiersz, dzieli na elementy i przypisuje do current
        //
        String line = reader.readLine();
        if(line == null){
            return false;
        }
        current = line.split(delimiter);
        return true;
    }




    public static void main(String[] args) throws IOException {
        CSVReader reader = new CSVReader("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/elec.csv",",",true);
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/output.csv"), "Cp1250"));
        output.printf("Time,Date,Period,Class,NSWDemand\n");
        while (reader.next()) {
            //print column names
            int id = reader.getInt("period");
            String name = reader.get("class");
            double fare = reader.getDouble("nswdemand");
            //get local time
            LocalTime time = reader.getTime("hour","HH:mm");
            //get local date
            LocalDate date = reader.getDate("date","yyyy-MM-dd");
            output.printf("%s,%s, ",time,date);
            output.printf("%d,%s,%f\n",id, name, fare);
        }
    }
//    public static void main(String[] args) throws IOException {
//        CSVReader reader = new CSVReader("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/elec.csv",",",true);
//
//        while(reader.next()){
//            int id = reader.getInt("period");
//            String name = reader.get("class");
//            double fare = reader.getDouble("nswdemand");
//            //get local time
//            LocalTime time = reader.getTime("hour","HH:mm");
//            //get local date
//            LocalDate date = reader.getDate("date","yyyy-MM-dd");
//
//            System.out.printf("%s - %s",time,date);
//            System.out.printf("%d %s %f\n",id, name, fare);
//
//        }
//        System.out.println("\n");
//        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
//        System.out.println("\n");
//        CSVReader reader2 = new CSVReader("/Users/mikolaj/Desktop/work/java_zajecia/lab5/resources/elec.csv",",",true);
//        while(reader2.next()){
//            int id = reader2.getInt(2);
//            String name = reader2.get(8);
//            double fare = reader2.getDouble(4);
//            //get local time
//            LocalTime time = reader.getTime(1,"HH:mm");
//            //get local date
//            LocalDate date = reader.getDate(0,"yyyy-MM-dd");
//
//            System.out.printf("%s - %s",time,date);
//            System.out.printf("%d %s %f\n",id, name, fare);
//        }
//    }


}