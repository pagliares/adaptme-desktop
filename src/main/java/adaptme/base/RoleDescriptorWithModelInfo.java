package adaptme.base;

import org.eclipse.epf.uma.RoleDescriptor;

public class RoleDescriptorWithModelInfo extends RoleDescriptor {
	private String modelInfo;

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo;
    }

}
