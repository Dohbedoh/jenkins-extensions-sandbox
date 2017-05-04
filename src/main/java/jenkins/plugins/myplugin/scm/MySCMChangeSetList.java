package jenkins.plugins.myplugin.scm;

import hudson.model.Run;
import hudson.scm.ChangeLogSet;
import hudson.scm.RepositoryBrowser;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Allan Burdajewicz
 */
public class MySCMChangeSetList extends ChangeLogSet<MySCMChangeSet> {

    private final List<MySCMChangeSet> changeSets;

    MySCMChangeSetList(Run<?,?> build, RepositoryBrowser<?> browser, List<MySCMChangeSet> logs) {
        super(build, browser);
        Collections.reverse(logs);  // put new things first
        this.changeSets = Collections.unmodifiableList(logs);
    }

    @Override
    public boolean isEmptySet() {
        return false;
    }

    @Override
    public Iterator<MySCMChangeSet> iterator() {
        return null;
    }
}
