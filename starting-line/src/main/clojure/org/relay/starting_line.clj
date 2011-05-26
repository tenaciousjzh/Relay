(ns org.relay.starting-line
  (:gen-class)
  (:use clojure.contrib.command-line))

(defn -main [&args]
  (with-command-line *command-line-args*
    "Starting-line is the command-line based utility for start a new relay web project."
    [[new-project "Creates a new project in a directory with the same name specified. This will default to \"relay_web_project\" if not specified." "relay_web_project"]
     remaining]
    (println "new-project:" new-project)))
