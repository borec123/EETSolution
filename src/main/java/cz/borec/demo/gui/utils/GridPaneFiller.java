package cz.borec.demo.gui.utils;

import java.util.List;
import java.util.Map;

import cz.borec.demo.core.entity.OrderState;
import cz.borec.demo.gui.controls.BlueText;
import cz.borec.demo.gui.controls.ButtonSizeUtils;
import cz.borec.demo.gui.controls.LiveButton;
import javafx.scene.control.Separator;
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
		double BUTTON_SIZE = ButtonSizeUtils.getTouchButtonSize();
		for(OrderState key : buttonMap.keySet()) {
			List<LiveButton> buttonList = buttonMap.get(key);
			boolean empty = buttonList.isEmpty();
			if(!(key == OrderState.STORNO && empty )) {
				BlueText l = new BlueText(key.toString(), 12);
				g.add(l, 0, j++, (int) (gridWidth / (BUTTON_SIZE + g.getHgap())), 1);
			}
			//Separator separator = new Separator();
			//g.add(separator, 0, j++, (int) (gridWidth / (BUTTON_SIZE + g.getHgap())), 1);
			if(!empty) {
			for (LiveButton liveButton : buttonList) {
				g.add(liveButton, i++, j);
				width += BUTTON_SIZE + g.getHgap();
				if (width > gridWidth - BUTTON_SIZE) {
					width = 0.0;
					i = 0;
					j++;
				}
			}
			}
			else {
				if(!(key == OrderState.STORNO)) {
				BlueText l = new BlueText("\t0", 12);
				g.add(l, 0, j++, (int) (gridWidth / (BUTTON_SIZE + g.getHgap())), 1);
				}
			}
			width = 0.0;
			i = 0;
			j++;
		}

	}


}
