package jenkins.plugins.myplugin.scm;

import hudson.model.User;
import hudson.scm.ChangeLogSet;

import java.util.Collection;

/**
 * @author Allan Burdajewicz
 */
public class MySCMChangeSet extends ChangeLogSet.Entry {

    @Override
    public ChangeLogSet getParent() {
        return super.getParent();
    }

    @Override
    public String getCommitId() {
        return super.getCommitId();
    }

    @Override
    public long getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public User getAuthor() {
        return null;
    }

    @Override
    public Collection<String> getAffectedPaths() {
        return null;
    }

    @Override
    public Collection<? extends ChangeLogSet.AffectedFile> getAffectedFiles() {
        return super.getAffectedFiles();
    }

    @Override
    public String getMsgAnnotated() {
        return super.getMsgAnnotated();
    }

    @Override
    public String getMsgEscaped() {
        return super.getMsgEscaped();
    }
}
