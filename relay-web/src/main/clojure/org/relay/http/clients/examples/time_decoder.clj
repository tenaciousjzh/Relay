(ns org.relay.http.clients.examples.time-decoder
  (:import (org.jboss.netty.handler.codec.frame FrameDecoder))
  )

(defn make-time-decoder
  (proxy [FrameDecoder] []
    (decode [channel-handler-context channel buffer]
      (let [readable-bytes (. buffer readableBytes)]
        (cond
          (readable-bytes < 4) (nil)
          :default (. buffer readBytes 4)
          ))))
  )
