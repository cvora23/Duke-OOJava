package module1;

import edu.duke.FileResource;
import edu.duke.StorageResource;

public class StoringAllGenes {
	
	public static int findStopIndex(String dna,int index){
		int stop1 = dna.indexOf("tag",index);
		if(stop1 == -1 || (stop1-index) %3 != 0){
			stop1 = dna.length();
		}
		int stop2 = dna.indexOf("tga",index);
		if(stop2 == -1 || (stop2-index) %3 != 0){
			stop2 = dna.length();
		}
		int stop3 = dna.indexOf("taa",index);
		if(stop3 == -1 || (stop3-index) %3 != 0){
			stop3 = dna.length();
		}
		
		return Math.min(stop1, Math.min(stop2, stop3));
	}
	
	public static void findStoreAllGenesFromDna(String dna){
		System.out.println("Original DNA = " + dna);
		String originalDna = dna;
		dna = dna.toLowerCase();
		int start = 0;
		while(true){
			int tag = dna.indexOf("atg",start);
			if(tag == -1){
				break;
			}
			int end = findStopIndex(dna,tag+3);
			if(end != dna.length()){
				mStorageResource.add(originalDna.substring(tag,end+3));
				start = end+3;
			}else{
				start = start + 3;
			}
		}
	}
		
	public static double cgRatio(String dna){
		
		dna = dna.toLowerCase();
		
		int cCount,gCount;
		cCount = dna.length() - dna.replaceAll("c", "").length();
		gCount = dna.length() - dna.replaceAll("g", "").length();

		return ((double)(cCount+gCount)/(double)(dna.length()));
	
	}
	
	public static void printAllGenes(){
		
		System.out.println("Total Number of Genes = "+mStorageResource.size());

		
		System.out.println("------------------printing all genes whose lengths are more than 60 characters----------------");
		int noGenesLenghtLongerThan60Nucleotides = 0;
		for(String gene: mStorageResource.data()){
			if(gene.length()>60){
				//System.out.println(gene);
				noGenesLenghtLongerThan60Nucleotides++;
			}
		}
		System.out.println("Total number of genes onger than 60 nucleotides =  "+noGenesLenghtLongerThan60Nucleotides);

		
		System.out.println();
		System.out.println("----------------printing all genes whose C-G-ratio is higher than 0.35--------------");

		int noGenesCGRatioGreaterThan35On100 = 0;

		for(String gene: mStorageResource.data()){
			if(cgRatio(gene) > 0.35){
				noGenesCGRatioGreaterThan35On100++;
				System.out.println(gene);
			}
		}
		System.out.println("Total genes whose C-G-ratio is higher than 0.35 = "+noGenesCGRatioGreaterThan35On100);

	}
	
	public static void testStorageFinder(){
		
		FileResource dnaFile = new FileResource("C:\\Users\\cvora\\git\\Duke-OOJava\\src\\module1\\GRch38dnapart.fa");
		findStoreAllGenesFromDna(dnaFile.asString());
		printAllGenes();
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testStorageFinder();
	}
	
	private static StorageResource mStorageResource = new StorageResource();

}
