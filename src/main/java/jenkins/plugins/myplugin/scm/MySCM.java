package jenkins.plugins.myplugin.scm;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Job;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.scm.*;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

public class MySCM extends SCM implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(MySCM.class.getName());

    private String myUrl;
    private String myUser;
    private String myPassword;

    @DataBoundConstructor
    public MySCM(String myUrl, String myUser, String myPassword) {
        this.myUrl = myUrl;
        this.myUser = myUser;
        this.myPassword = myPassword;
    }

    @Override
    public String getKey() {
        return "myplugin-" + myUrl.hashCode();
    }

    @Override
    public boolean requiresWorkspaceForPolling() {
        return false;
    }

    @Override
    public SCMRevisionState calcRevisionsFromBuild(
            Run<?, ?> build,
            FilePath workspace,
            Launcher launcher,
            TaskListener listener)
            throws IOException, InterruptedException {
        return SCMRevisionState.NONE;
    }

    @Override
    public PollingResult compareRemoteRevisionWith(
            Job<?, ?> project,
            Launcher launcher,
            FilePath workspace,
            TaskListener listener,
            SCMRevisionState _baseline) throws IOException, InterruptedException {
        return PollingResult.NO_CHANGES;
    }

    @Override
    public void checkout(
            Run<?, ?> build,
            Launcher launcher,
            FilePath workspace,
            TaskListener listener,
            File changelogFile,
            SCMRevisionState baseline) throws IOException, InterruptedException {

        if (!workspace.exists()) {
            workspace.mkdirs();
            listener.getLogger().println("Workspace created");
        }

        listener.getLogger().println("Start checkout");

        /**
         * TODO: Get the files.
         */

        listener.getLogger().println("Finish checkout");
    }

    @Override
    public ChangeLogParser createChangeLogParser() {
        return new MySCMChangeLogParser();
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    public String getMyUrl() {
        return myUrl;
    }

    @DataBoundSetter public final void setMyUrl(String myUrl) {
        this.myUrl = myUrl;
    }

    public String getMyUser() {
        return myUser;
    }

    @DataBoundSetter public final void setMyUser(String myUser) {
        this.myUser = myUser;
    }

    public String getMyPassword() {
        return myPassword;
    }

    @DataBoundSetter public final void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }


    @Extension
    public static final class DescriptorImpl extends SCMDescriptor<MySCM> {
        public DescriptorImpl() {
            super(MySCM.class, null);
            load();
        }

        @Override
        public SCM newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            MySCM scm = (MySCM) super.newInstance(req, formData);
            return scm;
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            save();
            return true;
        }

        @Override
        public String getDisplayName() {
            return "MySCM";
        }

        @Override
        public boolean isApplicable(Job project) {
            return true;
        }
    }
}

