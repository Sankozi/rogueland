(defn p-class-entry [entry]
  (println entry)
  (let [[ikey ivalue] entry]
   [(name ikey)
    ; ivalue]))
    (if (= ikey :items) (into [] (map name ivalue))
      ivalue)]))
(defn p-class [code-name params]
    {(name code-name)
     (into {}
        (map p-class-entry (seq params)))})
     ;(zipmap
     ;   (for [x (keys params)] (name x)) ;keywords to string (for java interop)
     ;   (vals params))}) ;map values
(defmacro classes [items-map]
    `(merge ~@(for [entry# (seq items-map)]
            (p-class (nth entry# 0) (nth entry# 1)))))
(classes {
    :brawler {
        :items [:quarterstaff, :jack-of-plates]}
})

