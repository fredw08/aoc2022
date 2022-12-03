(ns aoc2022.d03
  (:require
    [clojure.string :as str]
    [clojure.set :as s]))

(defn get-rucksack []
  (str/split (slurp "./d03/data.txt") #"\n"))

(defn duplicated-at-compartments [rucksack]
  (let [comp-len (/ (count rucksack) 2)
        left     (set (take comp-len rucksack))
        right    (set (take-last comp-len rucksack))]
   (first (s/intersection left right))))

(defn score [chr]
  ;; a-z 1~26, A-Z 27~52
  (-> (map char (range (int \a) (inc (int \z))))          ;; a - z chars
      (concat (map char (range (int \A) (inc (int \Z))))) ;; A - Z chars
      (.indexOf chr)
      inc))

(defn q1 []
  (->> (get-rucksack)                        ;; get each line as rucksack
       (map #(duplicated-at-compartments %)) ;; find duplicate betweem compartments
       (map score)                           ;; find each duplicated char's score
       (reduce +)))

;; q2 (different group, but intersaction -> calc-score remarin the same
(defn get-rucksack-v2 [] ;; 3 line as a group
  (partition 3 (get-rucksack)))

(defn duplicated-at-compartments-v2 [rucksack]
  (let [grp-1    (set (first rucksack))
        grp-2    (set (second rucksack))
        grp-3    (set (last rucksack))]
   (first (s/intersection grp-1 grp-2 grp-3))))

(defn q2 []
  (->> (get-rucksack-v2)                         ;; get each line as rucksack
       (map #(duplicated-at-compartments-v2 %))  ;; find duplicate betweem compartments
       (map score)                               ;; find each duplicated char's score
       (reduce +)))

(comment
  (q1)
  (q2))
