package cz.borec.demo.gui.utils;

import java.util.List;
import java.util.Map;

import cz.borec.demo.core.entity.OrderState;
import cz.borec.demo.gui.controls.ButtonSizeUtils;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.scene.layout.GridPane;

public class GridPaneFiller {

	public static void fillButtons(GridPane g, List<LiveButton> buttonList, double gridWidth) {
		int i = 0;
		int j = 0;
		// Node hovno = (Node)g.getParent().getParent().getParent().getParent();
		double width = 0.0;
		g.getChildren().clear();
		for (LiveButton liveButton : buttonList) {
			g.add(liveButton, i++, j);
			double BUTTON_SIZE = ButtonSizeUtils.getTouchButtonSize();
			width += BUTTON_SIZE + g.getHgap();
			if (width > gridWidth - BUTTON_SIZE) {
				width = 0.0;
				i = 0;
				j++;
			}
		}

	}

	public static void fillButtons(GridPane g, Map<OrderState, List<LiveButton>> buttonMap, double gridWidth) {
		int i = 0;
		int j = 0;
		// Node hovno = (Node)g.getParent().getParent().getParent().getParent();
		double width = 0.0;
		g.getChildren().clear();
		for(OrderState key : buttonMap.keySet()) {
			List<LiveButton> buttonList = buttonMap.get(key);
			for (LiveButton liveButton : buttonList) {
				g.add(liveButton, i++, j);
				double BUTTON_SIZE = ButtonSizeUtils.getTouchButtonSize();
				width += BUTTON_SIZE + g.getHgap();
				if (width > gridWidth - BUTTON_SIZE) {
					width = 0.0;
					i = 0;
					j++;
				}
			}
			width = 0.0;
			i = 0;
			j++;
		}

	}


}
