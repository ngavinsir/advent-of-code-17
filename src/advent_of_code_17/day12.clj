(ns advent-of-code-17.day12
  (:require [clojure.java.io :as io]))

(def input
  (->> (io/reader (io/resource "day12.txt"))
       (line-seq)
       (map (partial format "[%s]"))
       (map read-string)))

(def map-conn
  (reduce (fn [map [id _ & conns]]
            (assoc map id conns))
          {}
          input))

(defn get-group
  [entry]
  (loop [programs [entry]
         group #{}]
    (if (empty? programs)
      group
      (if (contains? group (first programs))
        (recur (rest programs) group)
        (recur (into (rest programs) (map-conn (first programs)))
               (conj group (first programs)))))))

(defn part-1
  []
  (count (get-group 0)))

(defn part-2
  []
  (loop [total-group 0
         visited #{}
         [program & rest :as programs] (keys map-conn)]
    (if (empty? programs)
      total-group
      (if (contains? visited program)
        (recur total-group visited rest)
        (recur (inc total-group) (into visited (get-group program)) rest)))))

