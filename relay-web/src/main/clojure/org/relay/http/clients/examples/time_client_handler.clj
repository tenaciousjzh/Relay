(ns org.relay.http.clients.examples.time-client-handler
  (:import (java.util.Date)
    (org.jboss.netty.buffer ChannelBuffer)
    (org.jboss.netty.channel SimpleChannelHandler)))

(defn make-time-handler []
  (proxy [SimpleChannelHandler] []
    (messageReceived [channel-handler-context message-event]
      (let [buf (cast ChannelBuffer (. message-event getMessage))
            current-time-millis (. buf readInt * 1000)]
        (println (Date. current-time-millis))
        (..
          (. message-event getChannel)
          (close))))

    (exceptionCaught [channel-handler-context exception-event]
      (..
        (. exception-event getCause)
        (printStackTrace))
      (..
        (. exception-event getChannel)
        (close)))))
