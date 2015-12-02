package tr.org.unicase.web.component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import tr.org.unicase.web.component.internals.config.Configuration;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;

public class UnicaseUploadBox extends Upload implements Receiver, ProgressListener, FailedListener, SucceededListener, StartedListener {

	private ByteArrayOutputStream os = new ByteArrayOutputStream(Configuration.Uploadbox.BAOS_LEN);

	private String[] avaliableMimeTypes = Configuration.Uploadbox.DEFAULT_MIMETYPES;

	private String fileName;
	private byte[] fileContent;

	
	private ProgressBar progress = new ProgressBar(0.0f);
	private FinishedListener finishedListener = null;

	public UnicaseUploadBox() {
		setReceiver(this);
		addProgressListener(this);
		addFailedListener(this);
		addSucceededListener(this);
	}

	public UnicaseUploadBox(FinishedListener succeededListener) {
		this();
		finishedListener = succeededListener;
	}

	private boolean isMimeTypeAvaliable(String mimeType) {
		System.out.println("the mimetype : " + mimeType);
		for (int i = 0; i < avaliableMimeTypes.length; i++) {
			if (mimeType.equals(avaliableMimeTypes[i])) {
				return true;
			}
		}

		return false;
	}

	public OutputStream receiveUpload(String filename, String mimeType) {
		this.setFileName(filename);
		// Needed to allow re-uploading
		os.reset();
		return os;

	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {
		progress.setVisible(true);
		if (contentLength == -1 || contentLength > Configuration.Uploadbox.MAX_SIZE || readBytes > Configuration.Uploadbox.MAX_SIZE) {
			this.interruptUpload();
			progress.setIndeterminate(true);
			this.setCaption("Dosya boyutu 10 MB dan büyük olamaz");
		} else {
			progress.setIndeterminate(false);
			progress.setValue(((float) readBytes) / ((float) contentLength));
		}
	}

	public void uploadSucceeded(SucceededEvent event) {
		try {
			setFileContent(os.toByteArray());
			System.out.println("fileContent length : " + getFileContent().length);
			if (getFileContent().length > 0) {
				Notification.show(getFileName() + " dosyası upload edildi.", Type.HUMANIZED_MESSAGE);
				this.setCaption("Dosyanız yüklenmiştir. Kaydettikten sonra yeni bir dosya yükleyebilirsiniz.");
				if (finishedListener != null) {
					finishedListener.fireEvent(new UnicaseUploadFinisedEvent(this));
				}
			} else {
				Notification.show("Yuklemek istediginiz bir dosya secin.", Type.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
		}

	}

	@Override
	public void uploadFailed(FailedEvent event) {
		Notification.show("Upload (" + event.getFilename() + ") islemi basarışız.", Type.ERROR_MESSAGE);
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public static interface FinishedListener {
		public void fireEvent(UnicaseUploadFinisedEvent event);
	}

	public static class UnicaseUploadFinisedEvent {
		private UnicaseUploadBox source;

		public UnicaseUploadFinisedEvent(UnicaseUploadBox box) {
			this.source = box;
		}

		public UnicaseUploadBox getSource() {
			return source;
		}

		public void setSource(UnicaseUploadBox source) {
			this.source = source;
		}

	}

	public void setAvailableMimeTypes(String[] mimeTypes) {
		if (mimeTypes != null && mimeTypes.length > 0)
			avaliableMimeTypes = mimeTypes;
	}

	@Override
	public void uploadStarted(StartedEvent event) {
		String contentType = event.getMIMEType();

		if (!isMimeTypeAvaliable(contentType)) {
			Notification.show(contentType + " kabul edilen format değil", Type.ERROR_MESSAGE);
			this.interruptUpload();
		}
	}

}
