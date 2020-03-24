(ns advent-of-code-17.day6
  (:require [clojure.java.io :as io]))

(def input
  (map read-string
       (clojure.string/split
         (slurp (io/resource "day6.txt"))
         #"\s+")))

(defn redistribute
  [input]
  (let [bank-count (count input)
        max-val (apply max input)
        max-pos (.indexOf input max-val)]
    (reduce #(update %1 (mod %2 bank-count) inc)
            (assoc input max-pos 0)
            (range (inc max-pos)
                   (+ (inc max-pos) max-val)))))

(defn part-1
  [input]
  (count (reduce #(if (%1 %2)
                    (reduced %1)
                    (conj %1 %2))
                 #{}
                 (iterate redistribute (vec input)))))

(defn part-2
  [input]
  (loop [[first & rest] (iterate redistribute (vec input))
         store {}
         step 0]
    (let [value (get store first)]
      (if value
        (- step value)
        (recur rest (assoc store first step) (inc step))))))
