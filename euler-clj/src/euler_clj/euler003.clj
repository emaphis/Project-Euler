(ns euler-clj.euler003
  (:require [midje.sweet :refer :all]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Largest prime factor
;;; Problem 3
;;
;; The prime factors of 13195 are 5, 7, 13 and 29.
;;
;; What is the largest prime factor of the number 600851475143 ?
;;

(defn factors
  "return the prime factors of a given number - naive"
  ([num] (factors num 2))
  ([num fac]
   (cond (<= num 2) '()
                 (zero? (rem num fac)) (cons fac (factors (/ num fac)))
                 true (factors num (inc fac)))))

(fact "test example from question"
  (factors 13195) => [5 7 13 29]
  (apply max (factors 13195)) => 29)

;; (apply max (factors 600851475143)) ;; stack overflow!!!



;; needed to be tail recursive, so I had to learn and apply
;; the (loop...  ... (recur ...)) construct.

(defn factors'
  "return the prime factors of a given number - faster"
  ([num]
   (loop [num num
          fac-lst '()
          fac 2]
     (cond (<= num 2) fac-lst
           (zero? (rem num fac)) (recur (/ num fac)
                                        (cons fac fac-lst)
                                        fac)
           true (recur num fac-lst (inc fac))))))

(fact "test example from question"
  (factors' 13195) => [29 13 7 5]
  (apply max (factors' 13195)) => 29)

;;(apply max (factors' 600851475143))  ;; works!
