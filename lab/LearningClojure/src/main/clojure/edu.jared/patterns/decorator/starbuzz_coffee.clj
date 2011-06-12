(ns edu.jared.patterns.decorator.starbuzz-coffee)

(def condiments {:costs [0.20 0.15 0.35]  :descriptions [", Mocha" ", Soy" ", Whip"]})

(defprotocol Beverage
  (cost [x])
  (description [x]))

(defrecord Espresso []
  Beverage
  (cost [x] 1.99)
  (description [x] "Espresso"))

(defrecord DarkRoast []
  Beverage
  (cost [x] 0.99)
  (description [x] "Dark Roast"))

(defrecord HouseBlend []
  Beverage
  (cost [x] 0.89)
  (description [x] "House Blend"))



(defn display-with-meta [beverage]
  (let [cost (+ (cost beverage) (reduce + (:costs (meta beverage))))
        descriptions (apply str (:descriptions (meta beverage)))]
    (println (description beverage) descriptions " $" cost)))

(defn display [beverage]
  (if (nil? (meta beverage))
    (println (description beverage) " $" (cost beverage))
    (display-with-meta beverage)))



(display (Espresso.))
; The goal of decorator is to extend the ability of an existing type.
; Clojure meta-data can add existing functionality to any type

(def dark-roast (DarkRoast. ))

(def decorated-dark-roast (with-meta dark-roast condiments))

(display decorated-dark-roast)

