(ns euler-clj.euler005
  (:require [midje.sweet :refer :all]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Smallest multiple
;;; Problem 5
;;
;; 2520 is the smallest number that can be divided by each of the numbers from
;; 1 to 10 without any remainder.
;; 
;; What is the smallest positive number that is evenly divisible by all of the
;; numbers from 1 to 20?
;;

(defn gcd [x y]
  "greatest common divisor"
  (if (zero? y) x
      (gcd y (mod x y))))

(fact "test gcd"
  (gcd 4 4) => 4   ; really
  (gcd 12 8) => 4
  (gcd 54 24) => 6
  (gcd 56 42) => 14)

(defn lcm [x y]
  "lowist common multiple"
  (/ (* x y) (gcd x y)))

(fact "test lcm"
  (lcm 4 4) => 4   ; really?
  (lcm 4 8) => 8
  (lcm 4 6) => 12
  (lcm 21 6) => 42)

(defn smallist-mult
  [num]
  (reduce #(lcm %1 %2) (range 1 num)))

(fact "example from problem"
  (smallist-mult 10) => 2520)

;; (smallist-mult 20)
