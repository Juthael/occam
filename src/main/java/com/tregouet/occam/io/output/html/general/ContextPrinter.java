package com.tregouet.occam.io.output.html.general;

import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.io.output.html.problem_space_page.ProblemSpacePagePrinter;

public class ContextPrinter {

	public static final ContextPrinter INSTANCE = new ContextPrinter();
	public static final String caption = "Context";
	private static final String[] alinea = ProblemSpacePagePrinter.alinea;
	private static final String nL = System.lineSeparator();

	private ContextPrinter() {
	}
	
	public String print(List<IContextObject> context, int a) {
		StringBuilder sB = new StringBuilder();
		sB.append(alinea[a] + "<section>" + nL)
				.append(alinea[a + 1] + "<header>" + nL)
					.append(alinea[a + 2] + "<h3> CONTEXT </h3>" + nL)
				.append(alinea[a + 1] + "</header>" + nL)
				.append(printArray(context, a + 1) + nL)
			.append(alinea[a] + "</section>" + nL);
		return sB.toString();
	}

	private static String printArray(List<IContextObject> context, int a) {
		String[] head = new String[context.size()];
		String[] optionalSubhead = (context.get(0).getName() == null ? null : new String[context.size()]);
		String[] body = new String[context.size()];
		for (int i = 0; i < context.size(); i++) {
			IContextObject obj = context.get(i);
			head[i] = Integer.toString(obj.iD());
			if (optionalSubhead != null)
				optionalSubhead[i] = obj.getName();
			body[i] = toString(obj);
		}
		return TablePrinter.INSTANCE.printStringTableWithOptionalSubHead(head, optionalSubhead, body, caption, a);
	}
	
	private static String toString(IContextObject object) {
		StringBuilder sB = new StringBuilder();
		for (IConstruct construct : object.getConstructs()) {
			sB.append(construct.toString() + "<br>");
		}
		return sB.toString();
	}

}
