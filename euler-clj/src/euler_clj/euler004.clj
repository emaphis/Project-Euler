(ns euler-clj.euler004
  (:require [midje.sweet :refer :all]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Largest palindrome product
;;; Problem 4
;;
;; A palindromic number reads the same both ways. The largest palindrome made
;; from the product of two 2-digit numbers is 9009 = 91 × 99.
;;
;; Find the largest palindrome made from the product of two 3-digit numbers.
;;

(defn palindrome?
  "is the number a palindrome"
  [num]
  (let [num-str (str num)]
    (= (seq num-str) (reverse num-str))))

(fact "testing palindrome?"
  (palindrome? 1) => true
  (palindrome? 9009) => true
  (palindrome? 9090) => false)

(defn palindromes
  "calulate palindromes of the cross product 
   of [1 to num] X [1 to num]"
  [num]
  (for [x (range 1 num)
        y (range 1 num)
        :when (palindrome? (* x y))]
    (* x y)))

(fact "example from exercise"
  (apply max (palindromes 100)) => 9009)

;; (apply max (palindromes 1000))
