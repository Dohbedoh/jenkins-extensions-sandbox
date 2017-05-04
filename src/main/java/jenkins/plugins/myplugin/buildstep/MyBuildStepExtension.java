package jenkins.plugins.myplugin.buildstep;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.plugins.myplugin.Messages;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

/**
 * Created by Allan on 7/11/2015.
 */
public class MyBuildStepExtension extends Builder implements SimpleBuildStep {

    /**
     * The version of My Extension to use.
     */
    private String myVersion;

    /**
     * The messages to display.
     */
    private List<MyBuildStepMessage> myMessages;

    //Advanced
    private String myTargetName;
    /**
     * Indicate if need to perform A.
     */
    private boolean showBuildInfo;

    /**
     * Indicate if need to perform B.
     */
    private boolean showPluginInfo;

    // Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public MyBuildStepExtension(
            String myVersion,
            List<MyBuildStepMessage> myMessages,
            String myTargetName,
            Boolean showBuildInfo,
            Boolean showPluginInfo) {

        this.myVersion = myVersion;
        this.myMessages = isEmpty(myMessages)
                ? newArrayList(new MyBuildStepMessage("This is a default message..."))
                : myMessages;
        this.myTargetName = myTargetName;
        this.showBuildInfo = showBuildInfo;
        this.showPluginInfo = showPluginInfo;
    }

    /**
     * We'll use this from the <tt>config.jelly</tt>.
     */
    public String getMyVersion() {
        return myVersion;
    }

    public String getMyTargetName() {
        return myTargetName;
    }

    public List<MyBuildStepMessage> getMyMessages() {
        return myMessages;
    }

    public boolean isShowBuildInfo() {
        return showBuildInfo;
    }

    public boolean isShowPluginInfo() {
        return showPluginInfo;
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {

        PrintStream logger = listener.getLogger();

        logger.println("Plugin Name:" + getDescriptor().getMyPluginName());
        logger.println("Plugin Path:" + getDescriptor().getMyPluginPath());
        logger.println("Unix:" + launcher.isUnix());
        for (MyBuildStepMessage message : myMessages) {
            logger.println(message.getValue());
        }

        if (showBuildInfo) {
            logger.println("Name:" + workspace.getName());
            logger.println("Free DS:" + workspace.getFreeDiskSpace());
            logger.println("Total DS:" + workspace.getTotalDiskSpace());
            logger.println("Useable DS:" + workspace.getUsableDiskSpace());
        }

        if (showPluginInfo) {
            logger.println("Unix:" + launcher.isUnix());
            logger.println("Target Name:" + myTargetName);
            logger.println("Version:" + myVersion);
        }
    }

    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        private String myPluginName;
        private String myPluginPath;

        public DescriptorImpl() {
            load();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.MyBuildStepExtension_DisplayName();
        }

        public String getMyPluginName() {
            return myPluginName;
        }

        public void setMyPluginName(String myPluginName) {
            this.myPluginName = myPluginName;
        }

        public String getMyPluginPath() {
            return myPluginPath;
        }

        public void setMyPluginPath(String myPluginPath) {
            this.myPluginPath = myPluginPath;
        }

        /**
         * Validate that at least one message is provided by the user.
         *
         * @param myMessages The list of messages provided by the user.
         * @return
         * @throws IOException
         * @throws ServletException
         */
        public FormValidation doCheckMyMessages(@QueryParameter List<MyBuildStepMessage> myMessages)
                throws IOException, ServletException {
            if (isEmpty(myMessages)) {
                return FormValidation.error("Please add at least one message");
            }
            return FormValidation.ok();
        }

        /**
         * Validate the format of the version number given.
         *
         * @param myVersion The version number provided by the user.
         * @return
         * @throws IOException
         * @throws ServletException
         */
        public FormValidation doCheckMyVersion(@QueryParameter String myVersion)
                throws IOException, ServletException {
            if (myVersion.length() == 0) {
                return FormValidation.error("You need to set a version number");
            }
            Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+");
            Matcher matcher = pattern.matcher(myVersion);
            if (!matcher.find()) {
                return FormValidation.error("Wrong version Number");
            }
            return FormValidation.ok();
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            // To persist global configuration information,
            // set that to properties and call save().
            myPluginName = formData.getString("myPluginName");
            myPluginPath = formData.getString("myPluginPath");
            // req.bindJSON(this, formData) is easier when there are many fields; need set* methods for this,
            // like setMyPluginName
            save();
            return super.configure(req, formData);
        }
    }
}
