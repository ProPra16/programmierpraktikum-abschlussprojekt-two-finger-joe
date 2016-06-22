import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
	
	FileWriter writer;
	File file;


//Irgendwie muss der Stream übergeben werden. Also die Eingabe der User.

	public static void save (int status, String Eingabe) {
		switch (status) {
			case 0:
				saveText(Eingabe);
				break;
			case 1:
				codeToTemp(Eingabe);
				break;
			case 2:
				tempToCode(Eingabe);
				break;
		}
	}

	public static void saveText(String Eingabe) {
	//	Datei = Datei();
	//	FileWriter saveText = new FileWriter();
	//	saveText.Stream(Stream,"projekt/code.java");
	}

	public static void codeToTemp(String Eingabe) {
		// Kopiere Code.java --> Temp_code.java
	}

	public static void tempToCode(String Eingabe) {
		// Kopiere Temp_code.java --> Code.java
	}

	/*
	public static void main(String[] args){	
		int status = 0;
		String Stream = "dasds";
		save(status, Stream);
	}
	


	public class FileSchreiben {
	FileWriter writer;
	File file;
	public void Stream(String a, String b){
		 file = new File(b);
		 try {
			 writer = new FileWriter(file ,true);
			 writer.write(a + " ");
			 writer.flush();
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	} 
	

	public class OSDetector
	{
	    public static boolean isWindows = false;
	    public static boolean isLinux = false;
	    public static boolean isMac = false;
	    
	    static
	    {
	        String os = System.getProperty("os.name").toLowerCase();
	        isWindows = os.contains("win");
	        isLinux = os.contains("nux") || os.contains("nix");
	        isMac = os.contains("mac");
	    }
	    public static boolean isWindows() { return isWindows; }
	    public static boolean isLinux() { return isLinux; }
	    public static boolean isMac() { return isMac; };
	} */
}
