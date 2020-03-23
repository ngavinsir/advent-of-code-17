(ns advent-of-code-17.day3)

(def directions [[1 0] [0 1] [-1 0] [0 -1]])

(def direction-list
  (mapcat
    (fn [[i [dir1 dir2]]]
      (vec (concat (repeat i dir1) (repeat i dir2))))
    (map vector (rest (range)) (partition 2 (cycle directions)))))

(def point-list
  (reductions #(mapv + %1 %2) [0 0] direction-list))

(defn get-point
  [input]
  (nth point-list (dec input)))

(defn part-1
  [input]
  (let [[x y] (get-point input)]
    (+ (Math/abs ^long x) (Math/abs ^long y))))

(def all-directions
  (into [[-1 -1] [-1 1] [1 -1] [1 1]]
        directions))

(defn all-neighbor-positions [pos]
  (mapv #(mapv + pos %) all-directions))

(def value-from-neighbor-list
  (reductions (fn [{:keys [neighbor]} point]
                (let [neighbor-value (reduce + (vals (select-keys neighbor (all-neighbor-positions point))))
                      value (max neighbor-value 1)]
                  {:neighbor (assoc neighbor point value)
                   :value value}))
              {:neighbor {[0 0] 1}
               :value 1}
              (rest point-list)))

(defn part-2
  []
  (first
    (filter #(> % 289326)
            (map :value value-from-neighbor-list))))