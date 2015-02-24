(ns euler-clj.euler001
  (:require [midje.sweet :refer :all]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Problem 1
;;
;; If we list all the natural numbers below 10 that are multiples of 3 or 5, we
;; get 3, 5, 6 and 9. The sum of these multiples is 23.
;;
;; Find the sum of all the multiples of 3 or 5 below 1000.
;;

(defn multiples
  "stream of multiples of 3 and 5 from 1 up to num"
  [num]
  (filter #(or (zero? (mod % 5))
               (zero? (mod % 3)))
          (range 1 num)))

(facts "test examples given in problem"
  (fact "multiples upto 10"
    (multiples 10) => '(3 5 6 9))
  (fact "the sum of multiples upto 10"
    (apply + (multiples 10)) => 23))

(apply + (multiples 1000))


;; or as a one liner:

(apply + (filter #(or (zero? (mod % 5)) (zero? (mod % 3))) (range 1 1000)))
