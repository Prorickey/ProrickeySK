package me.prorickey.addon.elements.data.get;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;

import me.prorickey.addon.elements.data.GetRequestData;

public class ExprLastGetRequestBody extends SimplePropertyExpression<GetRequestData, String> {
	
	static {
	    PropertyExpression.register(ExprLastGetRequestBody.class, String.class, "[response] bod(y|ies)", "getrequestdatas");
	  }
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String convert(GetRequestData a) {
		return a.getBody();
	}

	@Override
	protected String getPropertyName() {
		return "body";
	}

}
