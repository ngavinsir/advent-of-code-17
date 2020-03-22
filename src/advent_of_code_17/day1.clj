(ns advent-of-code-17.day1
  (:require [clojure.java.io :as io]))

(def input
  (mapv
    (comp read-string str)
    (slurp (io/resource "day1.txt"))))

;; Part 1
(defn sum
  [input]
  (->> (partition 2 1 (conj (vec input) (first input)))
       (filter (partial apply =))
       (map first)
       (apply +)))

;; Part 2
(defn sum2
  [input]
  (->> (map vector
            input
            (drop (/ (count input) 2) (cycle input)))
       (filter (partial apply =))
       (map first)
       (apply +)))
