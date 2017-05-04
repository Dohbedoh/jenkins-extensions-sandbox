package jenkins.plugins.myplugin.project;

import hudson.Extension;
import hudson.model.ItemGroup;
import hudson.model.Project;
import hudson.model.TopLevelItem;
import jenkins.model.Jenkins;
import jenkins.plugins.myplugin.Messages;

/**
 * Created by allan on 13/11/2015.
 */
public class MyProjectExtension extends Project<MyProjectExtension, MyProjectExtensionBuild> implements TopLevelItem {

    public MyProjectExtension(ItemGroup parent, String name) {
        super(parent, name);
    }

    @Override
    protected Class<MyProjectExtensionBuild> getBuildClass() {
        return MyProjectExtensionBuild.class;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

    @Extension(ordinal = 0)
    public static class DescriptorImpl extends AbstractProjectDescriptor {

        @Override
        public String getDisplayName() {
            return Messages.MyProjectExtension_DisplayName();
        }

        @Override
        public MyProjectExtension newInstance(ItemGroup parent, String name) {
            return new MyProjectExtension(parent, name);
        }
    }
}
