package com.tregouet.occam.io.output.html.pages;

import com.google.common.base.Supplier;
import com.tregouet.occam.io.output.html.models.FootPrinter;
import com.tregouet.occam.io.output.html.models.HeaderPrinter;
import com.tregouet.occam.io.output.html.models.MenuPrinter;
import com.tregouet.occam.io.output.html.models.MenuType;

public class StartPagePrinter implements Supplier<String> {

	public static final StartPagePrinter INSTANCE = new StartPagePrinter();

	private static final String nL = System.lineSeparator();

	private StartPagePrinter() {
	}

	@Override
	public String get() {
		StringBuilder sB = new StringBuilder();
		sB.append(HeaderPrinter.INSTANCE.get() + nL);
		sB.append(MenuPrinter.INSTANCE.print(MenuType.START_MENU, 0) + nL);
		sB.append(FootPrinter.INSTANCE.get() + nL);
		return sB.toString();
	}

}
