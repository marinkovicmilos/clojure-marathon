(ns clojure-marathon.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure-marathon.views :as views]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (views/home-page))
  (GET "/races" [] (views/home-page))
  (GET "/add-race" [] (views/add-race-page))
  (POST "/add-race" {params :params} (views/add-race-result-page params))
  (GET "/edit-race/:id" [id] (views/edit-race-page id))
  (POST "/edit-race" {params :params} (views/edit-race-result-page params))
  (GET "/delete-race/:id" [id] (views/delete-race-page id))
  (GET "/runners-list/:id" [id] (views/runners-list-page id))
  (GET "/add-runner/:id" [id] (views/add-runner-page id))
  (POST "/add-runner" {params :params} (views/add-runner-result-page params))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
