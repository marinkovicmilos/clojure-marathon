(ns clojure-marathon.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure-marathon.views :as views]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/races" [] (views/home-page))
  (GET "/add-race" [] (views/add-race-page))
  (POST "/add-race" {params :params} (views/add-race-result-page params))
  (GET "/edit-race/:id" [id] (str "Izmeni trku " id))
  (GET "/delete-race/:id" [id] (str "Obrisi trku " id))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
