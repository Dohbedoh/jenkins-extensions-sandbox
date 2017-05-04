package jenkins.plugins.myplugin;

import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.User;
import jenkins.model.Jenkins;

import javax.annotation.CheckForNull;

/**
 * Extensible property of {@link MyExtensionObject}.
 *
 * Created by Allan on 7/11/2015.
 */
public abstract class MyExtensionProperty { //extends AbstractDescribableImpl<MyExtensionProperty> implements ExtensionPoint {

    /**
     * Get the information of the {@link MyExtensionObject}.
     *
     * @param myExtensionObject {@link MyExtensionObject}
     * @return The array of information
     */
    public abstract String[] getMyExtensionObjectInfo(MyExtensionObject myExtensionObject);


    public abstract void doA();

    public void doB() {

    }

    public abstract void doC();

//    /**
//     * Returns all the registered {@link MyExtensionProperty} descriptors.
//     */
//    public static DescriptorExtensionList<MyExtensionProperty,Descriptor<MyExtensionProperty>> all() {
//        return (DescriptorExtensionList) Jenkins.getInstance().getDescriptorList(MyExtensionProperty.class);
//    }

}