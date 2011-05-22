(ns test.org.relay.http.http_handler_registry_spec
  (:use org.relay.http.handlers.handler-registry)
  (:use clojure.test)
  )

(defn handle-get-request [http-message channel]
  (println "http-message contains: " http-message)
  (println "channel contains: " channel)
  )

(deftest test-http-method-values
  (is (= (:get http-methods) "GET"))
  (is (= (:put http-methods) "PUT"))
  (is (= (:post http-methods) "POST"))
  (is (= (:delete http-methods) "DELETE")))

;(deftest test-register-one-handler
;  (let [uri "/account/profile"
;        method (:get http-methods)
;        fn (handle-get-request)
;        is-registered (register-handler uri method fn)]
;    (is (= is-registered true))
;    (is (= (http-method-handled? uri (:get http-methods)) true))))
