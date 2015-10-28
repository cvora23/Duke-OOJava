package module1;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

public class ParsingExportData {

	public static String countryInfo(CSVParser parser,String country){
		String info = "";
		for(CSVRecord record:parser){
			
			String mCountry = record.get("Country");
			if(mCountry.equalsIgnoreCase(country)){
				info = mCountry + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
				break;
			}
		}
		return info;
	}
	
	public static void listExportersTwoProducts(CSVParser parser, String exportItem1,String exportItem2){
		for(CSVRecord record:parser){
			
			String exports = record.get("Exports");
			if(exports.contains(exportItem1) && exports.contains(exportItem2)){
				System.out.println(record.get("Country"));
			}
			
		}
	}
	
	public static int numberOfExporters(CSVParser parser,String exportItem){
		int numberOfExporters = 0;
		
		for(CSVRecord record:parser){
			String exports = record.get("Exports");
			if(exports.contains(exportItem)){
				numberOfExporters++;
			}
		}
		
		return numberOfExporters;
	}
	
	public static void bigExporters(CSVParser parser,String amount){
		for(CSVRecord record:parser){
			String value = record.get("Value (dollars)");
			if(value.contains("$")){
				value = value.replaceAll("\\$|(?<=\\d),(?=\\d)", ""); 
				amount = amount.replaceAll("\\$|(?<=\\d),(?=\\d)", "");
				if(Float.parseFloat(value) > Float.parseFloat(amount)){
					System.out.print(record.get("Country")+ " ");
					System.out.println(record.get("Value (dollars)"));
				}
			}
		}
	}
	
	public static void tester(){
		
		FileResource fr = new FileResource("C:\\Users\\cvora\\git\\Duke-OOJava\\src\\module1\\exportdata.csv");
		CSVParser parser = fr.getCSVParser();
		
		System.out.println(countryInfo(parser,"Nauru"));
		parser = fr.getCSVParser();
		listExportersTwoProducts(parser,"cotton","flowers");
		parser = fr.getCSVParser();
		System.out.println(numberOfExporters(parser,"cocoa"));
		parser = fr.getCSVParser();
		bigExporters(parser,"$999,999,999,999");
		parser = fr.getCSVParser();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tester();
	}

}
