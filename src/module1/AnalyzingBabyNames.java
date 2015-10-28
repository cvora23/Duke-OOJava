package module1;

import java.io.File;
import java.util.*;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

public class AnalyzingBabyNames {

	public static void totalBirths(FileResource fr){
	
		List<String>boysNames = new ArrayList<String>();
		List<String>girlsNames = new ArrayList<String>();

		for(CSVRecord rec: fr.getCSVParser(false)){
			
			if(rec.get(1).equalsIgnoreCase("M")){
				if(!boysNames.contains(rec.get(0))){
					boysNames.add(rec.get(0));
				}
			}else if(rec.get(1).equalsIgnoreCase("F")){
				if(!girlsNames.contains(rec.get(0))){
					girlsNames.add(rec.get(0));
				}
			}
		}
		
		//print unique boys names
		for(String names: boysNames){
			System.out.println(names);
		}
		//print unique girls names
		for(String names: girlsNames){
			System.out.println(names);
		}
		
		System.out.println("Number of unique boys are: "+ boysNames.size());
		System.out.println("Number of unique girls are: "+ girlsNames.size());
		// print total names in file
		System.out.println("Total names in file are: "+(boysNames.size()+girlsNames.size()));
	}
	
	public static String createFileName(int year){
		
//		String fName = "C:\\Users\\cvora\\Downloads\\us_babynames\\us_babynames_by_year\\yob"+year+".csv";
		String fName = "C:\\Users\\cvora\\Downloads\\us_babynames\\us_babynames_by_year\\yob"+year+".csv";
		return fName;
	}
	
	public static int getRank(int year,String name,String gender){
		int rank = -1;
		FileResource fr = new FileResource(createFileName(year));
		CSVParser parser = fr.getCSVParser(false);
		int mRank = 0;
		int fRank = 0;
		
		for(CSVRecord rec : parser){
			
			if(gender.equalsIgnoreCase("M") && rec.get(1).equalsIgnoreCase("M")){
				mRank++;
				if(rec.get(0).equalsIgnoreCase(name)){
					rank = mRank;
					break;
				}
			}else if(gender.equalsIgnoreCase("F")&& rec.get(1).equalsIgnoreCase("F")){
				fRank++;
				if(rec.get(0).equalsIgnoreCase(name)){
					rank = fRank;
					break;
				}
			}
		}
		return rank;
	}
	
	public static String getName(int year,int rank,String gender){
		
		String name = "NO NAME";
		FileResource fr = new FileResource(createFileName(year));
		CSVParser parser = fr.getCSVParser(false);
		int mRank = 0;
		int fRank = 0;
		
		for(CSVRecord rec : parser){
			
			if(gender.equalsIgnoreCase("M") && rec.get(1).equalsIgnoreCase("M")){
				mRank++;
				if(mRank == rank){
					name = rec.get(0);
					break;
				}
			}else if(gender.equalsIgnoreCase("F")&& rec.get(1).equalsIgnoreCase("F")){
				fRank++;
				if(fRank == rank){
					name = rec.get(0);
					break;
				}
			}
		}
		return name;
	}
	
	public static void whatIsNameInYear(String name,int year,int newYear,String gender){
		int rank = -1;
		rank = getRank(year,name,gender);
		if(rank != -1){
			String newName = getName(newYear,rank,gender);
			if(!newName.equalsIgnoreCase("NO NAME")){
				System.out.println(name+" born in "+year+" would be "+newName+" if she was born in "+newYear);
			}else{
				System.out.println("Error in Data Input");
			}
		}else{
			System.out.println("Error in Data Input");
		}
	}
	
	public static int yearOfHighestRank(String name,String gender){
		int yearOfHighestRank = -1;
		int highestRank = 0;
		DirectoryResource dir = new DirectoryResource();
		for(File g : dir.selectedFiles()){
			String fileName = g.getName();
			fileName = fileName.replaceAll("[^0-9]", "");
			int year = Integer.parseInt(fileName);
			int ret = getRank(year,name,gender);
			if((ret != -1) && (yearOfHighestRank == -1)){
				highestRank = ret;
				yearOfHighestRank = year;
			}else if((ret != -1) && (ret <highestRank)){
				highestRank = ret;
				yearOfHighestRank = year;
			}
		}
		return yearOfHighestRank;
	}
	
	public static double getAverageRank(String name,String gender){
		double averageRank = -1.0;
		int count = 0;
		int totalRank = 0;
		DirectoryResource dir = new DirectoryResource();
		for(File g : dir.selectedFiles()){
			String fileName = g.getName();
			fileName = fileName.replaceAll("[^0-9]", "");
			int year = Integer.parseInt(fileName);
			int ret = getRank(year,name,gender);
			if(ret != -1){
				count++;
				totalRank += ret;
			}
		}
		if(count != 0 && totalRank != 0){
			averageRank = (double)totalRank/(double)count;
		}
		return averageRank;
	}
	
	static int getTotalBirthsRankedHigher(int year,String name,String gender){
		
		int totalBirthsRankedHigher = -1;
		int internalTotalBirthsRankedHigher = 0;
		FileResource fr = new FileResource(createFileName(year));
		CSVParser parser = fr.getCSVParser(false);

		int rank = getRank(year,name,gender);
		if(rank != -1){
			int count = 0;
			for(CSVRecord rec : parser){
					if(rec.get(1).equalsIgnoreCase(gender)){
						count++;
						if(count == rank){
							break;
						}
						internalTotalBirthsRankedHigher += Integer.parseInt(rec.get(2));
					}
				}
			}
		if(internalTotalBirthsRankedHigher != 0){
			totalBirthsRankedHigher = internalTotalBirthsRankedHigher;
		}
		return totalBirthsRankedHigher;
	}
	
	public static void tester(){
		
		//FileResource fr = new FileResource(createFileName(1905));
		//totalBirths(fr);
		
		System.out.println(getRank(1960,"Emily", "F"));
		System.out.println(getRank(1971,"Frank", "M"));

		//System.out.println(getRank(2012,"Mason", "F"));
		System.out.println(getName(1980,350,"F"));
		System.out.println(getName(1982,450,"M"));

		whatIsNameInYear("Susan",1972,2014,"F");
		whatIsNameInYear("Owen",1974,2014,"M");

		//System.out.println(yearOfHighestRank("Genevieve","F"));
		//System.out.println(yearOfHighestRank("Mich","M"));

		System.out.println(getAverageRank("Susan","F"));
		System.out.println(getAverageRank("Robert","M"));
		System.out.println(getTotalBirthsRankedHigher(1990,"Emily","F"));
		System.out.println(getTotalBirthsRankedHigher(1990,"Drew","M"));

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		tester();
	}

}
