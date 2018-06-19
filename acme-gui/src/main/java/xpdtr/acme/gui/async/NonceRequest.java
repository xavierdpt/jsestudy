package xpdtr.acme.gui.async;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import xpdtr.acme.gui.utils.Promise;

public class NonceRequest {

	public static Promise<String> send(AcmeDirectoryInfos2 infos) {

		Promise<String> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					promise.success(Acme2.nonce(infos));
				} catch (Exception e) {
					promise.failure(e);
				}
			}

		});

		promise.setThread(thread);

		return promise;
	}
	
}
