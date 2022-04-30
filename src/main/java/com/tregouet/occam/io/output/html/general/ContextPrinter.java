package com.tregouet.occam.io.output.html.general;

import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public class ContextPrinter {

	public static final ContextPrinter INSTANCE = new ContextPrinter();
	public static final String caption = "Context";

	private ContextPrinter() {
	}

	public String print(List<IContextObject> context, String alinea) {
		String[] head = new String[context.size()];
		String[] optionalSubhead = (context.get(0).getName() == null ? null : new String[context.size()]);
		String[] body = new String[context.size()];
		for (int i = 0; i < context.size(); i++) {
			IContextObject obj = context.get(i);
			head[i] = Integer.toString(obj.getID());
			if (optionalSubhead != null)
				optionalSubhead[i] = obj.getName();
			body[i] = toString(obj);
		}
		return TablePrinter.INSTANCE.printStringTableWithOptionalSubHead(head, optionalSubhead, body, caption, alinea);
	}
	
	private String toString(IContextObject object) {
		StringBuilder sB = new StringBuilder();
		for (IConstruct construct : object.getConstructs()) {
			sB.append(construct.toString() + "<br>");
		}
		return sB.toString();
	}

}
