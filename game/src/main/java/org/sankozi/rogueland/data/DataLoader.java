package org.sankozi.rogueland.data;

import clojure.lang.Named;
import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.sankozi.rogueland.model.*;
import org.sankozi.rogueland.model.effect.DamageEffect;
import org.sankozi.rogueland.model.effect.Effect;

import static org.sankozi.rogueland.data.LoaderUtils.*;
import org.sankozi.rogueland.model.effect.ParamChangeEffect;

/**
 * Object that loads games resources - strings, game data, and others
 * @author sankozi
 */
@SuppressWarnings("unchecked")
public final class DataLoader {

    private final LoadingCache<String, Object> evaluatedClResources = 
            CacheBuilder.newBuilder()
                        .concurrencyLevel(1)
                        .expireAfterAccess(5, TimeUnit.MINUTES)
                        .build(new CacheLoader<String, Object>(){
                            public Object load(String name) throws Exception {
                                return clojure.lang.Compiler.load(new StringReader(loadResource(name)));
                            }
                        });

    private Map<String, ItemTemplate> baseItemTemplates;

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
		return evaluatedClResources.getUnchecked(name);
	}

	Map<String, ItemTemplate> loadItemTemplates(){
        if(baseItemTemplates == null) {
            Map<String, ?> clMap = (Map) evaluateClResource("items.cl");
            Map<String, ItemTemplate> ret = Maps.newHashMapWithExpectedSize(clMap.size());
            for(Map.Entry<String, ?> entry : clMap.entrySet()){
                ret.put(entry.getKey(), buildItemTemplate(entry.getKey(), (Map)entry.getValue()));
            }
            baseItemTemplates = ret;
        }
		return baseItemTemplates;
	}

    public ItemTemplate getItemTemplate(String name){
        return loadItemTemplates().get(name);
    }

	private static ItemTemplate buildItemTemplate(String name, Map map) {
        ItemTemplateBuilder builder = new ItemTemplateBuilder()
                .setName(map.get("name").toString())
                .setDescription(Objects.toString(map.get("desc"), ""))
                .setParams(toFloatMap((Map) map.get("protection"), Destroyable.Param.class));

        builder.setTypes(parseItemTypes(map));

        Map effects = (Map) map.get("effects");
        if(effects != null){
            for(Map.Entry entry : (Set<Map.Entry>)effects.entrySet()){
                parseEffect(entry.getKey().toString(), (Map) entry.getValue(), builder);
            }
        }

        return builder.createItemTemplate();
	}

    private static EnumSet<ItemType> parseItemTypes(Map map) {
        EnumSet<ItemType> types = EnumSet.noneOf(ItemType.class);
        Set<Named> namedTypes = (Set) map.get("types");
        for(Named nType : namedTypes){
            types.add(
                    ItemType.valueOf(
                    CaseFormat.LOWER_HYPHEN.to(
                    CaseFormat.UPPER_UNDERSCORE, nType.getName())));
        }
        return types;
    }

    private static void parseEffect(String name, Map<String, ?> params, ItemTemplateBuilder builder){
        switch(name) {
            case "protection":
                builder.setUsedEffect(new ParamChangeEffect(name, toFloatMap(params, Destroyable.Param.class)));
                break;
            case "attack":
                builder.setWeaponEffect(DamageEffect.multiDamageEffect(toIntMap(params, Damage.Type.class)));
                break;
            default:
                throw new RuntimeException("unknown effect '" + name + "'");
        }
    }
}
