package example.company.tox.java.security.cert;

import java.util.ArrayList;
import java.util.List;

import com.brightsparklabs.asanti.model.data.RawAsnData;
import com.google.common.collect.ImmutableList;

public class AsantiAsnDescription {

	private List<RawAsnDataDescription> asnData = new ArrayList<>();

	public AsantiAsnDescription(ImmutableList<RawAsnData> data) {
		data.forEach((rawData) -> {
			asnData.add(new RawAsnDataDescription(rawData));
		});
	}

	public List<RawAsnDataDescription> getAsnData() {
		return asnData;
	}

	public void setAsnData(List<RawAsnDataDescription> asnData) {
		this.asnData = asnData;
	}

}
