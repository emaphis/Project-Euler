(ns euler-clj.sequence
  (:require [midje.sweet :refer :all]))

;; Sequences seem to play a big role in solving Project Euler problems
;; so it seems wise to experiment with them. This file will also serve
;; as a repository of knowledge and examples.
;; Mosly derived from Aphyr's wonderful totorial:
;; https://aphyr.com/posts/304-clojure-from-the-ground-up-sequences

(def numbers [1 2 3])

(fact "an identity relating 'cons' 'first' and 'numbers'"
  (cons (first [1 2 3]) (rest [1 2 3])) => [1 2 3])

;; recursion:
(defn inc-first [nums]
  "return a sequence with the first element incremented"
  (if (empty? nums) '()
    (cons (inc (first nums))
          (rest nums))))

(fact "'inc-first' increments first element of a sequence"
  (inc-first []) => []
  (inc-first [1]) => [2]
  (inc-first [1 2 3 4]) => [2 2 3 4])

(defn inc-all [nums]
  "increment all of the elements of a numerical sequence"
  (if (empty? nums) '()
      (cons (inc (first nums))
            (inc-all (rest nums)))))

(fact "'inc-all' increments all elements of a sequence"
  (inc-all []) => []
  (inc-all [1]) => [2]
  (inc-all [1 2 3 4]) => [2 3 4 5])

;; now generalize this:
(defn transform-all [fun xs]
  "transforms all of the elements of a sequence of 'x' with a function of type 'x'"
  (if (empty? xs) '()
      (cons (fun (first xs))
            (transform-all fun (rest xs)))))

(fact "'transform-all' transfrom all elements of a sequence"
  (transform-all inc []) => []
  (transform-all inc [1]) => [2]
  (transform-all inc [1 2 3 4]) => [2 3 4 5]
  (transform-all keyword ["bell" "hooks"]) => [:bell :hooks])

;; building sequences:

(defn expand [fun x count]
  "expand a single value into a sequence of values"
  (if (pos? count)
    (cons x (expand fun (fun x) (dec count)))))

(fact "expand a single value into a sequence of values"
      (expand inc 0 10) => '(0 1 2 3 4 5 6 7 8 9))
(fact "clojure includes a similar function 'iterate'"
  (take 10 (iterate inc 0)) => '(0 1 2 3 4 5 6 7 8 9)
  (take 5 (iterate (fn [x] (str x "o")) "y")) => '("y" "yo" "yoo" "yooo" "yoooo"))

(fact "the ackerman? function"
  (take 10 (iterate (fn [x] (if (odd? x) (+ 1 x) (/ x 2))) 10))
  => '(10 5 6 3 4 2 1 2 1 2))

;; 
(fact "'repeat' for sequences of the same item."
  (take 5 (repeat :hi)) => '(:hi :hi :hi :hi :hi)
  (repeat 5 3.14) => '(3.14 3.14 3.14 3.14 3.14))

;; 'repeatedly' calls a function 'f' to generate an infinite sequence of values"
;(take 5 (repeatedly rand))

(fact "'range' generates sequence of numbers between two points"
  (range 5) => '(0 1 2 3 4)
  (range 2 10) '(2 3 4 5 6 7 8 9)
  (range 0 30 5) => '(0 5 10 15 20 25))

(fact "'cycle' repeats a sequence forever"
  (take 10 (cycle [1 2 3])) => '(1 2 3 1 2 3 1 2 3 1))


;; Transforming sequences

(fact "'map' applies a function to each element"
  (map (fn [n vehicle] (str "I've got " n " " vehicle "s"))
       [0 200 9]
       ["car" "train" "kiteboard"])
  => '("I've got 0 cars" "I've got 200 trains" "I've got 9 kiteboards"))

(fact "'map' can fold together corresponding elements of sequences"
  (map + [1 2 3]
         [4 5 6]
         [7 8 9])
  => '(12 15 18))

(fact "'map' can combine infinite and finites sequences"
      (map (fn [index element] (str index ". " element))
           (iterate inc 0)
           ["erlang" "ruby" "haskell"])
      => '("0. erlang" "1. ruby" "2. haskell"))

(fact "indexing elements"
      (map-indexed (fn [index element] (str index ". " element))
                   ["erlang" "ruby" "haskell"])
      => '("0. erlang" "1. ruby" "2. haskell"))

(fact "tacking one sequence on another"
  (concat [1 2 3] [:a :b :c] [4 5 6])
  => '(1 2 3 :a :b :c 4 5 6))

(fact "inter-mixing sequences"
      (interleave [:a :b :c] [1 2 3])
      => '(:a 1 :b 2 :c 3))

(fact "insert an element between items in a sequence"
  (interpose :and [1 2 3 4])
  => '(1 :and 2 :and 3 :and 4))

(fact "reverse a sequence"
  (reverse [1 2 3]) => '(3 2 1)
  (reverse "woolf") => '(\f \l \o \o \w))
(fact "convert back to a string"
  (apply str (reverse "woolf")) => "floow")
(fact "but break up strings int sequence of chars"
  (seq "sato") => '(\s \a \t \o))

;; randome shuffle
;; (shuffle [1 2 3 4])


;; Sequences:
(fact "sequenses with 'range'"
  (range 10) => '(0 1 2 3 4 5 6 7 8 9)
  (take 3 (range 10)) => '(0 1 2)
  (drop 3 (range 10)) => '(3 4 5 6 7 8 9))

(fact "slicing end of range"
  (take-last 3 (range 10)) => '(7 8 9)
  (drop-last 3 (range 10)) => '(0 1 2 3 4 5 6))

(fact "function can make the decision "
  (take-while pos? [3 2 1 0 -1 -2 10]) => '(3 2 1)
  (drop-while neg? [-3 -2 -1 0 1 2 3]) => '(0 1 2 3))

(fact "cutting a sequence in two"
  (split-at 4 (range 10)) => ['(0 1 2 3) '(4 5 6 7 8 9)]
  (split-with number? [1 2 3 :mark 4 5 6 :mark 7])
  => ['(1 2 3) '(:mark 4 5 6 :mark 7)])

(fact "finding particular elements in a sequence"
  (filter pos? [1 5 -4 -7 3 0]) => '(1 5 3)
  (remove pos? [1 5 -4 -7 3 0]) => '(-4 -7 0))

(fact "group a sequence into chunks"
  (partition 2 [:a 1 :b 2 :c 3]) => '((:a 1) (:b 2) (:c 3))
  (partition-by neg? [1 2 3 2 1 -1 -2 -3 -2 -1 1 2])
  => '((1 2 3 2 1) (-1 -2 -3 -2 -1) (1 2)))

;;;;;;;;;;;;;;;;;;;;;;;;
;; colapsing sequences

(fact "counting element appearences"
  (frequencies [:meow :mrrrow :meow :meow]) => {:meow 3, :mrrrow 1})

;;;;;;;;;;;;;;;;;;;;;;;;
;; reduce

(fact "'reduce' is a general purpose utility"
  (reduce + [1 2 3 4]) => 10)

(fact "return a sequence of intermediate steps"
  (reductions + [1 2 3 4]) => '(1 3 6 10))

(fact "start with a default state"
  (reduce conj #{} [:a :b :b :b :a :a]) => #{:a :b})

(fact "'reducing' elements 'into' a collection"
  (into {} [[:a 2][:b 3]]) => {:a 2, :b 3}
  (into '() [1 2 3 4]) => '(4 3 2 1))  ; reverses the sequence

(fact "retaining sequence order"
  (reduce conj [] [1 2 3 4 5]) => [1 2 3 4 5])

;; map defined in terms of reduce
(defn my-map [fun coll]
  (reduce (fn [output element]
            (conj output (fun element)))
          []
          coll))

(fact "let's try my-map"
  (my-map inc [1 2 3 4]) => [2 3 4 5])

;; take-while
(defn my-take-while [pred coll]
  (reduce (fn [out elem]
            (if (pred elem)
              (conj out elem)
              (reduced out)))
          []
          coll))

(fact "test my-take-while"
  (my-take-while pos? [2 1 0 -1 - 1 2])
  => [2 1])
