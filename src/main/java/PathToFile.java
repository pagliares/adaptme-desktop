

import java.io.File;

public class PathToFile {
	
	public static File getPathToInputFile(String fileName, boolean javaEE){
		if (javaEE) {
			return new File("/Users/pagliares/Dropbox/projetosEclipse/workspaceHirata/AdaptmeRepositoryWeb/input/process_alternatives_epf/"+fileName+".xml");
		}
		return new File("input/process_alternatives_epf/"+fileName+".xml");
	}
	
	public static File getPathToOutputFile(String fileName, boolean javaEE){
		if (javaEE) {
			return new File("/Users/pagliares/Dropbox/projetosEclipse/workspaceHirata/AdaptmeRepositoryWeb/output/spem_uma_simplified/"+fileName+".xml");
		}
		return new File("output/"+fileName+".xml");
	}
}


//String contextPath = context.getContextPath();
//System.out.println("Context path..:  " + contextPath);
//
//String realPath = context.getRealPath("/");
//System.out.println("Real path..:  " + realPath);
