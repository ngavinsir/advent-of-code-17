(ns advent-of-code-17.day2
  (:require [clojure.java.io :as io]))

(def input
  (->> (io/reader (io/resource "day2.txt"))
       (line-seq)
       (mapv (comp (partial mapv read-string) #(clojure.string/split % #"\s+")))))

;; Part 1
(defn checksum
  [input]
  (reduce + (map #(- (apply max %) (apply min %)) input)))

;; Part 2
(defn part-2
  [input]
  (apply + (map #(first (for [a %
                              b %
                              :when (and (not (= a b))
                                         (= 0 (rem a b)))]
                          (/ a b)))
                input)))

