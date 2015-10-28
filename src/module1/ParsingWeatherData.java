package module1;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import java.io.*;
public class ParsingWeatherData {

	public static CSVRecord coldestHourInFile(CSVParser parser){
		CSVRecord smallestSoFar = null;
		
		for(CSVRecord currentRow:parser){
			smallestSoFar = getSmallestOfTwo(currentRow,smallestSoFar);
		}
		return smallestSoFar;
	}
	
	
	public static CSVRecord getSmallestOfTwo(CSVRecord currentRow,CSVRecord smallestSoFar){
		
		if(smallestSoFar == null){
			double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
			if(currentTemp != -9999.0f){
				smallestSoFar = currentRow;
			}
		}else{
			double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
			double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
			
			if((currentTemp != -9999.0f) && (currentTemp < smallestTemp)){
				smallestSoFar = currentRow;
			}
		}
		return smallestSoFar;
	}
	
	public static CSVRecord getSmallestHumidityOfTwo(CSVRecord currentRow,CSVRecord smallestSoFar){
		
		String humidity = currentRow.get("Humidity");
		if(!humidity.equalsIgnoreCase("N/A")){
			if(smallestSoFar == null){
					smallestSoFar = currentRow;
			}else{
				double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
				double smallestHumidity = Double.parseDouble(smallestSoFar.get("Humidity"));
				
				if(currentHumidity < smallestHumidity){
					smallestSoFar = currentRow;
				}
			}
		}
		return smallestSoFar;
	}
	
	public static String fileWithColdestTemperature(){
		
		CSVRecord smallestSoFar = null;
		DirectoryResource dir = new DirectoryResource();
		String fileName = null;
		
		for(File g : dir.selectedFiles()){
			FileResource fr = new FileResource(g);
			CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
			smallestSoFar = getSmallestOfTwo(currentRow,smallestSoFar);
			if(smallestSoFar.equals(currentRow)){
				fileName = g.getAbsolutePath();
			}
		}
		
		return fileName;
	}

	
	public static CSVRecord lowestHumidityInFile(CSVParser parser){
		CSVRecord smallestSoFar = null;
		
		for(CSVRecord currentRow:parser){
			smallestSoFar = getSmallestHumidityOfTwo(currentRow,smallestSoFar);
		}
		return smallestSoFar;
	}
	
	public static CSVRecord lowestHumidityInManyFiles(){
		CSVRecord smallestSoFar = null;
		DirectoryResource dir = new DirectoryResource();
		for(File g : dir.selectedFiles()){
			FileResource fr = new FileResource(g);
			CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
			smallestSoFar = getSmallestHumidityOfTwo(currentRow,smallestSoFar);
		}
		return smallestSoFar;
	}
	
	static double averageTemperatureInFile(CSVParser parser){
		double totalTemperature = 0.0;
		double totalNumberRows = 0.0;
		for(CSVRecord currentRow:parser){
			double value = Double.parseDouble(currentRow.get("TemperatureF"));
			if(value != -9999.0f){
				totalTemperature+=value;
			}
			totalNumberRows++;
		}
		return (double)totalTemperature/(double)totalNumberRows;
	}
	
	public static double averageTemperatureWithHighHumidityInFile(CSVParser parser,int humidityNo){
		double totalTemperature = 0.0;
		double totalNumberRows = 0.0; 
		for(CSVRecord currentRow:parser){
			String humidity = currentRow.get("Humidity");
			if(!humidity.equalsIgnoreCase("N/A")){
				int value = Integer.parseInt(humidity);
				if(value>= humidityNo ){
					totalTemperature+=Double.parseDouble(currentRow.get("TemperatureF"));
					totalNumberRows++;
				}
			}
		}
		return (double)totalTemperature/(double)totalNumberRows;
	}
	
	public static void testColdestHourInFile(){
	
		FileResource fr = new FileResource();
		CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
		System.out.print("Coldest Temperature is: "+ smallest.get("TemperatureF"));
		System.out.println(" at: "+ smallest.get("DateUTC"));
	}
	
	public static void testFileWithColdestTemperature(){
		String fileName = fileWithColdestTemperature();
		File f = new File(fileName);
		FileResource fr = new FileResource(f);
		CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
		System.out.println("Coldest day was in File: "+fileName);
		System.out.println("Coldest Temperature on that was: "+ smallest.get("TemperatureF"));
		System.out.println("All the temperature on that day were:");
		for(CSVRecord currentRow:fr.getCSVParser()){
			System.out.println(currentRow.get("DateUTC") + " " + currentRow.get("TemperatureF"));
		}
	}
	
	public static void testLowestHumidityInFile(){
		FileResource fr = new FileResource();
		CSVRecord smallest = lowestHumidityInFile(fr.getCSVParser());
		System.out.print("Lowest Humidity was: "+ smallest.get("Humidity"));
		System.out.println(" at: "+ smallest.get("DateUTC"));
	}
	
	public static void testLowestHumidityInManyFiles(){
		CSVRecord smallest = lowestHumidityInManyFiles();
		System.out.print("Lowest Humidity was: "+ smallest.get("Humidity"));
		System.out.println(" at: "+ smallest.get("DateUTC"));
	}
	
	public static void testAverageTemperatureInFile(){
		FileResource fr = new FileResource();
		double avgTemp = averageTemperatureInFile(fr.getCSVParser());
		System.out.println("Average temperature in file is: "+avgTemp);
	}
	
	public static void testAverageTemperatureWithHighHumidityInFile(){
		FileResource fr = new FileResource();
		int humidity = 80;
		double avgTemp = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(),humidity);
		if(avgTemp>0.0){
			System.out.println("Average temperature with humidity: "+humidity + " is "+avgTemp);
		}else{
			System.out.println("No temperature with that humidity");
		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testColdestHourInFile();
		testFileWithColdestTemperature();
		testLowestHumidityInFile();
		testLowestHumidityInManyFiles();
		testAverageTemperatureInFile();
		testAverageTemperatureWithHighHumidityInFile();
		
	}

}
