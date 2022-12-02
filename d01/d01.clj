(ns aoc2022.d01
  (:require
    [clojure.string :as str]))
    ;; [clojure.edn :as edn]))

(defn total-calories-for-each-elf []
  (let [data (slurp "./data.txt")]
    (->> (str/split data #"\n\n")
         (map (fn[x]
                (->> (str/split x #"\n") ;; each elf split by \n
                     (map read-string)   ;; each string convert to int
                     (reduce +)))))))    ;; sum of each int(calories)

;; top 1 elf
(defn q1 []
  (->> (total-calories-for-each-elf)
       (apply max)))

;; top 3 elf
(defn q2 []
  (->> (total-calories-for-each-elf)
      (sort)
      (take-last 3)
      (reduce +)))

(comment
  (prn "testing")
  (read-string "10")
  (take-last 2 (sort '(1 2 3 4)))
  (reduce + '(1 2 3))

  (total-calories-for-each-elf)
  (q1)
  (q2))
