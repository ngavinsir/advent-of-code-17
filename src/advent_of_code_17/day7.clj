(ns advent-of-code-17.day7
  (:require [clojure.java.io :as io]))

(def input
  (->> (line-seq (io/reader (io/resource "day7.txt")))
       (map (partial re-seq #"(?:^[a-z]+)|(?<=\()\d+|(?<=->\s)\w+.*"))
       (reduce (fn [store [name, weight, rest]]
                 (let [children rest]
                   (assoc store
                     name
                     {:name     name
                      :weight   (read-string weight)
                      :children (if children
                                  (set (clojure.string/split children #",\s"))
                                  #{})})))
               {})))

(defn find-parent
  [store child]
  (if-let [parent (first (filter #(contains? (:children (val %)) child) store))]
    (key parent)
    nil))

;; Part 1
(defn find-root
  [input]
  (last (take-while some? (iterate (partial find-parent input)
                                   (first (keys input))))))

(defn get-children
  [node-name]
  (if-let [node (input node-name)]
    (if-let [children (not-empty (:children node))]
      (assoc node
        :children
        (map #(get-children %) children))
      node)
    nil))

(defn get-total-weight
  [node-name]
  (if-let [node (get-children node-name)]
    (reduce (fn [total node]
              (+ total (get-total-weight (:name node))))
            (:weight node)
            (:children node))))

(defn part-2
  [input]
  (let [root (get-children (find-root input))]
    (loop [target-score 0
           node root]
      (let [total-weights (mapv get-total-weight (map :name (:children node)))
            store (reduce #(assoc %1 (get-total-weight (:name %2)) %2)
                          {}
                          (:children node))]
        (if (apply = total-weights)
          (- target-score (reduce + total-weights))
          (let [[wrong-number right-number] (map first (sort-by val (frequencies total-weights)))]
            (recur right-number (store wrong-number))))))))







