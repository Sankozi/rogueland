(defn generator [code-name params] 
    {(name code-name) (zipmap 
        (for [x (keys params)] (name x)) ;keywords to string (for java interop)
        (vals params))}) ;map values
(defmacro generators [items-map] 
    `(merge ~@(for [entry# (seq items-map)]
            (generator (nth entry# 0) (nth entry# 1)))))
(generators {
    :war-starter-pack {
        :items (:simple-quarterstaff)}
})
