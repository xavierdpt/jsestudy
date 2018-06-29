package xpdtr.acme.gui;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.utils.Promise;

public class AccountDetailsRequest {

	public static Promise<AcmeResponse<String>> send(AcmeSession session) {

		Promise<AcmeResponse<String>> promise = new Promise<>();

		Thread thread = new Thread(() -> {
			try {
				promise.success(Acme2.accountDetails(session));
			} catch (Exception exception) {
				promise.failure(exception);
			}
		});
		promise.setThread(thread);

		return promise;
	}

}
