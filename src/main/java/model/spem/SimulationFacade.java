package model.spem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "SIMULATION")
@XmlRootElement(name = "simulation")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimulationFacade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String objective = "teste";

    @XmlElementRef(name = "process_alternative")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationFacade")
    private List<ProcessRepository> processAlternatives = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulationFacade") // first index of this list is associated with first index of the processAlternatives list
    private List<Integer> listWithNumberOfSimulationRuns = new ArrayList<>();

    public List<ProcessRepository> getProcessAlternatives() {
	return processAlternatives;
    }

    public void setProcessAlternatives(List<ProcessRepository> processAlternatives) {
	this.processAlternatives = processAlternatives;
    }

    public boolean addProcessAlternative(ProcessRepository processRepository) {
	return processAlternatives.add(processRepository);
    }
    
    public boolean addNumberOfSimulationRuns(int numberOfSimulationRuns) {
    	this.listWithNumberOfSimulationRuns.add(numberOfSimulationRuns);
    	return true;
    }
    
    public Integer getNumberOfSimulationRuns(String processAlternativeName) {
    	int index = 0;
    	for (int i = 0; i < processAlternatives.size(); i++) {
    		if (processAlternativeName.equalsIgnoreCase(processAlternatives.get(i).getName())){
    			index = i;		
    		}
    	}
    	return listWithNumberOfSimulationRuns.get(index);
    }

    public String getObjective() {
	return objective;
    }

    public void setObjective(String objective) {
	this.objective = objective;
    }

}
