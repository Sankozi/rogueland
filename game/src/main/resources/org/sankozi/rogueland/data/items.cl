(defn item [code-name params] 
    {(name code-name) (zipmap 
        (for [x (keys params)] (name x)) ;keywords to string (for java interop)
        (vals params))}) ;map values
(defmacro items [items-map] 
    `(merge ~@(for [entry# (seq items-map)]
            (item (nth entry# 0) (nth entry# 1)))))
(items {
:simple-quarterstaff {
    :name "Simple quarterstaff"
    :types #{:staff}
    :durability 100}
:sword {
	:name "Sword"
	:types #{:weapon}
    :durability 200}
:leather-armor {
	:name "Leather armor"
	:types #{:worn-chest}
	:effects {
	    :protection {
	        :piercing-prot +1
	        :slashing-prot +2
	        :blunt-prot +3}}
	:protection {
	        :piercing-prot 0
	        :slashing-prot 0
	        :blunt-prot 1}
    :durability 200}
:test-item {
    :name "Test item"
    :types #{}
    :durability 5}
})
		
