package example.company.tox.java.security.cert;

import java.util.ArrayList;
import java.util.List;

import com.brightsparklabs.asanti.model.data.RawAsnData;

public class RawAsnDataDescription {

	private List<String> tags = new ArrayList<>();
	private List<TaggedBytes> taggedBytes = new ArrayList<>();

	public RawAsnDataDescription(RawAsnData rawData) {
		/*
		 * rawData.getRawTags().forEach((tag) -> { tags.add(tag); });
		 */
		rawData.getBytes().forEach((s, b) -> {
			taggedBytes.add(new TaggedBytes(s, b));
		});
		/*
		 * contains(String) contains(Pattern) getBytes() getBytes(String)
		 * getBytesMatching(Pattern) getRawTags()
		 */
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<TaggedBytes> getTaggedBytes() {
		return taggedBytes;
	}

	public void setTaggedBytes(List<TaggedBytes> taggedBytes) {
		this.taggedBytes = taggedBytes;
	}

}
