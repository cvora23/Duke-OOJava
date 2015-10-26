/**
 * Finds a protein within a strand of DNA represented as a string of c,g,t,a letters.
 * A protein is a part of the DNA strand marked by start and stop codons (DNA triples)
 * that is a multiple of 3 letters long.
 *
 * @author Duke Software Team 
 */

package module1;

import edu.duke.*;

import java.io.*;

public class FindingOneGene {
	
	public String findProtein(String dna) {
		String lowerCasedna = dna.toLowerCase();
		int start = lowerCasedna.indexOf("atg");
		if (start == -1) {
			return "";
		}
		
		int tagStopCodon = lowerCasedna.indexOf("tag", start+3);
		int tgaStopCodon = lowerCasedna.indexOf("tga", start+3);
		int taaStopCodon = lowerCasedna.indexOf("taa", start+3);

		//"CASE FOR TAG STOP CODON"
		if ((tagStopCodon - start) % 3 == 0) {
			return lowerCasedna.substring(start, tagStopCodon+3);
		}
		
		//"CASE FOR TGA STOP CODON"
		else if((tgaStopCodon-start) % 3 == 0){
			return lowerCasedna.substring(start, tgaStopCodon+3);
		}
		
		//"CASE FOR TAA STOP CODON"
		else if((taaStopCodon-start) % 3 == 0){
			return lowerCasedna.substring(start, taaStopCodon+3);
		}
		else {
			return "";
		}
		
	}
	
	public void testing() {
		
		String test1 = "AATGCTAGTTTAAATCTGA";
		String test2 = "ataaactatgttttaaatgt";
		String test3 = "acatgataacctaag";

		
		String result = findProtein(test1);
		System.out.println(result);
		result = findProtein(test2);
		System.out.println(result);
		result = findProtein(test3);
		System.out.println(result);
	}
	
	
	public static void main(String[] args) {
		FindingOneGene tagFinder = new FindingOneGene();
		tagFinder.testing();
    }
	
}
