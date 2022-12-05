(ns aoc2022.d05
  (:require
    [clojure.string :as str]))

(defn get-data []
  (slurp "./d05/data.txt"))

(defn get-crates []
  (-> (first (str/split (get-data) #"\n\n")) ;; first part of \n\n
      (str/split #"\n")))                    ;; break by line

(defn get-moves []
  (-> (last (str/split (get-data) #"\n\n"))  ;; last part of \n\n
      (str/split #"\n")))                    ;; break by line

;; transpose crate visual pattern into readable format
(defn transpose [m]
  (apply mapv vector m))

(defn remove-space-from-col [col]
  (remove #(= \space %) col))

(defn refine-crates []
  (->> (get-crates)
       transpose
       (filter #(not= \space (last %)))   ;; form [[\space \space ... \1] [... \2] ...]
       (mapv #(remove-space-from-col %)))) ;; form ((... \1) (... \2) (... \3) ...)

(defn refine-moves []
  (->> (get-moves)
       (map #(-> (re-seq #"\d+" %)))
       (mapv #(map read-string %))))

(defn perform-move [crates move need-reverse]
  (let [[times from to] move
        from-crate  (nth crates (dec from))
        to-crate    (nth crates (dec to))
        stacks      (if need-reverse
                      (reverse (take times from-crate))
                      (take times from-crate))]
    (-> crates
        (assoc (dec from) (take-last (- (count from-crate) times) from-crate))
        (assoc (dec to)   (concat stacks to-crate)))))

(defn perform-instructions [need-reverse]
  (let [moves (refine-moves)
        crates (refine-crates)]
    (loop [remain moves
           final crates]
      (if (empty? remain)
        final
        (let [[move & remaining] remain]
          (recur remaining
                 (perform-move final move need-reverse)))))))

(defn q1 []
  (map first (perform-instructions true)))

(defn q2 []
  (map first (perform-instructions false)))

(comment
  (apply str (q1))  ;; "BWNCQRMDB"
  (apply str (q2))) ;; "NHWZCBNBF"

  ;; (refine-crates)
  ;; (refine-moves)
  ;; (concat [\X] (first (refine-crates)))
  ;; (reverse (take 2 (nth (refine-crates) 1)))

