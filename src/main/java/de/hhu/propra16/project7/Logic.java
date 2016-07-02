/* Erste Skizze der Logik Architektur*/

package de.hhu.propra16.project7;

//import vk.core.api.CompilationUnit;
//import vk.core.api.CompilerFactory;
//import vk.core.api.JavaStringCompiler;
//import vk.core.internal.InternalResult



// übersichtlichkeit halber: https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-two-finger-joe/blob/Logik/test





public class Logic {
	
	public static void main(String[] args){
	
		test();
	}
	


	
	public Status Input(int Nutzer, 
				boolean TestFehlschlag, Status status){
				boolean CompilerWorks = true;
				
				
			//	boolean CompilerWorks = CompileErrors();
				
				
		
		if(status==Status.Red) return Red( Nutzer,  CompilerWorks,  TestFehlschlag);
		if(status==Status.Green) return Green( Nutzer,  CompilerWorks,  TestFehlschlag);
		if(status==Status.Refactoring) return Refactoring( Nutzer,  CompilerWorks,  TestFehlschlag);
		
		
		return Status.Red;
	}
	

	
	public Status Red(int Nutzer, 
			boolean CompilerWorks, boolean TestFehlschlag){
		
			if(Nutzer==1 && (CompilerWorks==false||TestFehlschlag==true)){	
				return Status.Green;
				}

		return Status.Red;
				
	} 
	
	

	
	public Status Green(int Nutzer, 
			boolean CompilerWorks, boolean TestFehlschlag){

			if(Nutzer==0)return Status.Red;
			if(Nutzer==1)return Status.Green;
			if(Nutzer==2 && CompilerWorks==true && TestFehlschlag == false)return Status.Refactoring;
			
			
			return Status.Green;		
	}
	

	
	
	public Status Refactoring(int Nutzer, 
			boolean CompilerWorks, boolean TestFehlschlag){
			if(Nutzer==0 && CompilerWorks==true && TestFehlschlag == false)return Status.Red;
			return Status.Refactoring;		
	}
	
	
/*	public boolean CompileErrors(String className, String classContent){
			CompilationUnit unit = new CompilationUnit(className, classContent, false);
			JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
		return  compiler.hasCompileErrors();
	
	} */
	
	public enum Status
	{
		Red, Green, Refactoring
	}
	
	public static void test(){
		
		 Status status = Status.Red;
		 
			Logic log = new Logic();
			Status a = log.Input(1,true,status);
			System.out.println(a);
			
			
			status = Status.Green;
			 a = log.Input(2,false,status);
			System.out.println(a);
			
			status = Status.Refactoring;
			 a = log.Input(0,false,status);
			System.out.println(a);
		
		
	}
	
	
	
	
		
}
