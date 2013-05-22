(ns wdays.test.change
  (:use [wdays.change])
  (:use [clojure.test]))

(deftest upd-wday-test
  (let [wd [1.0 2.0 3]
        new-wday (wdays.change/upd-wday wd)]
    (is (every? integer? new-wday))
    (is [1.0 2 3.0] new-wday)))

