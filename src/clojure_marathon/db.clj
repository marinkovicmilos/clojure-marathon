(ns clojure-marathon.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec
  {:dbtype "h2"
   :dbname "./races-db"
   :user "admin"
   :password "admin"})

(defn get-all-races
  []
  (jdbc/query db-spec ["select * from races"]))

