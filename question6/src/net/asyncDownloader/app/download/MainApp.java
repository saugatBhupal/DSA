import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class MainApp extends JFrame{
    private JPanel downloadPanel;
    private ExecutorService executorService;

    // creating a main app so that each individual download can take place inside an independent widget.
    public MainApp() {
        super("MultiThreaded Downloader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        downloadPanel = new JPanel();
        downloadPanel.setLayout(new BoxLayout(downloadPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(downloadPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewDownloader();
            }
        });

        add(newButton, BorderLayout.SOUTH);

        executorService = Executors.newCachedThreadPool();

        pack();
        setLocationRelativeTo(null);
    }

    //When the new button is clicked a new SwingFileDownload widget will be created in a new thread.
    //This will ensure each download is taking place independently.
    private void addNewDownloader() {
        DownloadWidget downloader = new DownloadWidget();
        executorService.submit(() -> {
            downloadPanel.add(downloader);
            revalidate();
            repaint();
        });
    }


    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainApp().setVisible(true);
			}
		});
	}
}
