(ns advent-of-code-17.day8
  (:require [clojure.java.io :as io]))

(def input
  (->> (io/reader (io/resource "day8.txt"))
       (line-seq)
       (map #(str "[" % "]"))
       (map read-string)))

(def fn-map
  {'== =
   '!= not=
   '> >
   '>= >=
   '<= <=
   '< <
   'inc +
   'dec -})

(defn run-instruction
  [store [reg cmd arg _ reg_cond cond arg_cond]]
  (if ((fn-map cond) (get store reg_cond 0) arg_cond)
    (let [new-value ((fn-map cmd) (get store reg 0) arg)]
      (-> (assoc store reg new-value)
          (assoc :highest (max (:highest store 0) new-value))))
    store))

(defn part-1
  [input]
  (let [result (reduce run-instruction {} input)]
    (apply max (vals (filter #(not= (first %) :highest) result)))))

(defn part-2
  [input]
  (let [result (reduce run-instruction {} input)]
    (:highest result)))
