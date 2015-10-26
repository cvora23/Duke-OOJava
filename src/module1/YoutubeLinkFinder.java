package module1;

import edu.duke.URLResource;

public class YoutubeLinkFinder {

		private static String UrlLoc = "http://www.dukelearntoprogram.com/course2/data/manylinks.html";
		
		public void printYoutubeLinks(){
			URLResource res = new URLResource(UrlLoc);
			for (String line : res.lines()) {
				//System.out.println(line);
				printIfYoutubeLinks(line);
			}
		}
		
		public void printIfYoutubeLinks(String ipString){
			
			// Given a line of string.
			// 1: "Find youtube.com pattern from line"
			// 2: If found go find " before youtube.com and " after youtube.com.
			// 3: Between these starTag and endTag is the youtube link. (Take care about case sensitivity)
			
			String ipStringPostProc = 	ipString.toLowerCase();		
			int youtubeIndex = ipStringPostProc.indexOf("youtube.com");
			if(youtubeIndex == -1){
				return;
			}else{
				int startIndex = 0,endIndex = 0;
				startIndex = ipStringPostProc.lastIndexOf('"', youtubeIndex);
				endIndex = ipStringPostProc.indexOf('"', youtubeIndex);
				System.out.println(ipString.substring(startIndex+1,endIndex));
			}
			
		}
		
		public static void main(String[] args) {
			YoutubeLinkFinder youtubeLinkFinder = new YoutubeLinkFinder();
			youtubeLinkFinder.printYoutubeLinks();
	    }
		
		
	
}
