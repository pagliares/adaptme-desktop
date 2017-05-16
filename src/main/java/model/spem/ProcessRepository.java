package model.spem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.modelmapper.ModelMapper;

import model.spem.util.ProcessContentType;

@Entity
@Table(name = "PROCESS")
@XmlRootElement(name = "process")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessRepository implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlAttribute
	private Integer id;

	@XmlAttribute
	private String name = "Teste process name";

	@XmlAttribute
	private String simulationObjective;

	@XmlAttribute
	private boolean chosen;

	@XmlElementRef(name = "process_content")
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "processRepository")
	private List<ProcessContentRepository> processContents;

	@ManyToOne
	@XmlTransient
	private SimulationFacade simulationFacade;

	@XmlTransient
	@Transient
	private List<ProcessContentRepository> listProcessContentRepositoryWithTasksOnly = new ArrayList<>();
	
	@XmlTransient
	@Transient
	private LinkedHashSet<ProcessContentRepository> listProcessContentRepository = new LinkedHashSet<>();

	@XmlTransient
	@Transient
	private List<ProcessContentRepository> tasks = new ArrayList<>();

	public ProcessRepository() {
		processContents = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public SimulationFacade getSimulationFacade() {
		return simulationFacade;
	}

	public void setSimulationFacade(SimulationFacade simulationFacade) {
		this.simulationFacade = simulationFacade;
	}

	public boolean addProcessElement(ProcessContentRepository processContentsRepository) {
		return processContents.add(processContentsRepository);
	}

	public List<ProcessContentRepository> getProcessContents() {
		return processContents;
	}

	public void setProcessContents(List<ProcessContentRepository> processContents) {
		this.processContents = processContents;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getSimulationObjective() {
		return simulationObjective;
	}

	public void setSimulationObjective(String simulationObjective) {
		this.simulationObjective = simulationObjective;
	}

	// With this return, i can get list of roles, additional roles, input/output
	// workproducts
	public List<ProcessContentRepository> getListProcessContentRepositoryWithTasksOnly(
			List<ProcessContentRepository> listOfProcessContentRepository) {
		if (listOfProcessContentRepository == null) {
			return null;
		} else {
			for (ProcessContentRepository pcr : listOfProcessContentRepository) {
				if (pcr.getType().equals(ProcessContentType.TASK)) {
					listProcessContentRepositoryWithTasksOnly.add(pcr);
				} else
					getListProcessContentRepositoryWithTasksOnly(pcr.getChildren());
			}
			return listProcessContentRepositoryWithTasksOnly;
		}
	}

	public List<ProcessContentRepository> getTasks(List<ProcessContentRepository> listOfProcessContentRepository) {
		if (listOfProcessContentRepository == null) {
			return null;
		} else {
			for (ProcessContentRepository pcr : listOfProcessContentRepository) {
				if (pcr.getType().equals(ProcessContentType.TASK)) {
					listProcessContentRepositoryWithTasksOnly.add(pcr);
				} else
					getListProcessContentRepositoryWithTasksOnly(pcr.getChildren());
			}
			return listProcessContentRepositoryWithTasksOnly;
		}
	}

	// Este metodo removeu um erro muito dificil que era a geracao de varias
	// tarefas no xacdml duplicada
	public void clearListOfTasks() {
		listProcessContentRepositoryWithTasksOnly.clear();
	}
	
	public void clearListOfTasksAndContainers() {
		processContents.clear();
	}

	public List<ProcessContentRepository> getListProcessContentRepositoryWithParticularProcessContentOnly(
			List<ProcessContentRepository> listOfProcessContentRepository, String processContentName) {
		if (listOfProcessContentRepository == null) {
			return null;
		} else {
			for (ProcessContentRepository pcr : listOfProcessContentRepository) {
				if (pcr.getName().equalsIgnoreCase(processContentName)) {
					listProcessContentRepositoryWithTasksOnly.add(pcr);
				} else
					getListProcessContentRepositoryWithTasksOnly(pcr.getChildren());
			}
			return listProcessContentRepositoryWithTasksOnly;
		}
	}

	public void imprimeTasks(List<ProcessContentRepository> listOfProcessContentRepository) {

		if (listOfProcessContentRepository == null) {
			return;
		} else {
			for (ProcessContentRepository pcr : listOfProcessContentRepository) {
				if (pcr.getType().equals(ProcessContentType.TASK)) {
					System.out.println(pcr.getName());
				} else
					imprimeTasks(pcr.getChildren());
			}

		}
	}

	public void setListProcessContentRepositoryWithTasksOnly(
			List<ProcessContentRepository> listProcessContentRepositoryWithTasksOnly) {
		this.listProcessContentRepositoryWithTasksOnly = listProcessContentRepositoryWithTasksOnly;
	}

	public List<ProcessContentRepository> getTasks() {
		return getTasks(processContents);
	}

	public void setTasks(List<ProcessContentRepository> tasks) {
		this.tasks = tasks;
	}

	// Set<ProcessContentRepository> tem todos os elementos, incluindo BEGIN e END
	// ModelMapper permite clonar os objetos (preciso gerar 2 dummies para um spem hierarchical element
	public Set<ProcessContentRepository> getListProcessContentRepository(List<ProcessContentRepository> listOfProcessContentRepository) {
		if (listOfProcessContentRepository == null) {
			return null;
		} else {
			ModelMapper modelMapper = new ModelMapper();
			for (ProcessContentRepository pcr : listOfProcessContentRepository) {
				if (pcr.getType().equals(ProcessContentType.TASK) || pcr.getType().equals(ProcessContentType.MILESTONE)) {
					listProcessContentRepository.add(pcr);
				} else{
 					ProcessContentRepository begin = new ProcessContentRepository();
					modelMapper.map(pcr, begin);
					begin.setName("BEGIN_" + begin.getName());
					listProcessContentRepository.add(begin);
					getListProcessContentRepository(pcr.getChildren());
					ProcessContentRepository end = new ProcessContentRepository();
					modelMapper.map(pcr, end);
					end.setName("END_" + end.getName());
					listProcessContentRepository.add(end);
				}					
			}
			return listProcessContentRepository;
		}
	}
	
}
