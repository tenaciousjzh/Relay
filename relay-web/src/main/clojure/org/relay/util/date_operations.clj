(ns org.relay.util.date-operations
  (:import (java.text SimpleDateFormat)
    (java.util GregorianCalendar Calendar))
  (:use clojure.contrib.str-utils))

(defn date [date-string]
  (let[format (SimpleDateFormat. "yyyy-MM-dd")
       d (.parse format date-string)]
    (doto (GregorianCalendar.)
      (.setTime d))))

(defn pad [n]
  (if (< n 10) (str "0" n) (str n)))

(defn month-from [date]
  (inc (.get date Calendar/MONTH)))

(defn day-from [date]
  (.get date Calendar/DAY_OF_MONTH))

(defn year-from [date]
  (.get date Calendar/YEAR))

(defn as-string [date]
  (let [year (year-from date)
        month (pad (month-from date))
        day (pad (day-from date))]
    (str-join "-" [year month day])))