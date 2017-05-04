package jenkins.plugins.myplugin.buildstep;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Created by Allan on 8/11/2015.
 */
public class MyBuildStepMessage implements Describable<MyBuildStepMessage> {

    private String value;

    @DataBoundConstructor
    public MyBuildStepMessage(String value) {
        this.value = value;
    }

    @Override
    public Descriptor<MyBuildStepMessage> getDescriptor() {
        return new DescriptorImpl();
    }

    public String getValue() {
        return value;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<MyBuildStepMessage> {
        public String getDisplayName() { return "Build Step Message"; }
    }
}
