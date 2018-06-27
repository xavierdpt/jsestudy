package xpdtr.acme.gui.async;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.company.acme.v2.Acme2;
import example.company.acme.v2.AcmeDirectoryInfos2;
import xpdtr.acme.gui.utils.Promise;

public class DirectoryRequest {

	public static Promise<AcmeDirectoryInfos2> send(String url, ObjectMapper om) {

		Promise<AcmeDirectoryInfos2> promise = new Promise<>();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AcmeDirectoryInfos2 infos = Acme2.directory(url, om);
					promise.success(infos);
				} catch (Exception e) {
					promise.failure(e);
				}
			}
		});
		promise.setThread(thread);

		return promise;
	}
	
}
