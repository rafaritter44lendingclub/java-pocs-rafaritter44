package com.github.rafaritter44.java_pocs.elasticsearch;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class SearchAsYouType extends JPanel implements KeyListener {

	private static final long serialVersionUID = -1139983212741864762L;
	
	private final MovieService service;
	private final StringBuilder userInput;
	private final JTextField textField;
	private final JTextArea textArea;

	public SearchAsYouType() {
		super(new GridBagLayout());
		
		final RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")));
		service = new MovieService(client);
		
		userInput = new StringBuilder();

		textField = new JTextField(20);
		textField.addKeyListener(this);

		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		final JScrollPane scrollPane = new JScrollPane(textArea);

		// Add Components to this panel.
		final GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		add(textField, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}

	/**
	 * Create the GUI and show it. For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		final JFrame frame = new JFrame("Search As You Type");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.
		frame.add(new SearchAsYouType());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		userInput.append(e.getKeyChar());
		textArea.setText("");
		final List<Movie> results = service.searchAsYouTypeMovieTitle(userInput.toString());
		results
				.stream()
				.map(Movie::getTitle)
				.forEach(movieTitle -> textArea.append(movieTitle + "\n"));
	}

	@Override
	public void keyPressed(final KeyEvent e) {}

	@Override
	public void keyReleased(final KeyEvent e) {}
	
}
