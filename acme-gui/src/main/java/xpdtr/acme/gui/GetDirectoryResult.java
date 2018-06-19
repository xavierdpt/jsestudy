package xpdtr.acme.gui;

import example.company.acme.v2.AcmeDirectoryInfos2;

public class GetDirectoryResult {

	private AcmeDirectoryInfos2 infos;
	private Exception exception;

	public AcmeDirectoryInfos2 getInfos() {
		return infos;
	}

	public void setInfos(AcmeDirectoryInfos2 infos) {
		this.infos = infos;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;

	}

}
