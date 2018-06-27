package xpdtr.acme.gui.interactions;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import example.company.acme.AcmeSession;
import example.company.acme.v2.AcmeDirectoryInfos2;
import xpdtr.acme.gui.async.DirectoryRequest;
import xpdtr.acme.gui.components.DirectoryUI;
import xpdtr.acme.gui.utils.U;

public class DirectoryInteraction extends UIInteraction {

	private AcmeSession session;

	public DirectoryInteraction(Container container, AcmeSession session, Runnable validate, Runnable finished) {
		super(container, validate, finished);
		this.session = session;
	}

	@Override
	public void start() {
		container.add(DirectoryUI.renderStarting());
		validate();
		DirectoryRequest.send(session.getUrl(),session.getOm()).then(this::success, this::failure);
	}

	private void success(AcmeDirectoryInfos2 infos) {
		List<Component> responseComponents = DirectoryUI.getSuccessComponents(infos);
		U.addM(container, responseComponents);
		session.setInfos(infos);
		finished();
		validate();
	}

	private void failure(Exception exception) {
		container.add(DirectoryUI.getFailureComponent(exception));
		finished();
		validate();
	}

}
