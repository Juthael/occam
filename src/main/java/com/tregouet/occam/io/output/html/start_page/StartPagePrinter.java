package com.tregouet.occam.io.output.html.start_page;

import com.google.common.base.Supplier;
import com.tregouet.occam.io.output.html.general.FootPrinter;
import com.tregouet.occam.io.output.html.general.HeaderPrinter;
import com.tregouet.occam.io.output.html.general.MenuPrinter;
import com.tregouet.occam.io.output.html.general.MenuType;

public class StartPagePrinter implements Supplier<String> {

	public static final StartPagePrinter INSTANCE = new StartPagePrinter();

	private static final String nL = System.lineSeparator();

	private StartPagePrinter() {
	}

	@Override
	public String get() {
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get() + nL);
		sB.append(MenuPrinter.INSTANCE.print(MenuType.START_MENU, "") + nL);
		sB.append(FootPrinter.INSTANCE.get() + nL);
		return sB.toString();
	}

}
