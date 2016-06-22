public class Save {

	public static void save (int status) {
		switch (status) {
			case 0:
				saveText();
				break;
			case 1:
				codeToTemp();
				break;
			case 2:
				tempToCode();
				break;
		}
	}

	public static void saveText() {
		// Speichere Text.java;
	}

	public static void codeToTemp() {
		// Kopiere Code.java --> Temp_code.java
	}

	public static void tempToCode() {
		// Kopiere Temp_code.java --> Code.java
	}

}
