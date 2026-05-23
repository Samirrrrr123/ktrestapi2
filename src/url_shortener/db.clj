(ns url-shortener.db
  (:require [korma.core :refer [defdb defentity select insert delete update where]]
            [korma.sql :as sql]))

(defdb db {:classname "org.sqlite.JDBC"
           :subprotocol "sqlite"
           :subname "url_shortener.db"})

(defentity links
  (table :links)
  (entity-fields :id :short_url :normal_url)
  (pk :id))

(defn init-db []
  (sql/exec-raw "CREATE TABLE IF NOT EXISTS links (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    short_url TEXT NOT NULL UNIQUE,
    normal_url TEXT NOT NULL)"))

(defn find-by-short [short]
  (first (select links (where {:short_url short}))))

(defn insert-link [short normal]
  (insert links (values {:short_url short :normal_url normal})))

(defn update-link [short normal]
  (update links (set-fields {:normal_url normal}) (where {:short_url short})))

(defn delete-link [short]
  (delete links (where {:short_url short})))

(defn exists? [short]
  (boolean (find-by-short short)))
