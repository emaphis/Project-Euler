(ns euler-clj.euler006
  (:require [midje.sweet :refer :all]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Sum square difference
;;; Problem 6
;;;
;;The sum of the squares of the first ten natural numbers is,
;;                 12 + 22 + ... + 102 = 385
;;
;; The square of the sum of the first ten natural numbers is,
;;                 (1 + 2 + ... + 10)2 = 552 = 3025
;;
;; Hence the difference between the sum of the squares of the first ten natural
;; numbers and the square of the sum is 3025 − 385 = 2640.
;;
;; Find the difference between the sum of the squares of the first one hundred
;; natural numbers and the square of the sum.
;;

(defn sum-of-squares
  [num]
  (apply + (map #(* % %)(range 1 (inc num)))))

(fact "example from problem"
  (sum-of-squares 10) => 385)

(defn square-of-sums
  [num]
  (let [sum (apply + (range 1 (inc num)))]
    (* sum sum)))

(fact "example from problem"
  (square-of-sums 10) => 3025)

(defn sum-square-difference
  [num]
  (- (square-of-sums num) (sum-of-squares num)))

(fact "example from problem"
  (sum-square-difference 10) => 2640)

;;(sum-square-difference 100)
