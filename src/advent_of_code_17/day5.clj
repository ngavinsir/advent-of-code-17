(ns advent-of-code-17.day5
  (:require [clojure.java.io :as io]))

(def input
  (->> (line-seq (io/reader (io/resource "day5.txt")))
       (mapv read-string)))

(defn part-1
  []
  (loop [maze input
         pos 0
         step 0]
    (let [jump (get maze pos)]
      (if (nil? jump)
        step
        (recur (assoc maze pos (inc jump))
               (+ pos jump)
               (inc step))))))

(defn part-2
  []
  (loop [maze input
         pos 0
         step 0]
    (let [jump (get maze pos)]
      (if (nil? jump)
        step
        (recur (assoc maze pos (+ jump (if (>= jump 3) -1 1)))
               (+ pos jump)
               (inc step))))))