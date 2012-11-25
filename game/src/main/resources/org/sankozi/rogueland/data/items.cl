(defmacro item [code-name params] 
    `{~(name code-name) ~(zipmap 
        (for [x (keys params)] (name x)) ;keywords to string (for java interop)
        (vals params))}) ;map values
(defmacro items [items-map] 
    (merge (for [entry# (seq items-map)]
            `(item ~(nth entry# 0) ~(nth entry# 1)))))
{:sword {
	:name "Sword"
	:type #{:weapon}
    :durability 20}
:leather-armor {
	:name "Leather armor"
	:type #{:worn-chest}
    :durability 20}
}
		
