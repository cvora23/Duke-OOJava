package module1;

import edu.duke.*;

public class HelloWorld {
	
	public void runHello () {
		FileResource res = new FileResource("C:\\Users\\cvora\\git\\Duke-OOJava\\src\\module1\\hello_unicode.txt");
		for (String line : res.lines()) {
			System.out.println(line);
		}
	}
	
	public static void main(String[] args) {
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.runHello();
    }
	
}
