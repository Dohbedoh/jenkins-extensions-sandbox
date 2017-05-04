package jenkins.plugins.myplugin.project;

import hudson.model.Build;

import java.io.File;
import java.io.IOException;

/**
 * Created by allan on 13/11/2015.
 */
public class MyProjectExtensionBuild extends Build<MyProjectExtension, MyProjectExtensionBuild> {

    public MyProjectExtensionBuild(MyProjectExtension project) throws IOException {
        super(project);
    }

    public MyProjectExtensionBuild(MyProjectExtension project, File buildDir) throws IOException {
        super(project, buildDir);
    }

    @Override
    public void run() {
        execute(new MyBuildExecution());
    }

    /**
     * @see {@link Build} for custom Build and BuildExecution.
     */
    public class MyBuildExecution extends BuildExecution {

    }
}
