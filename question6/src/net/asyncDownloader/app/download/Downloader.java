import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {

	private HttpURLConnection httpConn;

	private InputStream inputStream;

	private String fileName;
	private int contentLength;

	//code to download file from the url
	public void downloadFile(String fileURL) throws IOException {
		URL url = new URL(fileURL);
		httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();
	
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			contentLength = httpConn.getContentLength();
	
			if (contentType != null && contentType.startsWith("image")) {
				if (contentType.endsWith("png")) {
					if (disposition != null) {
						int index = disposition.indexOf("filename=");
						if (index > 0) {
							fileName = disposition.substring(index + 10,
									disposition.length() - 1);
						}
					} else {
						fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
								fileURL.length());
					}
	
					System.out.println("Content-Type = " + contentType);
					System.out.println("Content-Disposition = " + disposition);
					System.out.println("Content-Length = " + contentLength);
					System.out.println("fileName = " + fileName);
	
					inputStream = httpConn.getInputStream();
				} else {
					throw new IOException("Invalid content type. Expected image/png.");
				}
			} else {
				throw new IOException("Invalid content type. Expected an image.");
			}
		} else {
			throw new IOException("No file to download. Server replied HTTP code: " + responseCode);
		}
	}

	public void disconnect() throws IOException {
		inputStream.close();
		httpConn.disconnect();
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getContentLength() {
		return this.contentLength;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}
}