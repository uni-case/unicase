package tr.org.unicase.web.component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

public class UnicaseFileDownloader extends FileDownloader {

	public UnicaseFileDownloader(String fileName, byte[] data) {
		this(createResource(fileName, data));
	}

	public UnicaseFileDownloader(Resource resource) {
		super(resource);

	}

	private static StreamSource createStreamSource(final byte[] data) {
		return new StreamSource() {

			@Override
			public InputStream getStream() {
				return new ByteArrayInputStream(data);
			}
		};
	}

	private static StreamResource createResource(String fileName, byte[] data) {
		return new StreamResource(createStreamSource(data), fileName);
	}

}
