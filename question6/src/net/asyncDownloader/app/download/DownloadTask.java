import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class DownloadTask extends SwingWorker<Void, Void> {
	private static final int BUFFER_SIZE = 4096;	
	private String downloadURL;
	private String saveDirectory;
	private DownloadWidget gui;
	private volatile boolean pause = false;
	
	public DownloadTask(DownloadWidget gui, String downloadURL, String saveDirectory) {
		this.gui = gui;
		this.downloadURL = downloadURL;
		this.saveDirectory = saveDirectory;
	}

	//set the pause state to true
	public void pause(){
		pause = true;
	}

	//set the pause statte to false
	public void resume(){
		pause = false;
		execute();
	}

	//utilizing the doInBackground to further asynchronize the download function.
	//using this function allows the widget to be fully functional while the image continues to download
	@Override
	protected Void doInBackground() throws Exception {
		try {
			Downloader util = new Downloader();
			util.downloadFile(downloadURL);
			gui.setFileInfo(util.getFileName(), util.getContentLength());
			
			String saveFilePath = saveDirectory + File.separator + util.getFileName();

			InputStream inputStream = util.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			long totalBytesRead = 0;
			int percentCompleted = 0;
			long fileSize = util.getContentLength();

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				if(!pause)
					outputStream.write(buffer, 0, bytesRead);
					totalBytesRead += bytesRead;
					percentCompleted = (int) (totalBytesRead * 100 / fileSize);
	
					setProgress(percentCompleted);			
			}

			outputStream.close();

			util.disconnect();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(gui, "Error downloading file: " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);			
			ex.printStackTrace();
			setProgress(0);
			cancel(true);			
		}
		return null;
	}
	
	//once the doInBackground job is complete, this code block is initiated
	@Override
	protected void done() {
		if (!isCancelled()) {
			JOptionPane.showMessageDialog(gui,
					"File has been downloaded successfully!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}	
}