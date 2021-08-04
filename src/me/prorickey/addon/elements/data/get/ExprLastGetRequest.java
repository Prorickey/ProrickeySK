package me.prorickey.addon.elements.data.get;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import me.prorickey.addon.elements.data.GetRequestData;
import me.prorickey.addon.elements.requests.EffGetRequest;

public class ExprLastGetRequest extends SimpleExpression<GetRequestData> {
	
	static {
	    Skript.registerExpression(ExprLastGetRequest.class, GetRequestData.class,
	        ExpressionType.SIMPLE, "[the] [(last|recent)] [recieved] [http[s]] get [req[uest]] [res[ponse]]");
	  }

	@Override
	public Class<? extends GetRequestData> getReturnType() {
		return GetRequestData.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}

	@Override
	  public String toString(@Nullable Event e, boolean debug) {
	    return "last received http get response";
	  }

	@Override
	protected GetRequestData[] get(Event e) {
		if(EffGetRequest.reqRes == null) {
			return new GetRequestData[0];
		}
		return new GetRequestData[] {EffGetRequest.reqRes};
	}
	
}
