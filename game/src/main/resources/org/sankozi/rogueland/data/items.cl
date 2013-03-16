(defn protection-value [prots] 
    (into {} (map (fn [entry] 
                    (let [[ikey ivalue] entry] [(name ikey) ivalue]))        
                (merge {:max-durability 1} prots))))
(defn effect-value [effect]
    (into {} (map (fn [entry] 
                    (let [[ikey ivalue] entry] [(name ikey) ivalue])) 
                effect)))     
(defn effects-value [effects]
    (into {} (map (fn [entry] 
                    (let [[ikey ivalue] entry] [(name ikey) (effect-value ivalue)]))        
                effects)))               
(defn item-entry [entry] (let [[ikey ivalue] entry] 
        [(name ikey) 
            (if (= ikey :protection) (protection-value ivalue)
            (if (= ikey :effects) (effects-value ivalue)
             ivalue))]))
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
    :desc "2 meter high stick stripped of bark"
    :types #{:staff}
    :durability 100
    :value 80
    :effects {
        :attack {
            :blunt 8}}}
:quarterstaff {
    :name "Quarterstaff"
    :desc "Staff made from hardwood cut into quarters."
    :types #{:staff}
    :durability 100
    :value 100
    :effects {
        :attack {
            :blunt 10}}}
:crude-short-sword {
  	:name "Crude short sword"
	  :types #{:sword}
    :durability 200
    :effects {
        :attack {
            :slashing 6}}}
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
	      :max-durability 200}}
:jack-of-plates {
    :name "Jack of plates"
    :types #{:worn-chest}
    :effects {
        :protection {
            :piercing-prot +2
            :slashing-prot +3
            :blunt-prot +4}}
    :protection {
        :piercing-prot 1
        :slashing-prot 2
        :blunt-prot 3
        :max-durability 200}}
:test-item {
    :name "Test item"
    :desc "Item used for testing"
    :types #{}
    :effects {
	      :protection {
	          :piercing-prot +1}}
    :durability 5}
})
		
