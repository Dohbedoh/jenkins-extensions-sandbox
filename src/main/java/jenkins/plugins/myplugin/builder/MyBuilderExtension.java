package jenkins.plugins.myplugin.builder;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.plugins.myplugin.Messages;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Created by allan on 13/11/2015.
 */
public class MyBuilderExtension extends Builder {

    private final String task;

    @DataBoundConstructor
    public MyBuilderExtension(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    @Extension
    public static class Descriptor extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return FreeStyleProject.class.isAssignableFrom(jobType);
        }

        @Override
        public String getDisplayName() {
            return Messages.MyBuilderExtension_DisplayName();
        }
    }
}
