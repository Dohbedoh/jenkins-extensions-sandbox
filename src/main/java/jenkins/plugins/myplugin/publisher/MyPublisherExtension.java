package jenkins.plugins.myplugin.publisher;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;
import jenkins.plugins.myplugin.Messages;
import jenkins.plugins.myplugin.project.MyProjectExtension;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by allan on 13/11/2015.
 */
public class MyPublisherExtension extends Publisher {

    private static final Logger LOGGER = Logger.getLogger(MyPublisherExtension.class.getName());
    public final String tagName;
    public final Set<Result> resultsToDiscard;

    @DataBoundConstructor
    public MyPublisherExtension(
            String tagName,
            boolean discardSuccess,
            boolean discardUnstable,
            boolean discardFailure,
            boolean discardNotBuilt,
            boolean discardAborted
    ) {

        this.tagName = tagName;

        resultsToDiscard = new HashSet<Result>();
        if (discardSuccess) {
            resultsToDiscard.add(Result.SUCCESS);
        }
        if (discardUnstable) {
            resultsToDiscard.add(Result.UNSTABLE);
        }
        if (discardFailure) {
            resultsToDiscard.add(Result.FAILURE);
        }
        if (discardNotBuilt) {
            resultsToDiscard.add(Result.NOT_BUILT);
        }
        if (discardAborted) {
            resultsToDiscard.add(Result.ABORTED);
        }
    }

    public String getTagName() {
        return tagName;
    }

    public boolean isDiscardSuccess() {
        return isDiscard(Result.SUCCESS);
    }

    public boolean isDiscardFailure() {
        return isDiscard(Result.FAILURE);
    }

    public boolean isDiscardAborted() {
        return isDiscard(Result.ABORTED);
    }

    public boolean isDiscardUnstable() {
        return isDiscard(Result.UNSTABLE);
    }

    public boolean isDiscardNotBuilt() {
        return isDiscard(Result.NOT_BUILT);
    }

    private boolean isDiscard(Result resultType) {
        return resultsToDiscard.contains(resultType);
    }


    /**
     * Override this method to specify what to perform and return the outcome of the action.
     * @param build
     * @param launcher
     * @param listener
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    @Override
    public boolean perform(AbstractBuild<?,?> build, Launcher launcher, BuildListener listener)
            throws InterruptedException, IOException {
        LOGGER.log(Level.INFO, "performing " + build.getResult());

        /**
         * TODO: try few things here
         */
        return !resultsToDiscard.contains(build.getResult());
    }

    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        public FormValidation doCheckTagName(@QueryParameter String tagName) {

            if (tagName.length() == 0) {
                return FormValidation.error("You need to set a tag name");
            }
            Pattern pattern = Pattern.compile("[A-Za-z0-9_]");
            Matcher matcher = pattern.matcher(tagName);
            if (!matcher.find()) {
                return FormValidation.error("Non digit and non special characters only");
            }

            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class jobType) {
            return MyProjectExtension.class.isAssignableFrom(jobType);
        }

        @Override
        public String getDisplayName() {
            return Messages.MyPublisherExtension_DisplayName();
        }
    }
}
