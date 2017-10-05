/*package cz.borec.demo.gui;

import java.util.List;

import javafx.event.ActionEvent;
import cz.borec.demo.core.dto.RoomDTO;
import cz.borec.demo.core.dto.TableDTO;
import cz.borec.demo.gui.controls.AlertHelper;
import cz.borec.demo.gui.controls.LiveButton;

public class SwitchTableRoomsPane extends RoomsPane {

	public SwitchTableRoomsPane(Controller controller, List<RoomDTO> list) {
		super(controller, list);
	}

	protected void handleTableclick(ActionEvent arg0) {
		tableDTO = (TableDTO) ((LiveButton) arg0.getSource()).getUserData();
		if (tableDTO.getOrderDTO() != null) {
			if (tableDTO.getOrderDTO().getItems().size() > 0) {
				AlertHelper.showInfoDialog(
						"Tento st\u016Fl m\u00E1 otev\u0159enou objedn\u00E1vku.",
						"Vyberte jin\u00FD st\u016Fl.");
				return;
			}
		}
		
		controller.switchTable(tableDTO);
		
	}

}
*/