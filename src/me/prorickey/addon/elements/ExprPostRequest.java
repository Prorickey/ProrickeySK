package me.prorickey.addon.elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprPostRequest extends SimpleExpression<String> {
	
	static {
        Skript.registerExpression(ExprPostRequest.class, String.class, ExpressionType.COMBINED, "[send] [http[s]] post [req[uest]] to %string% with bod[y] %string% [with header[s] %-strings%]");
    }
	
	private static final Pattern HEADER = Pattern.compile("(.*?):(.+)");
    private Expression<String> url;
    private Expression<String> headers;
    private Expression<String> body;
 
    /*@Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }*/
 
    @Override
    public boolean isSingle() {
        return true;
    }
 
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        url = (Expression<String>) exprs[0];
        body = (Expression<String>) exprs[1];
        headers = (Expression<String>) exprs[2];
        return true;
    }
 
    @Override
    @Nullable
    protected String[] get(Event event) {
        String response = null;
		try {
			response = postRequest(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
        if (response != null) {
            return new String[] {response};
        }
        return null;
    }
    
    public String postRequest(Event e) throws IOException {
		URL obj = new URL(this.url.getSingle(e));
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("POST");
		
		String headers[] = null;
	    if (this.headers != null) {
	      headers = this.headers.getAll(e);
	    }
	    
	    for (String header : headers) {
	        Matcher headerMatcher = HEADER.matcher(header);
	        if (headerMatcher.matches()) {
	          con.setRequestProperty(headerMatcher.group(1).trim(),
	              headerMatcher.group(2).trim());
	        } else {
	          Skript.warning(String.format("Header entered incorrectly for request to %s: \"%s\"", url, header));
	        }
	      }
		
		con.setDoOutput(true);
		
		byte[] out = this.body.getSingle(e).getBytes(StandardCharsets.UTF_8);
		int length = out.length;
		con.setFixedLengthStreamingMode(length);
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.connect();
		
		try(OutputStream os = con.getOutputStream()) {
		    os.write(out);
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String output;
		StringBuffer response = new StringBuffer();
		
		while ((output = in.readLine()) != null) {
		   response.append(output);
		}
		  
		Bukkit.getLogger().info(String.format("Response code %s", con.getResponseCode()));
		in.close();
		
        return response.toString();
    }

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "post request to";
	}

	@Override
	public java.lang.Class<? extends String> getReturnType() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
