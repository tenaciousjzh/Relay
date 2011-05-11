(ns org.relay
  (:use (org.relay.http.handlers [discard-server-handler]))
  (:gen-class))

(defn -main [& args]
  (println "This is the main function for app.")
  (run-discard-server 8080)
  )
