package org.sankozi.rogueland.data;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Object that loads games resources - strings, game data, and others
 * @author sankozi
 */
public final class DataLoader {

	Collection<String> getScriptNames(){
		List<String> ret = new ArrayList<>();
		Iterables.addAll(ret, 
				Splitter.on("\n")
					.trimResults()
					.omitEmptyStrings()
					.split(loadResource("scripts.list")));
		return ret;
	}

	String loadResource(String name) {
		try(InputStream is = getClass().getResourceAsStream(name)){
			return CharStreams.toString(new InputStreamReader(is, "UTF-8"));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	Object evaluateClResource(String name){
		return clojure.lang.Compiler.load(new StringReader(loadResource(name)));
	}
}
