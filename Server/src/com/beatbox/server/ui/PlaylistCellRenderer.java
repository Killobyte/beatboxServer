package com.beatbox.server.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.beatbox.server.ServerController;

public class PlaylistCellRenderer extends JLabel implements
		ListCellRenderer<Object> {

	private static final long serialVersionUID = 5044061666288879352L;
	private ServerController controller;

	public PlaylistCellRenderer(ServerController controller) {
		this.controller = controller;
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object obj,
			int index, boolean isSelected, boolean cellHasFocus) {

		setText(obj.toString());

		if (controller.getCurrentPlayingIndex() == index) {
			setForeground(Color.RED);
		} else {
			setForeground(Color.BLACK);
		}

		if (isSelected) {
			setBackground(Color.BLUE);
			if (controller.getCurrentPlayingIndex() != index) {
				setForeground(Color.WHITE);
			}
		} else {
			setBackground(Color.WHITE);
		}

		return this;
	}

}
