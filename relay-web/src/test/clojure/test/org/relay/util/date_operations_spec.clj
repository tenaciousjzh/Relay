(ns test.org.relay.util.date-operations-spec
  (:use org.relay.util.date-operations)
  (:use clojure.test))

(deftest test-simple-date-parsing
  (let [d (date "2011-01-30")]
    (is (= (month-from d) 1)
    (is (= (day-from d) 30)
    (is (= (year-from d) 2011))))))

(deftest test-as-string
  (let [d (date "2011-01-30")]
    (is (= (as-string d) "2011-01-30"))))
