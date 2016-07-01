import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;
import vk.core.internal.InternalResult

public class Compile {
	

	boolean CompileErrors(String className, String classContent){
		
	
	CompilationUnit unit = new CompilationUnit(className, classContent, false);
	JavaStringCompiler compiler = CompilerFactory.getCompiler(unit);
	
	
	return compiler.hasCompileErrors();
	
	}

}
