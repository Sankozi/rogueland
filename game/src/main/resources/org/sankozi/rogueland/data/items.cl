(defn protection-value [prots] 
    (into {} (map (fn [entry] 
            (let [[ikey ivalue] entry] [(name ikey) ivalue]))        
        (merge {:max-durability 1} prots))))
(defn item-entry [entry] (let [[ikey ivalue] entry] 
        [(name ikey) (if (= ikey :protection) (protection-value ivalue) ivalue)]))
(defn item [code-name params] ;keywords to string (for java interop)
    {(name code-name) 
     (into {} 
         (map item-entry (merge {:protection {}} params)))}) ;add empty protection element
(defmacro items [items-map] 
    `(merge ~@(for [entry# (seq items-map)]
            (item (nth entry# 0) (nth entry# 1)))))
(items {
:simple-staff {
    :name "Simple staff"
    :desc "2 meter high stick stripped of bark
           5 blunt damage"
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
	        :blunt-prot 1
	        :max-durability 200}
    }
:test-item {
    :name "Test item"
    :types #{}
    :effects {
	    :protection {
	        :piercing-prot +1}}
    :durability 5}
})
		
