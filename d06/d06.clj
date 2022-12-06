(ns aoc2022.d06
  (:require
    [clojure.string :as str]))

(defn get-data []
  (str/split (slurp "./d06/data.txt") #""))

(defn is-marker? [[unique new-col no-of-uniq] each-char]
  (let [last-4-char (take-last no-of-uniq new-col)
        unique?     (and
                      (>= (count new-col) no-of-uniq)
                      (=  (count last-4-char) (count (set last-4-char))))]
   [(conj unique unique?)        ;; is unique list
    (conj new-col each-char)     ;; char accumulated list
    no-of-uniq]))

(defn find-first-marker [no-of-uniq]
  (->> (get-data)
       (reduce is-marker? [[] [] no-of-uniq])
       first
       (reduce (fn [new-col unique]
                 (if unique
                   (reduced new-col)
                   (conj new-col unique))) [])

       (count)))

(comment
  (find-first-marker 4)   ;; q1
  (find-first-marker 14)) ;; q2
