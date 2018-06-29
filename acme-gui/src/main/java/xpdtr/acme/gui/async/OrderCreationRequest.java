package xpdtr.acme.gui.async;

import example.company.acme.AcmeSession;
import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeOrder;
import example.company.acme.v2.AcmeResponse;
import xpdtr.acme.gui.utils.Promise;

public class OrderCreationRequest {

	public static Promise<AcmeResponse<AcmeOrder>> send(AcmeSession session, String site) {
		Promise<AcmeResponse<AcmeOrder>> p = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AcmeResponse<AcmeOrder> response = Acme2.newOrder(session, site);
					p.success(response);
				} catch (Exception ex) {
					p.failure(ex);
				}
			}
		});

		p.setThread(thread);
		return p;
	}

}
