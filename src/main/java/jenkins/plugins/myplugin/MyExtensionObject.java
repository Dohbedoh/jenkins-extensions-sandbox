package jenkins.plugins.myplugin;

import hudson.model.User;

/**
 * Created by Allan on 7/11/2015.
 */
public abstract class MyExtensionObject {

    public abstract String getObjectName();

    public abstract String getObjectVersion();

    public abstract String getMessage();

    public abstract User getUser();
}
