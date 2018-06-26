package xpdtr.acme.gui.async;

import java.security.interfaces.ECPrivateKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import example.company.acme.v2.AcmeOrderWithNonce;
import xpdtr.acme.gui.utils.Promise;

public class OrderCreationRequest {

	public static Promise<AcmeOrderWithNonce> send(AcmeDirectoryInfos2 infos, String kid, String nonce, ObjectMapper om,
			ECPrivateKey privateKey, String site) {
		Promise<AcmeOrderWithNonce> p = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AcmeOrderWithNonce response = Acme2.newOrder(infos, kid, nonce, om, privateKey,site);
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
