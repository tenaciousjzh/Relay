(defproject starting-line "1.0.0-SNAPSHOT"
  :description "FIXME: write"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [clargon "1.0.0"]
                 [clj-time "0.1.0-RC1"]
                 ]
  :dev-dependencies [[swank-clojure "1.2.1"]
                     [lein-run "1.0.1-SNAPSHOT"]]
  :run-aliases {:tablify [starting-line.core -main]})
