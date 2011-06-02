(ns edu.jared.patterns.strategy.mini-duck-simulator)

(defprotocol FlyBehavior
  (fly [x]))

(defprotocol QuackBehavior
  (quack [x]))

;Option 1 - define using a type
(deftype MallardDuck []
  FlyBehavior
  (fly [x] println "I'm flying!!")
  QuackBehavior
  (quack [x] println "Quack"))

(def mallard (MallardDuck.))
(println "Mallard says: " (quack mallard) (fly mallard))

;Option 2 - pick which parts of the protocol to implement
(deftype ModelDuck [])
(extend-protocol FlyBehavior
  ModelDuck
  (fly [x] println "I can't fly"))
(extend-protocol QuackBehavior
  ModelDuck
  (quack [x] println "Quack"))

(def model (ModelDuck.))
(println "Model Duck says: " (quack model) (fly model))

