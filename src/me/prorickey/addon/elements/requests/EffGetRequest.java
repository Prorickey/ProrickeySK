package me.prorickey.addon.elements.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.prorickey.addon.elements.data.GetRequestData;

public class EffGetRequest extends Effect {
	
	static {
        Skript.registerEffect(EffGetRequest.class, "send [a[n]] [http[s]] get [web] req[uest] to %string% [with header[s] %-string%]");
    }
	
	public static GetRequestData reqRes;
	private static final Pattern HEADER = Pattern.compile("(.*?):(.+)");
	private Expression<String> url;
    private Expression<String> headers;
	
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.url = (Expression<String>) expressions[0];
        this.headers = (Expression<String>) expressions[1];
        return true;
    }

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if(this.headers != null) {
			return "send get request to string url: " + url.toString() + " with string headers: " + headers.toString();
		}
		return "send get request to: " + url.toString();
	}

	@Override
	protected void execute(Event event) {
		try {
			getRequest(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public GetRequestData getRequest(Event e) throws IOException {
		URL obj = new URL(this.url.getSingle(e));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("POST");
		
		String headers[] = null;
	    if (this.headers != null) {
	    	headers = this.headers.getAll(e);
	    }
	    
	    if(headers != null) {
		    for (String header : headers) {
		        Matcher headerMatcher = HEADER.matcher(header);
		        if (headerMatcher.matches()) {
		          con.setRequestProperty(headerMatcher.group(1).trim(),
		              headerMatcher.group(2).trim());
		        } else {
		          Skript.warning(String.format("Header entered incorrectly for request to %s: \"%s\"", url, header));
		        }
		      }
	    }
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();
		
		while ((output = in.readLine()) != null) {
			response.append(output);
		}
		  
		Bukkit.getLogger().info(String.format("Response code %s", con.getResponseCode()));
		Bukkit.getLogger().info(String.format("Response body %s", response));
		in.close();
		
        return new GetRequestData(con.getResponseCode(), response.toString());
    }
	
}
