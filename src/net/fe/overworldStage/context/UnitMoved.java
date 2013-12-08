package net.fe.overworldStage.context;

import java.util.Arrays;
import java.util.List;

import net.fe.overworldStage.*;
import net.fe.unit.Unit;

public class UnitMoved extends MenuContext {
	private int origX, origY;
	private Unit unit;
	private Zone zone;

	public UnitMoved(OverworldStage stage, OverworldContext prev, Unit u,
			int origX, int origY) {
		super(stage, prev, new Menu(0, 0));

		this.origX = origX;
		this.origY = origY;
		unit = u;

		// TODO Auto-generated constructor stub
	}

	public void startContext() {
		super.startContext();
		for (String cmd : getCommands(unit)) {
			menu.addItem(cmd);
		}
		updateZones();
	}

	public List<String> getCommands(Unit u) {
		// TODO
		return Arrays.asList("Attack", "Heal", "Item", "Wait");
	}

	@Override
	public void onSelect(String selectedItem) {
		// TODO
		System.out.println("Selected " + selectedItem);
		if (selectedItem.equals("Wait")) {
			unit.moved();
			stage.setMenu(null);
			stage.returnToNeutral();
			new Idle(stage, stage.getPlayer()).startContext();
		} else if (selectedItem.equals("Attack") || selectedItem.equals("Heal")) {
			// TODO Select Target
		}
	}

	public void onChange() {
		updateZones();
	}

	public void updateZones() {
		stage.removeEntity(zone);
		if (menu.getSelection().equals("Attack")) {
			zone = new Zone(grid.getRange(new Node(unit.xcoord, unit.ycoord),
					unit.getTotalWepRange(false)), Zone.ATTACK_DARK);
			stage.addEntity(zone);
		} else if (menu.getSelection().equals("Heal")) {
			zone = new Zone(grid.getRange(new Node(unit.xcoord, unit.ycoord),
					unit.getTotalWepRange(true)), Zone.HEAL_DARK);
			stage.addEntity(zone);
		}
	}

	@Override
	public void onCancel() {
		grid.move(unit.xcoord, unit.ycoord, origX, origY);
		unit.xcoord = origX;
		unit.ycoord = origY;
		stage.setMenu(null);
		stage.removeEntity(zone);
		prev.startContext();
	}

	@Override
	public void onLeft() {
		// Nothing
	}

	@Override
	public void onRight() {
		// Nothing
	}

}
