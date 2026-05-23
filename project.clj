(defproject url-shortener "0.1.0"
  :description "URL shortener REST API"
  :url "https://github.com/Samirrrrr123/ktrestapi"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.9.6"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [compojure "1.7.0"]
                 [ring/ring-defaults "0.3.4"]
                 [clj-http "3.12.3"]
                 [org.xerial/sqlite-jdbc "3.42.0.0"]
                 [korma "0.4.3"]]
  :main url-shortener.core
  :aot [url-shortener.core]
  :uberjar-name "url-shortener-0.1.0-standalone.jar"
  :profiles {:uberjar {:aot :all}})
