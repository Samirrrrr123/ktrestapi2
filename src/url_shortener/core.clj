(ns url-shortener.core
  (:gen-class)
  (:require [url-shortener.server :as server]
            [url-shortener.cli :as cli]
            [url-shortener.client :as client]))

(defn -main [& args]
  (if (seq args)
    (do
      (client/set-host (first args))
      (server/start-server (first args)))
    (cli/run-cli)))
