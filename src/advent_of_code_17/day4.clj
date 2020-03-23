(ns advent-of-code-17.day4
  (:require [clojure.java.io :as io])
  (:require [clojure.data]))

(def input
  (->> (line-seq (io/reader (io/resource "day4.txt")))
       (mapv #(clojure.string/split % #"\s+"))))

(defn valid-1?
  [input]
  (boolean
    (reduce (fn [store word]
              (if (contains? store word)
                (reduced false)
                (conj store word)))
            #{}
            input)))

;; Better valid 1 that I found from other's people code
(defn better-valid-1?
  [input]
  (= (count (distinct input)) (count input)))

(defn how-many-valid?
  [f input]
  (count (filter (partial f) input)))

(defn part-1
  []
  (how-many-valid? valid-1? input))

(defn words-map
  [input]
  (reduce (fn [store letter]
            (assoc store
              letter
              (inc (get store letter 0))))
          {}
          input))

(defn anagram?
  [a b]
  (let [[only-in-a only-in-b] (clojure.data/diff (words-map a) (words-map b))]
    (and (= only-in-a nil)
         (= only-in-b nil))))

(defn valid-2?
  [input]
  (loop [a input]
    (if (empty? a)
      true
      (let [b (rest a)
            anagram (filter #(anagram? (first a) %) b)]
        (if (empty? anagram)
          (recur b)
          false)))))

(defn part-2
  []
  (how-many-valid? valid-2? input))
