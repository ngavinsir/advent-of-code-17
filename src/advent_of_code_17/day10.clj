(ns advent-of-code-17.day10
  (:require [clojure.java.io :as io]))

(def input
  (->> (slurp (io/resource "day10.txt"))
       (#(str "[" % "]"))
       (read-string)))

(defn run-hash
  [list pos length]
  (apply (partial assoc list)
         (vec (mapcat vector
                      (map #(mod % (count list)) (range pos (+ pos length)))
                      (reverse (take length (drop pos (cycle list))))))))

(defn perform-hash
  [lengths list-size]
  (:list (reduce (fn [{:keys [skip list pos]} length]
                   {:skip (inc (or skip 0))
                    :list (if (= length 0)
                            list
                            (run-hash list pos length))
                    :pos  (mod (+ pos length skip) (count list))})
                 {:skip 0
                  :list (vec (range list-size))
                  :pos  0}
                 lengths)))

(defn part-1
  [lengths list-size]
  (apply * (take 2 (perform-hash lengths list-size))))

(def input-2
  (->> (slurp (io/resource "day10.txt"))
       (clojure.string/trim)
       (char-array)
       (chars)
       (mapv int)
       (#(apply conj % [17 31 73 47 23]))))

(defn part-2
  [lengths list-size round]
  (->> (perform-hash (flatten (repeat round lengths)) list-size)
       (partition 16)
       (map (partial reduce bit-xor))
       (map (partial format "%02x"))
       (apply str)))