package xpdtr.acme.gui.async;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.utils.Promise;

public class DirectoryRequest {

	public static Promise<AcmeResponse<AcmeDirectoryInfos2>> send(String url, ObjectMapper om, AcmeSession session) {

		Promise<AcmeResponse<AcmeDirectoryInfos2>> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AcmeResponse<AcmeDirectoryInfos2> response = Acme2.directory(url, om, session);
					promise.done(response);
				} catch (Exception e) {
					promise.done(new AcmeResponse<>(e));
				}
			}
		});
		promise.setThread(thread);

		return promise;
	}

}
