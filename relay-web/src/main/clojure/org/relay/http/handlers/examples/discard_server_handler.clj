(ns org.relay.http.handlers.examples.discard-server-handler
  (:import
    (org.jboss.netty.bootstrap ServerBootstrap)
    (org.jboss.netty.buffer ChannelBuffer)
    (org.jboss.netty.channel SimpleChannelHandler ChannelPipelineFactory Channels ChannelHandler)
    (org.jboss.netty.channel.socket.nio NioServerSocketChannelFactory)
    (java.net InetSocketAddress)
    (java.util.concurrent Executors)
    )
  )

(def discard-handler
  (proxy [SimpleChannelHandler] []
    (messageReceived [channel-handler-context event]
      (let [buf (cast ChannelBuffer (. event getMessage))]
        (println "Discarding message: ")
        (while (. buf readable)
            (print (char (. buf readByte)))
          )))
    (exceptionCaught [channel-handler-context exception-event]
      (println "Discard Server ran into a problem processing a request: " exception-event)
      )))

(def channel-pipeline-factory
  (reify ChannelPipelineFactory
    (getPipeline [this]
      ;had trouble passing in discard-handler to Channel/pipeline because it was a variadic function
      ;e.g - it expected ChannelHandler... variadic argument. StackOverFlow had a great post on this:
      ;http://stackoverflow.com/questions/5638541/problems-calling-a-variadic-java-function-from-clojure
      (Channels/pipeline (into-array ChannelHandler [discard-handler])))))

(defn run-discard-server
  "Generates a ChannelHandler that will discard messages when they are received. This is a
  contrived example mostly to determine how Clojure's interop facilities will work with Netty."
  [port]
  (let [factory (NioServerSocketChannelFactory. (Executors/newCachedThreadPool) (Executors/newCachedThreadPool))
        bootstrap (ServerBootstrap. factory)]
    (doto bootstrap
      (.setPipelineFactory channel-pipeline-factory)
      (.setOption "child.tcpNoDelay" true)
      (.setOption "child.keepAlive" true)
      (.bind (InetSocketAddress. port)))))
