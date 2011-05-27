(ns starting-line.core
  (:use clargon.core
    [clojure.contrib.json :only (read-json)]
    [clj-time.format :only (formatter formatters parse unparse)]
    [clj-time.core :only (within? interval)]
    [clojure.contrib.io :only (read-lines)]))

(def hms (formatters :hour-minute-second))

(defn parse-line [line]
  (let [[_ t json] (re-find #"\d{4}-\d{2}-\d{2} (\d{2}:\d{2}:\d{2}).*?(\{.*)" line)]
    [(parse hms t) (read-json json)]))

(defn in-range? [line options]
  (let [[t _] line
        {:keys [start end]} options]
    (within? (interval start end) t)))

(defn statistics-for [line options]
  (let [[t json] line
        statistics (options :statistics)]
    (apply vector (unparse hms t) (map json statistics))))

(defn all-lines [options]
  (let [lines (->> (options :file)
    (read-lines)
    (map parse-line)
    (filter #(in-range? % options))
    (map #(statistics-for % options)))]
    lines))

(defn -main [& args]
  (let [args (remove keyword? args)
        options (clargon
      args
      (required ["--file" "The log file to parse"])
      (required ["--statistics" "The statistics to display"] #(map keyword (.split (or % "") ",")))
      (optional ["--start" "Time to start capturing" :default "00:00:00"] #(parse hms %))
      (optional ["--end" "Time to end capturing" :default "23:59:59"] #(parse hms %)))]
    (doseq [l (all-lines options)]
      (apply println l))))

(comment (-main "--file" "example.log" "--statistics" "foos,bars"))




