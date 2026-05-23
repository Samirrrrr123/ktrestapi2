(ns url-shortener.client
  (:require [clj-http.client :as http]))

(def base-url (atom "http://localhost:8080"))

(defn set-host [port]
  (reset! base-url (str "http://localhost:" port)))

(defn create-url [normal]
  (:body (http/post (str @base-url "/normal-url")
                    {:body normal
                     :content-type "text/plain"
                     :as :string})))

(defn show-url [short]
  (try
    (:body (http/get (str @base-url "/short-url/" short) {:as :string}))
    (catch Exception _
      nil)))

(defn update-url [short normal]
  (try
    (let [enc (java.net.URLEncoder/encode normal "UTF-8")]
      (:status (http/put (str @base-url "/short-url/" short "/normal-url/" enc)
                         {:as :string})))
    (catch Exception _
      404)))

(defn delete-url [short]
  (try
    (:status (http/delete (str @base-url "/short-url/" short) {:as :string}))
    (catch Exception _
      404)))
