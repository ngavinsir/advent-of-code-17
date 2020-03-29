(ns advent-of-code-17.day11
  (:require [clojure.java.io :as io]))

(def input
  (->> (slurp (io/resource "day11.txt"))
       (format "[%s]")
       (read-string)))

(def directions
  {'n [1 1]
   's [-1 -1]
   'nw [0 1]
   'ne [1 0]
   'se [0 -1]
   'sw [-1 0]})

(defn move-grid
  [pos dir]
  (mapv + pos (directions dir)))

(defn distance
  [pos]
  (if (or (every? pos? pos) (every? neg? pos))
    (apply max (mapv #(max % (- %)) pos))
    (apply +   (mapv #(max % (- %)) pos))))

(defn part-1
  [input]
  (distance (reduce move-grid [0 0] input)))

(defn part-2
  [input]
  (->> (reductions move-grid [0 0] input)
       (map distance)
       (reduce max)))
