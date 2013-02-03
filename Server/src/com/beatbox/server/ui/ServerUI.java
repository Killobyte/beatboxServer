package com.beatbox.server.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.beatbox.lib.Library;
import com.beatbox.lib.song.Song;
import com.beatbox.server.ServerController;

public class ServerUI {

	ServerController controller;
	JFrame mainFrame;
	JTree libraryBrowser;
	PlaylistModel playlistModel;
	JList<Song> playlist;
	JButton playPauseButton;

	public ServerUI(ServerController controller) {
		this.controller = controller;

		// Create control buttons
		playPauseButton = new JButton("Play");
		playPauseButton.addActionListener(new PlayButtonListener(controller));

		// Create lables for IP and Port
		JLabel ipLabel = null;
		try {
			ipLabel = new JLabel("IP: "
					+ InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			System.err.println("Error get IP addr");
			e.printStackTrace();
		}
		JLabel portLabel = new JLabel("Port: "
				+ Integer.toString(controller.getServerPort()));

		/*
		 * JPanel networkPanel = new JPanel(); networkPanel .setLayout(new
		 * BoxLayout(networkPanel, BoxLayout.PAGE_AXIS));
		 * networkPanel.add(ipLabel); networkPanel.add(portLabel);
		 */

		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
		header.add(Box.createHorizontalGlue());
		header.add(playPauseButton);
		header.add(Box.createHorizontalGlue());
		// header.add(networkPanel);

		// Need this to set up environment to play MP3s
		final JFXPanel fxPanel = new JFXPanel();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(fxPanel);
			}
		});

		// Create library browser
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Library");
		Library library = controller.getLibrary();
		for (String artist : library.getArtists()) {
			DefaultMutableTreeNode artistNode = new DefaultMutableTreeNode(
					artist);
			for (Song song : library.getSongs(artist)) {
				SongTreeNode songNode = new SongTreeNode(song);
				artistNode.add(songNode);
			}
			root.add(artistNode);
		}
		libraryBrowser = new JTree(root);
		JScrollPane libraryScrollView = new JScrollPane(libraryBrowser);
		libraryScrollView.setPreferredSize(new Dimension(300, 450));

		// Create panel for add/remove buttons
		JButton addButton = new JButton(">");
		addButton.addActionListener(new AddButtonListener(controller));

		JButton removeButton = new JButton("<");
		removeButton.addActionListener(new RemoveButtonListener(controller));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(addButton);
		buttonPanel.add(createButtonSpacer());
		buttonPanel.add(removeButton);
		buttonPanel.add(Box.createVerticalGlue());

		// Create playlist window
		playlistModel = new PlaylistModel();
		playlist = new JList<Song>(playlistModel);
		playlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		playlist.setVisibleRowCount(-1);
		playlist.setCellRenderer(new PlaylistCellRenderer(controller));
		playlist.addMouseListener(new PlaylistClickListener(playlist,
				controller));

		JScrollPane playlistScroller = new JScrollPane(playlist);
		playlistScroller.setPreferredSize(new Dimension(300, 450));

		JPanel browserPanel = new JPanel();
		browserPanel
				.setLayout(new BoxLayout(browserPanel, BoxLayout.LINE_AXIS));
		browserPanel.add(libraryScrollView);
		browserPanel.add(createPanelSpacer());
		browserPanel.add(buttonPanel);
		browserPanel.add(createPanelSpacer());
		browserPanel.add(playlistScroller);

		// Footer
		JPanel networkPanel = new JPanel();
		networkPanel
				.setLayout(new BoxLayout(networkPanel, BoxLayout.LINE_AXIS));
		networkPanel.add(Box.createHorizontalGlue());
		networkPanel.add(ipLabel);
		networkPanel.add(createButtonSpacer());
		networkPanel.add(portLabel);
		networkPanel.add(Box.createHorizontalGlue());

		mainFrame = new JFrame();
		mainFrame.setTitle("BeatBox Home Jukebox");
		mainFrame.add(header, BorderLayout.PAGE_START);
		mainFrame.add(browserPanel, BorderLayout.CENTER);
		mainFrame.add(networkPanel, BorderLayout.PAGE_END);
		mainFrame.add(fxPanel, BorderLayout.EAST);

		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.addWindowListener(new BeatBoxWindowListener(controller));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setVisible(true);
	}

	private static void initFX(JFXPanel fxPanel) {
		// This method is invoked on JavaFX thread
		Group root = new Group();
		Scene scene = new Scene(root);
		fxPanel.setScene(scene);
	}

	private Component createSpacer(int size) {
		return Box.createRigidArea(new Dimension(size, size));
	}

	private Component createButtonSpacer() {
		return createSpacer(10);
	}

	private Component createPanelSpacer() {
		return createSpacer(20);
	}

	public JTree getLibraryTree() {
		return libraryBrowser;
	}

	public void addSongToPlaylist(Song song) {
		playlistModel.addSong(song);
	}

	public void removeSongFromPlaylist(int index) {
		playlistModel.removeSong(index);
	}

	public JList<Song> getPlaylist() {
		return playlist;
	}

	public void setPlayButtonText(String text) {
		playPauseButton.setText(text);
	}

	public void refreshPlaylist() {
		playlistModel.refresh();
	}
}
