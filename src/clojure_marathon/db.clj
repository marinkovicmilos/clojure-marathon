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

(defn add-race
  [naziv dan mesec godina email opis]
  (jdbc/insert! db-spec :races {:naziv naziv :dan dan :mesec mesec :godina godina :email email :opis opis}))

(defn edit-race
  [id naziv dan mesec godina email opis]
   (jdbc/update! db-spec :races {:naziv naziv :dan dan :mesec mesec :godina godina :email email :opis opis} ["id = ?" id]))

(defn get-race-by-id
  [id]
  (jdbc/query db-spec ["select * from races where id = ?" id]))

(defn delete-race
  [id]
  (jdbc/delete! db-spec :races ["id = ?" id]))

(defn get-all-runners-for-race
  [id]
  (jdbc/query db-spec ["select * from runners where trka = ?" id]))

(defn add-runner
  [ime prezime age broj email trka]
  (jdbc/insert! db-spec :runners {:ime ime :prezime prezime :age age :broj broj :email email :trka trka}))

(defn edit-runner
  [id ime prezime age broj email trka]
   (jdbc/update! db-spec :runners {:ime ime :prezime prezime :age age :broj broj :email email :trka trka} ["id = ?" id]))

(defn get-runner-by-id
  [id]
  (jdbc/query db-spec ["select * from runners where id = ?" id]))

(defn delete-runner
  [id]
  (jdbc/delete! db-spec :runners ["id = ?" id]))