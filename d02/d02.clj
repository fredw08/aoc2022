(ns aoc2022.d02
  (:require
    [clojure.string :as str]))

(defn get-rounds []
  (str/split (slurp "./data.txt") #"\n"))

(def score
  {"AA" (+ 3 1)
   "BB" (+ 3 2)
   "CC" (+ 3 3)
   "AB" (+ 2 6)
   "AC" (+ 3 0)
   "BA" (+ 1 0)
   "BC" (+ 3 6)
   "CA" (+ 1 6)
   "CB" (+ 2 0)})

(defn convert-hand [hand]
  (let [convert-map {"X" "A"
                     "Y" "B"
                     "Z" "C"}]
   (convert-map hand)))

(defn calc-score [round]
  (let [[left right] (str/split round #" ")
        right (convert-hand right)]
   (score (str left right))))

(defn q1 []
  ;; convert right-hand from XYZ to ABC, then each round map the score and sum it
  (->> (get-rounds)
       (map calc-score)
       (reduce +)))

(defn convert-hand-v2 [hand ref-hand]
  (let [convert-map-v2 {"X" {"A" "C"   ;; loss
                             "B" "A"
                             "C" "B"}
                        "Y" {"A" "A"   ;; draw
                             "B" "B"
                             "C" "C"}
                        "Z" {"A" "B"   ;; win
                             "B" "C"
                             "C" "A"}}]
    ((convert-map-v2 hand) ref-hand)))

(defn calc-score-v2 [round]
  (let [[left right] (str/split round #" ")
        right (convert-hand-v2 right left)]
   (score (str left right))))

(defn q2[]
  ;; X need lose; Y need draw; Z need win
  ;; convert right-hand from XYZ to ABC, then each round map the score and sum it
  (->> (get-rounds)
       (map calc-score-v2)
       (reduce +)))

(comment
  ({"A X" 1}"A X")
  (calc-score "A Y")
  (q1)
  (q2))
