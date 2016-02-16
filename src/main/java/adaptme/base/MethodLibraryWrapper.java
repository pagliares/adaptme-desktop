
package adaptme.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.epf.uma.Artifact;
import org.eclipse.epf.uma.CapabilityPattern;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.ContentPackage;
import org.eclipse.epf.uma.CustomCategory;
import org.eclipse.epf.uma.Deliverable;
import org.eclipse.epf.uma.Discipline;
import org.eclipse.epf.uma.DisciplineGrouping;
import org.eclipse.epf.uma.Domain;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.MethodLibrary;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.NamedElement;
import org.eclipse.epf.uma.Outcome;
import org.eclipse.epf.uma.Process;
import org.eclipse.epf.uma.ProcessComponent;
import org.eclipse.epf.uma.ProcessPackage;
import org.eclipse.epf.uma.Role;
import org.eclipse.epf.uma.RoleSet;
import org.eclipse.epf.uma.RoleSetGrouping;
import org.eclipse.epf.uma.Task;
import org.eclipse.epf.uma.Tool;
import org.eclipse.epf.uma.WorkProductType;

import adaptme.base.persist.PersistProcess;
import model.spem.ProcessContentRepository;
import model.spem.ProcessRepository;

/**
 * Classe de manipulação do objeto MethodLibrary. Fornece métodos para
 * manipulação dos elementos contidos na MethodLibrary
 *
 * @author eugf
 */
public class MethodLibraryWrapper {

    private MethodLibrary methodLibrary;
    private MethodLibraryHash methodLibraryHash;
    private File methodLibraryFile;
    private JAXBElement<?> jaxbElement;
    private boolean valid;

    public void load(File file) {
    	try {
    		JAXBContext jaxbContext = JAXBContext.newInstance("org.eclipse.epf.uma");
    		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    		jaxbElement = (JAXBElement<?>) unmarshaller.unmarshal(new FileInputStream(file));
    		methodLibraryFile = file;
    		methodLibrary = (MethodLibrary) jaxbElement.getValue();
    		methodLibraryHash = new MethodLibraryHash();
    		methodLibraryHash.buildElementsMap(methodLibrary);
    		valid = true;
    	} catch (JAXBException | IOException ex) {
    		System.out.println(ex);
    	}
    }

    
    public List<Process> getUMAProcesses() {

    	List<Process> processes = new ArrayList<>();

    	for (MethodPackage methodPackage : getMethodLibrary().getMethodPlugin().get(0).getMethodPackage()) {
    		if (methodPackage instanceof ProcessComponent) {
    			Process process = ((ProcessComponent) methodPackage).getProcess();
    			processes.add(process);
    		}
    	}
    	return processes;	
    }
    
    public boolean isValid() {
    	return valid;
    }

    public void save() {
    	saveAs(methodLibraryFile);
    }

    public void saveAs(File file) {
    	try {
    		checkIfMethodLibraryIsLoaded();
    		JAXB.marshal(jaxbElement, new FileOutputStream(file));
    		replaceNS2toUMA(file);
    	} catch (Exception ex) {
    		System.out.println("Erro:\t" + ex);
    	}
    }

    private void replaceNS2toUMA(File file) {
    	StringBuilder xmlBuffer = new StringBuilder();

    	try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
    		while (bf.ready()) {
    			xmlBuffer.append(bf.readLine().replace("ns2", "uma")).append("\n");
    		}
    		bf.close();
    	} catch (IOException ex) {
    		Logger.getLogger(MethodLibraryWrapper.class.getName()).log(Level.SEVERE, null, ex);
    	}

    	try (FileWriter fw = new FileWriter(file)) {
    		fw.write(xmlBuffer.toString());
    		fw.close();
    	} catch (IOException ex) {
    		Logger.getLogger(MethodLibraryWrapper.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }

    public MethodLibrary getMethodLibrary() {
    	return methodLibrary;
    }

    public MethodLibraryHash getMethodLibraryHash() {
    	return methodLibraryHash;
    }

    public List<MethodPlugin> getAllMethodPlugin() {
    	return methodLibrary.getMethodPlugin();
    }

    public void add(MethodLibrary mLibrary, MethodPlugin mPlugin) {
    	mLibrary.getMethodPlugin().add(mPlugin);
    	addToHash(mPlugin);

    }

    public void del(MethodPlugin mPlugin) {
    	methodLibrary.getMethodPlugin().remove(mPlugin);
    	removeFromHash(mPlugin);
    }

    // Componentes de Method Plugin
    public void add(MethodPlugin mPlugin, ContentCategoryPackage categoryPackage) {
    	// TODO: Gerar um valor hash para o novo elemento (talvez MD5 seja uma
    	// soluçao)
    	// TODO: Adicionar o novo elemento na hash
    	mPlugin.getMethodPackage().add(categoryPackage);

    }

    public void del(ContentCategoryPackage categoryPackage) {
    	for (MethodPlugin methodPlugin : methodLibrary.getMethodPlugin()) {
    		methodPlugin.getMethodPackage().remove(categoryPackage);
    	}
    	removeFromHash(categoryPackage);
    }

    public void add(MethodPlugin mPlugin, ContentPackage contentPackage) {
    	// TODO: Gerar um valor hash para o novo elemento (talvez MD5 seja uma
    	// soluçao)
    	// TODO: Adicionar o novo elemento na hash
    	mPlugin.getMethodPackage().add(contentPackage);
    }

    public void del(ContentPackage contentPackage) {
    	for (MethodPlugin methodPlugin : methodLibrary.getMethodPlugin()) {
    		methodPlugin.getMethodPackage().remove(contentPackage);
    	}
    	removeFromHash(contentPackage);
    }

    public void add(MethodPlugin mPlugin, ProcessComponent processComponent) {
    	// TODO: Gerar um valor hash para o novo elemento (talvez MD5 seja uma
    	// soluçao)
    	// TODO: Adicionar o novo elemento na hash
    	mPlugin.getMethodPackage().add(processComponent);
    }

    public void del(ProcessComponent processComponent) {
    	for (MethodPlugin methodPlugin : methodLibrary.getMethodPlugin()) {
    		methodPlugin.getMethodPackage().remove(processComponent);
    	}
    }

    public void add(MethodPlugin mPlugin, ProcessPackage processPackage) {
    	// TODO: Gerar um valor hash para o novo elemento (talvez MD5 seja uma
    	// soluçao)
    	// TODO: Adicionar o novo elemento na hash
    	mPlugin.getMethodPackage().add(processPackage);
    }

    public void del(ProcessPackage processPackage) {
    	for (MethodPlugin methodPlugin : methodLibrary.getMethodPlugin()) {
    		methodPlugin.getMethodPackage().remove(processPackage);
    	}
    }

    // Componentes de ContentCategoryPackage
    public void add(ContentCategoryPackage categoryPackage, Discipline discipline) {
    	categoryPackage.getContentCategory().add(discipline);
    }

    public void del(Discipline discipline) {
    	for (MethodPlugin methodPlugin : methodLibrary.getMethodPlugin()) {
    		for (MethodPackage methodPackage : methodPlugin.getMethodPackage()) {
    			if (methodPackage instanceof ContentCategoryPackage) {
    				ContentCategoryPackage categoryPackage = (ContentCategoryPackage) methodPackage;
    				categoryPackage.getContentCategory().remove(discipline);
    			}
    		}
    	}

    }

    public void add(ContentCategoryPackage categoryPackage, DisciplineGrouping disciplineGrouping) {
    	categoryPackage.getContentCategory().add(disciplineGrouping);

    }

    public void del(DisciplineGrouping disciplineGrouping) {
    	// categoryPackage.getContentCategory().remove(disciplineGrouping);

    }

    public void add(ContentCategoryPackage categoryPackage, RoleSet roleSet) {
    	categoryPackage.getContentCategory().add(roleSet);

    }

    public void del(RoleSet roleSet) {
    	// categoryPackage.getContentCategory().remove(roleSet);

    }

    public void add(ContentCategoryPackage categoryPackage, RoleSetGrouping roleSetGrouping) {
    	categoryPackage.getContentCategory().add(roleSetGrouping);

    }

    public void del(RoleSetGrouping roleSetGrouping) {
    	// categoryPackage.getContentCategory().remove(roleSetGrouping);

    }

    public void add(ContentCategoryPackage categoryPackage, Domain domain) {
    	categoryPackage.getContentCategory().add(domain);

    }

    public void del(Domain domain) {
    	// categoryPackage.getContentCategory().remove(domain);

    }

    public void add(ContentCategoryPackage categoryPackage, WorkProductType workProductType) {
    	categoryPackage.getContentCategory().add(workProductType);

    }

    public void del(WorkProductType workProductType) {
    	// categoryPackage.getContentCategory().remove(workProductType);

    }

    public void add(ContentCategoryPackage categoryPackage, Tool tool) {
    	categoryPackage.getContentCategory().add(tool);

    }

    public void del(Tool tool) {
    	// categoryPackage.getContentCategory().remove(tool);

    }

    public void add(ContentCategoryPackage categoryPackage, CustomCategory customCategory) {
    	categoryPackage.getContentCategory().add(customCategory);

    }

    public void del(CustomCategory customCategory) {
    	// categoryPackage.getContentCategory().remove(customCategory);

    }

    // Componentes de ContentPackage
    public void add(ContentPackage contentPackage, Role role) {
    	for (Object obj : contentPackage.getReusedPackageOrMethodPackage()) {
    		NamedElement element = (NamedElement) obj;
    		if (element.getName().equals(role.getName())) {
    			return;
    		}
    	}
    	contentPackage.getContentElement().add(role);

    }

    public void replace(ContentPackage contentPackage, Role role) {
    	for (Object obj : contentPackage.getReusedPackageOrMethodPackage()) {
    		NamedElement element = (NamedElement) obj;
    		if (element.getName().equals(role.getName())) {
    			contentPackage.getContentElement().remove(element);
    			contentPackage.getContentElement().add(role);

    		}
    	}
    }

    public void del(Role role) {
    	// contentPackage.getContentElement().remove(role);

    }

    public void add(ContentPackage contentPackage, Task task) {
    	contentPackage.getContentElement().add(task);

    }

    public void del(Task task) {
    	// contentPackage.getContentElement().remove(task);

    }

    public void add(ContentPackage contentPackage, Artifact artifact) {
    	contentPackage.getContentElement().add(artifact);

    }

    public void del(Artifact artifact) {
    	// contentPackage.getContentElement().remove(artifact);

    }

    public void add(ContentPackage contentPackage, Deliverable deliverable) {
    	contentPackage.getContentElement().add(deliverable);

    }

    public void del(Deliverable deliverable) {
    	// contentPackage.getContentElement().remove(deliverable);

    }

    public void add(ContentPackage contentPackage, Outcome outcome) {
    	contentPackage.getContentElement().add(outcome);

    }

    public void del(Outcome outcome) {
    	// contentPackage.getContentElement().remove(outcome);

    }

    // retorna todos os processos que podem ser usados para 'extends/replace' no
    // delivery process
    public List<CapabilityPattern> getAllCapabilityPatterns() {
    	List<CapabilityPattern> capabilityPatterns = new ArrayList<>();
    	for (MethodPlugin methodPlugin : methodLibrary.getMethodPlugin()) {
    		for (MethodPackage methodPackage : methodPlugin.getMethodPackage()) {
    			if (methodPackage instanceof ProcessPackage) {
    				ProcessPackage processPackage = (ProcessPackage) methodPackage;
    				for (Object obj : processPackage.getReusedPackageOrMethodPackage()) {
    					if (obj instanceof ProcessComponent) {
    						ProcessComponent processComponent = (ProcessComponent) obj;
    						if (processComponent.getProcess() instanceof CapabilityPattern) {
    							capabilityPatterns.add((CapabilityPattern) processComponent.getProcess());
    						}
    					}
    				}
    			}
    		}
    	}
    	return capabilityPatterns;
    }

    private void checkIfMethodLibraryIsLoaded() {
    	if (methodLibrary == null || methodLibraryFile == null) {
    		throw new RuntimeException("Method Library nao foi carregada.");
    	}
    }

    private void addToHash(Element element) {
    	if (element instanceof MethodElement) {
    		MethodElement methodElement = (MethodElement) element;
    		methodLibraryHash.getHashMap().put(methodElement.getId(), element);
    	}
    }

    private void removeFromHash(Element element) {
    	if (element instanceof MethodElement) {
    		MethodElement methodElement = (MethodElement) element;
    		methodLibraryHash.getHashMap().remove(methodElement.getId());
    	}
    }
    
    public static void main(String[] args) {
    	PersistProcess persistProcess = new PersistProcess();
 		File methodLibraryFile = new File("/Users/pagliares/Dropbox/projetosEPFComposer/Exported_EPF_XML_Processes/Software_Process_Alternatives/Software_Process_Alternatives.xml");
 		MethodLibraryWrapper methodLibraryWrapper = new MethodLibraryWrapper();
 		methodLibraryWrapper.load(methodLibraryFile);
 		List<Process> processes = methodLibraryWrapper.getUMAProcesses();
 		System.out.println(processes.size());
 		
 		List<ProcessRepository> processesRepository = new ArrayList<>();
  		
  		for (Process p: processes) {
  			processesRepository.add(persistProcess.buildProcess(p, methodLibraryWrapper.methodLibraryHash));
  		}
  		
  		for (ProcessRepository pr: processesRepository) {
  			System.out.println(pr.getName());
  			System.out.println(pr.getSimulationObjective());
  			List<ProcessContentRepository> processContents = pr.getProcessContents();
  			for (ProcessContentRepository pcr : processContents) {
//  				System.out.println(pcr.getName());
//  				System.out.println(pcr.getType().toString());
  				List<ProcessContentRepository> children = pcr.getChildren();
  				for (ProcessContentRepository pcrc : children) {
  					System.out.println(pcrc.getName());
  	  				System.out.println(pcrc.getType().toString());
  	  			List<ProcessContentRepository> children2 = pcrc.getChildren();
  	  			for (ProcessContentRepository pcrc2 : children2) {
  					System.out.println(pcrc2.getName());
  	  				System.out.println(pcrc2.getType().toString());
  				}
  				}
  			}
  		}
 		

    }
}
