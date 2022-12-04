(ns aoc2022.d04
  (:require
    [aoc2022.util :as util]
    [clojure.string :as str]
    [clojure.set :as cset]))

(defn get-assignments []
  (str/split (slurp "./d04/data.txt") #"\n"))

(defn convert-to-range [range-str]
  (let [[from to] (str/split range-str #"-")]
    (map int
         (range (util/parse-int from) (inc (util/parse-int to))))))

;; fully-overlap (q1)
(defn overlap-check [assignment]
  (let [[left right] (str/split assignment #",")
        left         (convert-to-range left)
        right        (convert-to-range right)]
    (>=
      (count (cset/intersection (set left) (set right))) ;; range got overdap
      (min   (count left) (count right)))))              ;; min length of left/right

;; any-overlap (q2)
(defn overlap-check-v2 [assignment]
  (let [[left right] (str/split assignment #",")
        left         (convert-to-range left)
        right        (convert-to-range right)]
    (>=
      (count (cset/intersection (set left) (set right))) ;; range got overdap
      1)))                                               ;; any overlap

(comment
  ;q1
  (->> (get-assignments)
       (map overlap-check)
       (filter identity)
       (count))

  ;q2
  (->> (get-assignments)
       (map overlap-check-v2)
       (filter identity)
       (count)))
