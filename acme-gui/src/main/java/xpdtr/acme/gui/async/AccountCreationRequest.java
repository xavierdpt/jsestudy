package xpdtr.acme.gui.async;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import xpdtr.acme.gui.utils.Promise;

public class AccountCreationRequest {

	public static Promise<AcmeSession> send(AcmeSession session, String contact) {

		Promise<AcmeSession> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					promise.success(Acme2.newAccount(session, contact));
				} catch (Exception exception) {
					promise.failure(exception);
				}
			}
		});
		promise.setThread(thread);

		return promise;
	}

}
