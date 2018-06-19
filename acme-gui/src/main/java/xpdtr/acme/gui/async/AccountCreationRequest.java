package xpdtr.acme.gui.async;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.account.AcmeAccount;
import xpdtr.acme.gui.utils.Promise;

public class AccountCreationRequest {

	public static Promise<AcmeAccount> send(AcmeDirectoryInfos2 infos, String nonce, ObjectMapper om, String contact) {

		Promise<AcmeAccount> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					promise.success(Acme2.newAccount(infos, nonce, om, contact));
				} catch (Exception exception) {
					promise.failure(exception);
				}
			}
		});
		promise.setThread(thread);

		return promise;
	}

}
