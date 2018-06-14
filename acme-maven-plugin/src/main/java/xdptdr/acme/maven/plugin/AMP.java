package xdptdr.acme.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import xpdtr.acme.gui.ExceptionHandler;
import xpdtr.acme.gui.ExceptionType;
import xpdtr.acme.gui.Main;

@Mojo(name = "gui")
public class AMP extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Main.show(new ExceptionHandler() {
			@Override
			public void handle(Exception e, ExceptionType et) {
				switch (et) {
				case FATAL:
					getLog().error(e.getClass().getName() + " : " + e.getMessage());
				case NOT_FATAL:
					getLog().warn(e.getClass().getName() + " : " + e.getMessage());
				}
			}
		});
	}

}