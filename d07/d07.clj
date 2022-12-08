(ns aoc2022.d07
  (:require
    [aoc2022.util :as util]
    [clojure.string :as str]))

(defn get-data []
  (str/split (slurp "./d07/data.txt") #"\n"))

(defn fix-all-parent-dir [size-map]
  ;; before  {("/") 23352670, ("/" "a") 94269, ("/" "a" "e") 584, ("/" "d") 24933642}
  ;; after   {("/") 48381165, ("/" "a") 94853, ("/" "a" "e") 584, ("/" "d") 24933642}
  (reduce
    (fn [final-map [dirs size]]

      ;; handle each possible parent
      (loop [dir dirs
             fm  final-map]
        (if (empty? dir)
          fm
          (let [new-dir     (pop (vec dir))
                parent      (seq new-dir)
                parent-size (get-in fm [new-dir])
                new-fm      (if (and parent parent-size)
                              (assoc-in fm [parent] (+ size parent-size))
                              fm)]
            (recur (seq new-dir) new-fm)))))

    size-map
    size-map))

(defn get-single-level-size []
  (reduce
    (fn [[current-dir size-map] cmd]
      (let [size (last (re-matches #"(\d+).*" cmd))
            cd   (last (re-matches #"\$ cd (.*)" cmd))
            current-dir (if cd
                          (if (= ".." cd)
                            (pop current-dir)
                            (conj current-dir cd))
                          current-dir)

            current-dir-size      (or (get-in size-map [(seq current-dir)]) 0)
            new-size              (if size (util/parse-int size) 0)
            new-current-dir-size  (+ new-size current-dir-size)
            size-map              (assoc-in size-map [(seq current-dir)] new-current-dir-size)]

        [current-dir size-map]))

    [[] {}]
    (get-data)))

(comment
  ;; q1 (1367870)
  (->> (last (get-single-level-size))
       (fix-all-parent-dir)
       (filter #(<= (second %) 100000))
       (map #(second %))
       (reduce +))

  ;; q2 (549173)
  (let [size-map       (-> (last (get-single-level-size))
                           (fix-all-parent-dir))
        root-dir-size  (get-in size-map ['("/")])
        current-space  (- 70000000 root-dir-size)
        target-to-del  (- 30000000 current-space)]
    (->> (sort-by #(second %) size-map)
         (filter  (fn[x] (>= (second x) target-to-del)))
         first)))




