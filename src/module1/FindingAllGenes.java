package module1;

import edu.duke.FileResource;
import edu.duke.StorageResource;

public class FindingAllGenes {

	public int findStopIndex(String dna,int index){
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
	
	public void findStoreAllGenesFromDna(String dna){
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
	
	void printAllGenes(){
		for(String gene: mStorageResource.data()){
			System.out.println("Gene: "+ gene);
		}
	}
	
	public static void test1(){
		FindingAllGenes findingAllGenes = new FindingAllGenes();
		findingAllGenes.findStoreAllGenesFromDna("ATGAAATGAAAA");
		findingAllGenes.printAllGenes();
	}
	
	public static void test2(){
		FindingAllGenes findingAllGenes = new FindingAllGenes();
		findingAllGenes.findStoreAllGenesFromDna("ccatgccctaataaatgtctgtaatgtaga");
		findingAllGenes.printAllGenes();
	}
	
	public static void test3(){
		FindingAllGenes findingAllGenes = new FindingAllGenes();
		findingAllGenes.findStoreAllGenesFromDna("CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA");
		findingAllGenes.printAllGenes();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
		test2();
		test3();
	}

	private StorageResource mStorageResource = new StorageResource();
	
}
