(ns org.relay.http.handlers.examples.echo-server-handler
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
      (let [message (. event getMessage)]
        (.. event
          (getChannel)
          (write message)
          )))
    (exceptionCaught [channel-handler-context exception-event]
      (println "Echo Server ran into a problem processing a request: " exception-event)
      )))

(def channel-pipeline-factory
  (reify ChannelPipelineFactory
    (getPipeline [this]
      (Channels/pipeline (into-array ChannelHandler [discard-handler])))))

(defn run-echo-server
  "Generates a ChannelHandler that will echo messages when they are received. This is a
  contrived example mostly to determine how Clojure's interop facilities will work with Netty."
  [port]
  (let [factory (NioServerSocketChannelFactory. (Executors/newCachedThreadPool) (Executors/newCachedThreadPool))
        bootstrap (ServerBootstrap. factory)]
    (doto bootstrap
      (.setPipelineFactory channel-pipeline-factory)
      (.setOption "child.tcpNoDelay" true)
      (.setOption "child.keepAlive" true)
      (.bind (InetSocketAddress. port)))))
