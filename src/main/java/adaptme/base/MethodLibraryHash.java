
package adaptme.base;

import java.util.HashMap;

import org.eclipse.epf.uma.Activity;
import org.eclipse.epf.uma.Constraint;
import org.eclipse.epf.uma.ContentCategory;
import org.eclipse.epf.uma.ContentCategoryPackage;
import org.eclipse.epf.uma.ContentElement;
import org.eclipse.epf.uma.ContentPackage;
import org.eclipse.epf.uma.Element;
import org.eclipse.epf.uma.MethodElement;
import org.eclipse.epf.uma.MethodLibrary;
import org.eclipse.epf.uma.MethodPackage;
import org.eclipse.epf.uma.MethodPlugin;
import org.eclipse.epf.uma.PackageableElement;
import org.eclipse.epf.uma.ProcessComponent;
import org.eclipse.epf.uma.ProcessElement;
import org.eclipse.epf.uma.ProcessPackage;

/**
 *
 * @author eugf
 * @author joelmir
 */
public class MethodLibraryHash {

    private HashMap<String, Element> hashMap;

    public MethodLibraryHash() {
	hashMap = new HashMap<>();
    }

    public HashMap<String, Element> getHashMap() {
	return hashMap;
    }

    public void buildElementsMap(MethodLibrary methodLibrary) {

	for (MethodPlugin methodPlugin : methodLibrary.getMethodPlugin()) {
	    hashMap.put(methodPlugin.getId(), methodPlugin);

	    for (MethodPackage methodPackage : methodPlugin.getMethodPackage()) {
		hashMap.put(methodPackage.getId(), methodPackage);

		if (methodPackage instanceof ContentCategoryPackage) {
		    ContentCategoryPackage categoryPackage = (ContentCategoryPackage) methodPackage;
		    mapContentCategoryPackage(categoryPackage);

		} else if (methodPackage instanceof ContentPackage) {
		    ContentPackage contentPackage = (ContentPackage) methodPackage;
		    mapContentPackage(contentPackage);

		} else if (methodPackage instanceof ProcessComponent) {
		    ProcessComponent processComponent = (ProcessComponent) methodPackage;
		    mapProcessComponent(processComponent);

		} else if (methodPackage instanceof ProcessPackage) {
		    ProcessPackage processPackage = (ProcessPackage) methodPackage;
		    mapProcessPackage(processPackage);
		}

	    }

	}

    }

    private void mapContentPackage(ContentPackage contentPackage) {
	for (ContentElement contentElement : contentPackage.getContentElement()) {
	    hashMap.put(contentElement.getId(), contentElement);
	}

	for (Object obj : contentPackage.getReusedPackageOrMethodPackage()) {
	    if (obj instanceof ContentPackage) {
		ContentPackage contentPackageChild = (ContentPackage) obj;
		hashMap.put(contentPackageChild.getId(), contentPackageChild);
		mapContentPackage(contentPackageChild);
	    }
	}
    }

    private void mapProcessPackage(ProcessPackage processPackage) {
	for (ProcessElement processElement : processPackage.getProcessElement()) {
	    hashMap.put(processElement.getId(), processElement);
	}

	for (Object obj : processPackage.getReusedPackageOrMethodPackage()) {
	    if (obj instanceof ProcessComponent) {
		ProcessComponent processComponent = (ProcessComponent) obj;
		hashMap.put(processComponent.getId(), processComponent);
		mapProcessComponent(processComponent);
	    } else if (obj instanceof ProcessPackage) {
		ProcessPackage processPackageChild = (ProcessPackage) obj;
		hashMap.put(processPackageChild.getId(), processPackageChild);
		mapProcessPackage(processPackageChild);
	    }
	    if (obj instanceof MethodElement) {
		MethodElement methodElement = (MethodElement) obj;
		hashMap.put(methodElement.getId(), methodElement);
	    }
	}
    }

    private void mapProcessComponent(ProcessComponent processComponent) {
	org.eclipse.epf.uma.Process process = processComponent.getProcess();
	hashMap.put(process.getId(), process);

	for (Object obj : process.getBreakdownElementOrRoadmap()) {
	    MethodElement methodElement = (MethodElement) obj;
	    hashMap.put(methodElement.getId(), methodElement);
	    if (methodElement instanceof Activity) {
		mapActivity((Activity) methodElement);
	    }
	}

	for (PackageableElement packageableElement : process.getOwnedRuleOrMethodElementProperty()) {
	    if (packageableElement instanceof Constraint) {
		Constraint constraint = (Constraint) packageableElement;
		hashMap.put(constraint.getId(), constraint);
	    }
	}

	for (Object obj : processComponent.getReusedPackageOrMethodPackage()) {
	    if (obj instanceof MethodElement) {
		MethodElement methodElement = (MethodElement) obj;
		hashMap.put(methodElement.getId(), methodElement);
	    }
	}

    }

    private void mapActivity(Activity activity) {
	for (Object obj : activity.getBreakdownElementOrRoadmap()) {
	    MethodElement methodElement = (MethodElement) obj;
	    hashMap.put(methodElement.getId(), methodElement);
	    if (methodElement instanceof Activity) {
		mapActivity((Activity) methodElement);
	    }
	}
    }

    private void mapContentCategoryPackage(ContentCategoryPackage categoryPackage) {
	for (ContentCategory contentCategory : categoryPackage.getContentCategory()) {
	    hashMap.put(contentCategory.getId(), contentCategory);
	}

	for (Object obj : categoryPackage.getReusedPackageOrMethodPackage()) {
	    if (obj instanceof ContentElement) {
		ContentElement contentElement = (ContentElement) obj;
		hashMap.put(contentElement.getId(), contentElement);
	    }
	}
    }

}
