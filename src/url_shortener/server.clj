(ns url-shortener.server
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes GET POST PUT DELETE]]
            [compojure.route :as route]
            [ring.util.response :refer [response status not-found]]
            [url-shortener.db :as db])
  (:import (java.util Random)))

(defn gen-short []
  (let [chars "abcdefghijklmnopqrstuvwxyz0123456789"
        rnd (Random.)]
    (apply str (repeatedly 8 #(nth chars (.nextInt rnd (count chars)))))))

(defn create-short [normal]
  (let [short (gen-short)]
    (db/insert-link short normal)
    short))

(defroutes app-routes
  (POST "/normal-url" request
    (let [normal (slurp (:body request))
          short (create-short (clojure.string/trim normal))]
      (response short)))
  (GET "/short-url/:code" [code]
    (if-let [row (db/find-by-short code)]
      (response (:normal_url row))
      (not-found "error")))
  (PUT "/short-url/:short/normal-url/:normal" [short normal]
    (let [decoded (java.net.URLDecoder/decode normal "UTF-8")]
      (if (db/exists? short)
        (do (db/update-link short decoded) (status 200 "OK"))
        (status 404 "error"))))
  (DELETE "/short-url/:code" [code]
    (if (db/exists? code)
      (do (db/delete-link code) (status 200 "OK"))
      (status 404 "error")))
  (route/not-found "not found"))

(defn start-server [port]
  (db/init-db)
  (println (str "Сервер запущен на порту " port))
  (run-jetty app-routes {:port (Integer/parseInt port) :join? true}))
