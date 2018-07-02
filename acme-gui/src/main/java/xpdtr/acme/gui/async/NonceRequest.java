package xpdtr.acme.gui.async;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.utils.Promise;

public class NonceRequest {

	public static Promise<AcmeResponse<String>> send(AcmeDirectoryInfos2 infos, AcmeSession session) {

		Promise<AcmeResponse<String>> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					promise.done(Acme2.nonce(session));
				} catch (Exception e) {
					promise.done(new AcmeResponse<String>(e));
				}
			}

		});

		promise.setThread(thread);

		return promise;
	}

}
