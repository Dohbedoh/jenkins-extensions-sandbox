package jenkins.plugins.myplugin.action;

import hudson.model.Action;

/**
 * @author Allan Burdajewicz
 */
public class MyActionExtension implements Action {

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "MyAction";
    }

    @Override
    public String getUrlName() {
        return "myaction";
    }
}
