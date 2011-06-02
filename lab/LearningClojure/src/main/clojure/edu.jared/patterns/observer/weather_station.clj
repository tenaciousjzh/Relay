(ns edu.jared.patterns.observer.weather-station)

(defrecord WeatherData [temperature humidity pressure])

(def weather-data (atom (WeatherData. 80 65 30.4)))

(defn display [temperature humidity]
  (println "Current conditions: " temperature "F degrees and " humidity "% humidity."))

(display (:temperature @weather-data) (:humidity @weather-data))

(defn on-change [the-key the-ref old-value new-value]
;  (println "Old conditions: " (:temperature old-value) "F degrees and " (:humidity old-value) "% humidity.")
  (display (:temperature new-value) (:humidity new-value)))

(add-watch weather-data :weather-watcher on-change)
(reset! weather-data (WeatherData. 82 70 29.2))
(reset! weather-data (WeatherData. 78 90 29.2))

