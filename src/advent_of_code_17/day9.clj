(ns advent-of-code-17.day9
  (:require [clojure.java.io :as io]))

(def input
  (->> (slurp (io/resource "day9.txt"))
       (map identity)))

(defn analyze-char
  [{:keys [group garbage score skip]
    :as   store
    :or   {group 0 score 0}}
   character]
  (if skip
    (assoc store :skip false)
    (-> (if garbage
          (case character
            \> (assoc store :garbage false)
            \! store
            (update store :garbage-score (fnil inc 0)))
          (case character
            \{ (update store :group (fnil inc 0))
            \} (-> (assoc store :score (+ score group))
                   (update :group dec))
            \< (assoc store :garbage true)
            store))
        (#(case character
            \! (assoc % :skip true)
            %)))))

(defn part-1
  [input]
  (:score (reduce analyze-char {} input)))

(defn part-2
  [input]
  (:garbage-score (reduce analyze-char {} input)))