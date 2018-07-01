package xpdtr.acme.gui;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.utils.Promise;

public class AccountDeactivationRequest {

	public static Promise<AcmeResponse<Boolean>> send(AcmeSession session) {

		Promise<AcmeResponse<Boolean>> promise = new Promise<>();

		promise.setThread(new Thread(() -> {
			try {
				promise.success(Acme2.deactivateAccount(session));
			} catch (Exception e) {
				promise.failure(e);
			}
		}));

		return promise;
	}

}
