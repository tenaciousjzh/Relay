(ns org.relay.http.handlers.examples.simple-netty-server
  (:gen-class)
  (:use clojure.contrib.import-static)
  (:import
    [java.net InetSocketAddress]
    [java.util.concurrent Executors]
    [org.jboss.netty.bootstrap ServerBootstrap]
    [org.jboss.netty.channel Channels ChannelPipelineFactory
     SimpleChannelHandler SimpleChannelUpstreamHandler]
    [org.jboss.netty.channel.socket.nio NioServerSocketChannelFactory]
    [org.jboss.netty.buffer ChannelBuffers]
    [org.jboss.netty.handler.codec.http HttpRequestDecoder
     HttpResponseEncoder DefaultHttpResponse]
    ))

(import-static org.jboss.netty.handler.codec.http.HttpVersion HTTP_1_1)
(import-static org.jboss.netty.handler.codec.http.HttpResponseStatus OK)
(import-static org.jboss.netty.handler.codec.http.HttpHeaders$Names CONTENT_TYPE)

(declare make-handler)

(defrecord Server [#^ServerBootstrap bootstrap channel])

(defn start
  "Start a Netty server. Returns the pipeline."
  [port handler]
  (let [channel-factory (NioServerSocketChannelFactory.
    (Executors/newCachedThreadPool)
    (Executors/newCachedThreadPool))
        bootstrap (ServerBootstrap. channel-factory)
        pipeline (.getPipeline bootstrap)]
    (doto pipeline
      (.addLast "decoder" (new HttpRequestDecoder))
      (.addLast "encoder" (new HttpResponseEncoder))
      (.addLast "handler" (make-handler)))
    (doto bootstrap
      (.setOption "child.tcpNoDelay", true)
      (.setOption "child.keepAlive", true))
    (new Server bootstrap (.bind bootstrap (InetSocketAddress. port)))))

(defn stop-server
  {:doc "Stops a Server instance"
   :arglists '([server])}
  [{bootstrap :bootstrap channel :channel}]
  (println "Shutting down server ")
  (do (.unbind channel)
    (.releaseExternalResources bootstrap)))

(defn http-response
  [status]
  (doto (DefaultHttpResponse. HTTP_1_1 status)
    (.setHeader CONTENT_TYPE "text/plain; charset=UTF-8")
    (.setContent (ChannelBuffers/copiedBuffer
      (str "Success: " status) "UTF-8"))))

(defn make-handler
  "Returns a Netty handler."
  []
  (proxy [SimpleChannelUpstreamHandler] []
    (messageReceived [ctx e]
      (let [c (.getChannel e)
            cb (.getMessage e)]
        (println "HTTP request from" c)
        (.write c (http-response OK))
        (-> e
          (.getChannel) ;e is set as the second element in this expr (.getChannel e)
          (.close) ;channel from previous line is threaded into (.close channel)
          )))

    (exceptionCaught [ctx e]
      (let [throwable (.getCause e)]
        (println "@exceptionCaught" throwable))
      (-> e .getChannel .close))))

(comment
  (def *server* (start 3335 make-handler))
  (stop-server *server*)
  )
